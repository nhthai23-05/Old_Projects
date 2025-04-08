#Import solver
from ortools.linear_solver import pywraplp
solver = pywraplp.Solver.CreateSolver('GLOP')

#Create variables x and y
x = solver.NumVar(0, solver.infinity(), 'x') #lower bound, upper bound, var name
y = solver.NumVar(0, solver.infinity(), 'y')

#Create constraint
solver.Add(x + 2*y <= 10)
solver.Add(4*x + 2*y <= 12)

#Objective function
solver.Maximize(x+y)
print(solver.Objective().Value())
print('x = ', x.solution_value())
print('y = ', y.solution_value())