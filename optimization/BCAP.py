inp = """4 12
5 1 3 5 10 12
5 9 3 4 8 12
6 1 2 3 4 9 7
7 1 2 3 5 6 10 11
25
1 2
1 3
1 5
2 4
2 5
2 6
3 5
3 7
3 10
4 6
4 9
5 6
5 7
5 8
6 8
6 9
7 8
7 10
7 11
8 9
8 11
8 12
9 12
10 11
11 12"""
from ortools.sat.python import cp_model

def BCAP_input(inp):
    lines = inp.strip().split("\n")  # Split input into lines
    m, n = map(int, lines[0].split())  # Number of teachers and courses

    teachers = []
    for i in range(1, m + 1):  # Loop through teacher lines
        line = list(map(int, lines[i].split()))
        num_courses_each_teacher = line[0]
        courses_each_teacher = line[1:num_courses_each_teacher + 1]
        teachers.append(courses_each_teacher)

    num_pairs_conflict = int(lines[m + 1])  # Number of conflict pairs
    conflicts = []
    for i in range(m + 2, m + 2 + num_pairs_conflict):  # Conflict pairs
        conflicts.append(tuple(map(int, lines[i].split())))

    return m, n, teachers, conflicts # 4, 12, list of 4 teachers, list of 25 conflicts

m, n, teachers, conflicts = BCAP_input(inp)
def solve(m,n,teachers,conflicts):
    model = cp_model.CpModel()

    # Decision variables
    x = {}
    for t in range(m):
        for c in teachers[t]:
            x[t, c] = model.NewBoolVar('x[%i,%i]' % (t, c))

    load = [model.NewIntVar(0, n, f'load_{t}') for t in range(m)]

    max_load = model.NewIntVar(0, n, 'max_load')

    # Constraints: Each course is assigned to exactly one teacher
    for c in range(1, n + 1):
        model.Add(sum(x[(t, c)] for t in range(m) if (t, c) in x) == 1)

    # Constraints: Teachers can only be assigned courses they are eligible for
    for t in range(m):
        for c in range(1, n + 1):
            if c not in teachers[t]:
                model.Add(x.get((t, c), 0) == 0)

    # Constraints: Conflict courses cannot be assigned to the same teacher
    for (c1, c2) in conflicts:
        for t in range(m):
            if (t, c1) in x and (t, c2) in x:
                model.Add(x[(t, c1)] + x[(t, c2)] <= 1)

    # Compute each teacher's load
    for t in range(m):
        model.Add(load[t] == sum(x[(t, c)] for c in range(1, n + 1) if (t, c) in x))

    # Minimize the maximum load
    model.AddMaxEquality(max_load, load)
    model.Minimize(max_load)

    # Solve the model
    solver = cp_model.CpSolver()
    status = solver.Solve(model)

    #output
    if status == cp_model.OPTIMAL or status == cp_model.FEASIBLE:
        return solver.Value(max_load)
    else:
        return -1
m, n, teachers, conflicts = BCAP_input(inp)
solve(m, n, teachers, conflicts)