from ortools.constraint_solver import pywrapcp
from ortools.constraint_solver import routing_enums_pb2

def create_data_model():
    """Stores the data for the problem."""
    firstline = input()
    firstline = firstline.split()
    n = int(firstline[0])
    k = int(firstline[1])
    distance_matrix = []
    for i in range(2*n + 1):
        row_i = []
        line_i = input()
        line_i = line_i.split()
        for j in line_i:
            row_i.append(int(j))
        distance_matrix.append(row_i)


    data = {}
    data['distance_matrix'] = distance_matrix
    data['num_vehicles'] = 1  # Only one bus
    data['depot'] = 0  # Starting and ending point
    data['pickups_deliveries'] = [ (i, i + n) for i in range(1, n + 1)
    ]
    data['vehicle_capacity'] = k  # Bus capacity
    data['demands'] = [0] + [1] * n + [-1] * n  # +1 for pickups, -1 for deliveries
    return data

def main():
    """Solves the PDP with capacity constraints."""
    data = create_data_model()
    manager = pywrapcp.RoutingIndexManager(len(data['distance_matrix']),
                                           data['num_vehicles'], data['depot'])
    routing = pywrapcp.RoutingModel(manager)

    # Define cost of each arc.
    def distance_callback(from_index, to_index):
        from_node = manager.IndexToNode(from_index)
        to_node = manager.IndexToNode(to_index)
        return data['distance_matrix'][from_node][to_node]

    transit_callback_index = routing.RegisterTransitCallback(distance_callback)
    routing.SetArcCostEvaluatorOfAllVehicles(transit_callback_index)

    # Add capacity constraints.
    def demand_callback(from_index):
        from_node = manager.IndexToNode(from_index)
        return data['demands'][from_node]

    demand_callback_index = routing.RegisterUnaryTransitCallback(demand_callback)
    capacity = routing.AddDimensionWithVehicleCapacity(
        demand_callback_index,
        0,  # Null capacity slack
        [data['vehicle_capacity']],  # Vehicle capacity
        True,  # Start cumul to zero
        "Capacity")
    capacity_dimension = routing.GetDimensionOrDie("Capacity")

    # Define pickup and delivery constraints.
    for pickup, delivery in data['pickups_deliveries']:
        routing.AddPickupAndDelivery(manager.NodeToIndex(pickup), manager.NodeToIndex(delivery))
        routing.solver().Add(routing.VehicleVar(manager.NodeToIndex(pickup)) ==
                             routing.VehicleVar(manager.NodeToIndex(delivery)))
        routing.solver().Add(
            capacity_dimension.CumulVar(manager.NodeToIndex(pickup)) <=
            capacity_dimension.CumulVar(manager.NodeToIndex(delivery)))

    # Set search parameters.
    search_parameters = pywrapcp.DefaultRoutingSearchParameters()
    search_parameters.first_solution_strategy = (
        routing_enums_pb2.FirstSolutionStrategy.PATH_CHEAPEST_ARC)

    # Solve the problem.
    solution = routing.SolveWithParameters(search_parameters)

    # Output the solution.
    if solution:
        route = []
        index = routing.Start(0)
        total_distance = 0
        while not routing.IsEnd(index):
            route.append(manager.IndexToNode(index))
            previous_index = index
            index = solution.Value(routing.NextVar(index))
            total_distance += routing.GetArcCostForVehicle(previous_index, index, 0)
        route.append(manager.IndexToNode(index))  # Return to depot
        route.remove(0)
        route.remove(0)
        # print(total_distance)
        print((len(route) - 2)//2)  # Number of cities (excluding the final return)
        print(" ".join(map(str, route)))

if __name__ == '__main__':
    main()