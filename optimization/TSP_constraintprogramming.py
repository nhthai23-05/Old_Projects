from pulp import LpProblem, LpMinimize, LpVariable, lpSum

def solve_tsp(distance_matrix):
    n = len(distance_matrix)
    problem = LpProblem("TSP", LpMinimize)

    # Decision variables
    x = [[LpVariable(f"x_{i}_{j}", cat="Binary") for j in range(n)] for i in range(n)]
    f = [[LpVariable(f"f_{i}_{j}", lowBound=0) for j in range(n)] for i in range(n)]

    # Objective function
    problem += lpSum(distance_matrix[i][j] * x[i][j] for i in range(n) for j in range(n))

    # Constraints: each node has exactly one incoming and one outgoing edge
    for i in range(n):
        problem += lpSum(x[i][j] for j in range(n) if i != j) == 1
        problem += lpSum(x[j][i] for j in range(n) if i != j) == 1

    # Subtour elimination constraints (flow-based)
    for i in range(1, n):
        for j in range(1, n):
            if i != j:
                problem += f[i][j] <= (n - 1) * x[i][j]
                problem += f[i][j] >= 0
    for j in range(1, n):
        problem += lpSum(f[i][j] for i in range(n) if i != j) - lpSum(f[j][k] for k in range(n) if j != k) == 1

    # Solve the problem
    problem.solve()

    # Extract solution
    tour = []
    for i in range(n):
        for j in range(n):
            if x[i][j].varValue == 1:
                tour.append((i, j))

    return tour, problem.objective.value()

# Example distance matrix
distance_matrix = [
    [0, 10, 15, 20],
    [10, 0, 35, 25],
    [15, 35, 0, 30],
    [20, 25, 30, 0]
]

tour, cost = solve_tsp(distance_matrix)
print("Tour:", tour)
print("Cost:", cost)
