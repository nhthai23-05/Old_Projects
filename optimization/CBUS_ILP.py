from ortools.linear_solver import pywraplp

def cbus_ilp_ortools(n, k, distance_matrix):

    big_M = 2 * n
    # Tạo solver
    solver = pywraplp.Solver.CreateSolver('SAT')

    # Biến quyết định: x[i, j] = 1 nếu xe đi từ i đến j
    x = {}
    for i in range(2 * n + 1):
        for j in range(2 * n + 1):
            if i != j:
                x[i, j] = solver.BoolVar(f'x[{i},{j}]')

    # Biến thứ tự: u[i] là thứ tự ghé thăm điểm i
    u = {i: solver.IntVar(0, 2 * n, f'u[{i}]') for i in range(2 * n + 1)}

    # Hàm mục tiêu: Tối thiểu hóa tổng khoảng cách
    solver.Minimize(
        solver.Sum(distance_matrix[i][j] * x[i, j] for i in range(2 * n + 1) for j in range(2 * n + 1) if i != j)
    )

    # Ràng buộc:
    # 1. Mỗi điểm được vào đúng 1 lần
    for i in range(1, 2 * n + 1):
        solver.Add(solver.Sum(x[j, i] for j in range(2 * n + 1) if j != i) == 1)

    # 2. Mỗi điểm được rời đi đúng 1 lần
    for i in range(1, 2 * n + 1):
        solver.Add(solver.Sum(x[i, j] for j in range(2 * n + 1) if j != i) == 1)

    # 3. Subtour elimination: u[i] + 1 <= u[j] nếu x[i, j] = 1
    for i in range(1, 2 * n + 1):
        for j in range(1, 2 * n + 1):
            if i != j:
                solver.Add(u[i] + 1 <= u[j] + (2 * n) * (1 - x[i, j]))

    # 4. Drop-off sau pickup
    for i in range(1, n + 1):
        solver.Add(u[i] <= u[i + n])

    # 5. Giới hạn sức chứa
    load = {i: solver.IntVar(0, k, f'load[{i}]') for i in range(2 * n + 1)}
    for i in range(1, 2 * n + 1):
        for j in range(1, n + 1):  # For pickup points
            if i != j:
                solver.Add(load[j] >= load[i] + 1 - big_M * (1 - x[i, j]))
                solver.Add(load[j] <= load[i] + 1 + big_M * (1 - x[i, j]))

    for j in range(n + 1, 2 * n + 1):  # For drop-off points
        if i != j:
            solver.Add(load[j] >= load[i] - 1 - big_M * (1 - x[i, j]))
            solver.Add(load[j] <= load[i] - 1 + big_M * (1 - x[i, j]))

    # Giải bài toán
    status = solver.Solve()

    # Xử lý kết quả
    if status == pywraplp.Solver.OPTIMAL:
        return solver.Objective().Value()
    else:
        print('No solution found.')
        return None

n, k = map(int, input().split())

# Read distance matrix
distance_matrix = []
for _ in range(2 * n + 1):
    row = list(map(int, input().split()))
    distance_matrix.append(row)


cost = int(cbus_ilp_ortools(n, k, distance_matrix))
print(cost)