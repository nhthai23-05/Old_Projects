import random

def evaluate(schedule, workloads):
    """Evaluate the quality of a schedule by calculating the workload imbalance."""
    worker_loads = [0] * len(schedule)
    for worker, tasks in enumerate(schedule):
        worker_loads[worker] = sum(workloads[task] for task in tasks)
    return max(worker_loads) - min(worker_loads)

def generate_neighbors(schedule, num_tasks):
    """Generate neighboring schedules by swapping task assignments."""
    neighbors = []
    for i in range(len(schedule)):
        for j in range(len(schedule)):
            if i != j and schedule[i]:
                # Move a task from worker i to worker j
                for task in schedule[i]:
                    neighbor = [list(worker) for worker in schedule]
                    neighbor[i].remove(task)
                    neighbor[j].append(task)
                    neighbors.append(neighbor)
    return neighbors

def hill_climbing(workloads, num_workers, max_iterations=1000):
    """Solve the scheduling problem using hill climbing."""
    num_tasks = len(workloads)

    # Randomly initialize the schedule
    schedule = [[] for _ in range(num_workers)]
    for task in range(num_tasks):
        schedule[random.randint(0, num_workers - 1)].append(task)

    # Evaluate the initial state
    current_eval = evaluate(schedule, workloads)

    for iteration in range(max_iterations):
        neighbors = generate_neighbors(schedule, num_tasks)
        
        # Find the best neighbor
        best_neighbor = None
        best_eval = float('inf')
        for neighbor in neighbors:
            neighbor_eval = evaluate(neighbor, workloads)
            if neighbor_eval < best_eval:
                best_neighbor = neighbor
                best_eval = neighbor_eval

        # Check if the neighbor improves the schedule
        if best_eval < current_eval:
            schedule = best_neighbor
            current_eval = best_eval
        else:
            break  # No better neighbor found, terminate

    return schedule, current_eval

if __name__ == "__main__":
    # Example problem: Task workloads and number of workers
    workloads = [10, 20, 30, 40, 50, 60, 70, 80, 90, 100]
    num_workers = 3

    # Solve the scheduling problem
    best_schedule, best_imbalance = hill_climbing(workloads, num_workers)

    # Display results
    print("Best Schedule:")
    for worker, tasks in enumerate(best_schedule):
        print(f"Worker {worker + 1}: Tasks {tasks}, Workload {sum(workloads[task] for task in tasks)}")

    print(f"Workload Imbalance: {best_imbalance}")
