from ortools.linear_solver import pywraplp

import sys
input = sys.stdin.read
string = input()


def process_input(inp):
    inp = inp.split('\n')
    firstline = inp[0].split()
    num_warehouse = int(firstline[0])
    num_supermarket = int(firstline[1])
    secondline = inp[1].split()
    A  = []
    for i in secondline:
        A.append(int(i))
    thirdline = inp[2].split()
    B = []
    for i in thirdline:
        B.append(int(i))
    otherline = inp[3:len(inp)]
    costmatrix = []
    for line in otherline:
        u = []
        for j in line.split():
            u.append(int(j))
        costmatrix.append(u)
    return num_supermarket, num_warehouse, A, B, costmatrix

def set_constraint(num_supermarket, num_warehouse, A,B, costmatrix):
    solver = pywraplp.Solver.CreateSolver("GLOP")
    K = num_warehouse*num_supermarket
    cost = 0.0
    x = {}
    for i in range(num_warehouse):
        sum = 0.0
        for j in range(num_supermarket):
            x[i, j] = solver.NumVar(0, A[i], f'x_{i}_{j}')
            cost += x[i, j] * costmatrix[i][j]
        solver.Add(sum <= A[i])
    for j in range(num_supermarket):
        sum_j = 0.0
        for i in range(num_supermarket):
            sum_j += x[i, j]
        solver.Add(sum_j >= B[j]) 
    solver.Minimize(cost)
    status = solver.Solve()
    if status == pywraplp.Solver.OPTIMAL:
        print(K)
        for i in range(num_warehouse): 
            for j in range(num_supermarket):
                string_x = 'x_'
                string_x += str(i+1) + str(j+1)
                result = f'{i+1} {j+1} {x[i, j].solution_value()}'
                print(result)

num_supermarket, num_warehouse, A,B, costmatrix = process_input(string)
set_constraint(num_supermarket, num_warehouse, A,B, costmatrix)
