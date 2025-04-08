import random
import math

def calculate_cost(route, distance_matrix):
    cost = 0
    for i in range(len(route) - 1):
        cost += distance_matrix[route[i]][route[i + 1]]
    cost += distance_matrix[route[-1]][0]  # Return to the depot
    return cost

def is_valid_route(route, n, k):
    capacity = 0
    visited_pickup = [False] * (n + 1)

    for point in route:
        if point <= n:  # Pickup point
            if capacity >= k:
                return False
            visited_pickup[point] = True
            capacity += 1
        else:  # Drop-off point
            if not visited_pickup[point - n]:  # Drop-off without pickup
                return False
            capacity -= 1

    return True

def generate_initial_solution(n):
    route = list(range(1, 2 * n + 1))
    random.shuffle(route)
    return route

def tweak_solution(route):
    new_route = route[:]
    i, j = random.sample(range(len(route)), 2)
    new_route[i], new_route[j] = new_route[j], new_route[i]
    return new_route

def cbus_simulated_annealing(n, k, distance_matrix, initial_temperature=1000, cooling_rate=0.998, max_iterations=10000):
    current_solution = generate_initial_solution(n)
    while not is_valid_route(current_solution, n, k):
        current_solution = generate_initial_solution(n)

    current_cost = calculate_cost([0] + current_solution, distance_matrix)
    best_solution = current_solution[:]
    best_cost = current_cost
    temperature = initial_temperature

    for iteration in range(max_iterations):
        new_solution = tweak_solution(current_solution)
        while not is_valid_route(new_solution, n, k):
            new_solution = tweak_solution(current_solution)

        new_cost = calculate_cost([0] + new_solution, distance_matrix)

        if new_cost < current_cost or random.random() < math.exp(-(new_cost - current_cost) / temperature):
            current_solution = new_solution
            current_cost = new_cost

            if new_cost < best_cost:
                best_solution = new_solution
                best_cost = new_cost

        temperature *= cooling_rate
        if temperature < 1e-6:
            break

    return best_solution, best_cost

# Input handling
n, k = map(int, input().split())

# Read distance matrix
distance_matrix = []
for _ in range(2 * n + 1):
    row = list(map(int, input().split()))
    distance_matrix.append(row)

# Run Simulated Annealing
route, cost = cbus_simulated_annealing(n, k, distance_matrix)
print(cost)
print(n, end= '\n')
print(' '.join(map(str, route)))
