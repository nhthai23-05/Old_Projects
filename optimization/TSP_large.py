import math
import random
import sys

# Function to calculate the total distance of a tour
def calculate_total_distance(tour, distance_matrix):
    """Calculate the total distance of a given tour."""
    return sum(distance_matrix[tour[i] - 1][tour[(i + 1) % len(tour)] - 1] for i in range(len(tour)))

# Simulated Annealing Algorithm
def simulated_annealing(n, distance_matrix, temp=1000, cooling_rate=0.995, iterations=1000):
    """Solve TSP using Simulated Annealing."""
    # Start with a random tour
    current_tour = list(range(1, n + 1))
    random.shuffle(current_tour)
    best_tour = current_tour[:]
    current_distance = calculate_total_distance(current_tour, distance_matrix)
    best_distance = current_distance

    while temp > 1:
        for _ in range(iterations):
            # Generate a new tour by swapping two cities
            i, j = random.sample(range(n), 2)
            new_tour = current_tour[:]
            new_tour[i], new_tour[j] = new_tour[j], new_tour[i]

            new_distance = calculate_total_distance(new_tour, distance_matrix)

            # Accept the new tour with a probability based on the temperature
            if new_distance < current_distance or random.random() < math.exp((current_distance - new_distance) / temp):
                current_tour = new_tour
                current_distance = new_distance

                # Update the best tour found so far
                if current_distance < best_distance:
                    best_tour = current_tour
                    best_distance = current_distance

        # Cool down the temperature
        temp *= cooling_rate

    return best_tour

# Function to read the input
def read_input():
    """Provide test input directly for debugging."""
    n = 5
    distance_matrix = [
        [0, 29, 20, 21, 16],
        [29, 0, 15, 17, 28],
        [20, 15, 0, 18, 23],
        [21, 17, 18, 0, 25],
        [16, 28, 23, 25, 0]
    ]
    return n, distance_matrix

# Function to output the result
def output_result(n, tour):
    """Output the result in the specified format."""
    print(n)
    print(" ".join(map(str, tour)))

# Main function
if __name__ == "__main__":
    n, distance_matrix = read_input()

    # Solve TSP using Simulated Annealing
    optimized_tour = simulated_annealing(n, distance_matrix)

    # Output the results
    output_result(n, optimized_tour)

