import random
import time

class NQueensSolver:
    def __init__(self, n):
        self.n = n
        self.queens = []  # queens[row] = column
        self.column_counts = [0] * n  # Count of queens in each column
        self.diagonal1_counts = [0] * (2 * n - 1)  # Count of queens in diagonal /
        self.diagonal2_counts = [0] * (2 * n - 1)  # Count of queens in diagonal \
        self.max_steps_without_improvement = n * 2
        
    def initialize_random_state(self):
        self.queens = [random.randint(0, self.n - 1) for _ in range(self.n)]
        self.update_conflict_counts()
        
    def update_conflict_counts(self): # Reset and recalculate all conflict counts
        self.column_counts = [0] * self.n
        self.diagonal1_counts = [0] * (2 * self.n - 1)
        self.diagonal2_counts = [0] * (2 * self.n - 1)
        
        for row, col in enumerate(self.queens):
            self.column_counts[col] += 1
            self.diagonal1_counts[row + col] += 1
            self.diagonal2_counts[row - col + self.n - 1] += 1
            
    def count_conflicts(self, row, col): # Count conflicts for a queen at given position
        conflicts = (
            self.column_counts[col] +
            self.diagonal1_counts[row + col] +
            self.diagonal2_counts[row - col + self.n - 1]
        )
        
        # Subtract self-conflicts if queen is already in this position
        if self.queens[row] == col:
            conflicts -= 3
            
        return conflicts
    
    def move_queen(self, row, new_col): # Move queen to new position and update conflict counts
        old_col = self.queens[row]
        
        # Remove old position counts
        self.column_counts[old_col] -= 1
        self.diagonal1_counts[row + old_col] -= 1
        self.diagonal2_counts[row - old_col + self.n - 1] -= 1
        
        # Add new position counts
        self.column_counts[new_col] += 1
        self.diagonal1_counts[row + new_col] += 1
        self.diagonal2_counts[row - new_col + self.n - 1] += 1
        
        self.queens[row] = new_col
        
    def get_total_conflicts(self): # Calculate total number of conflicts on the board
        total = 0
        for row in range(self.n):
            total += self.count_conflicts(row, self.queens[row])
        return total // 2  # Each conflict is counted twice
        
    def find_best_move(self): # Find move that results in fewest conflicts
        best_row = best_col = -1
        min_conflicts = float('inf')
        
        # Try all possible moves
        rows = list(range(self.n))
        random.shuffle(rows)  # Randomize order of moves
        
        for row in rows:
            current_conflicts = self.count_conflicts(row, self.queens[row])
            if current_conflicts == 0:
                continue
                
            # Try moving queen to each column in random order
            cols = list(range(self.n))
            random.shuffle(cols)
            
            for col in cols:
                if col == self.queens[row]:
                    continue
                    
                conflicts = self.count_conflicts(row, col)
                if conflicts < min_conflicts:
                    min_conflicts = conflicts
                    best_row = row
                    best_col = col
                    
                # Early exit if we found a move with no conflicts
                if conflicts == 0:
                    return best_row, best_col, min_conflicts
                    
        return best_row, best_col, min_conflicts
        
    def solve(self, max_restarts = 100): # Main solving function using hill climbing with random restarts
        for _ in range(max_restarts):
            self.initialize_random_state()
            steps_without_improvement = 0
            step = 0
            
            while steps_without_improvement < self.max_steps_without_improvement:
                current_conflicts = self.get_total_conflicts()
                print(f"Restart {_}, Step {step}, Conflicts: {current_conflicts}")

                if current_conflicts == 0:
                    return True
                    
                row, col, min_conflicts = self.find_best_move()
                if row == -1:  # No improving move found
                    break
                    
                self.move_queen(row, col)
                step += 1
                
                if min_conflicts >= current_conflicts:
                    steps_without_improvement += 1
                else:
                    steps_without_improvement = 0
                    
        return False

def solve_n_queens(n):
    solver = NQueensSolver(n)
    success = solver.solve()
    if success:
        return solver.queens
    else:
        return []

if __name__ == "__main__":
    n = int(input())
    start_time = time.time()
    solution = solve_n_queens(n)
    end_time = time.time()
    print(*solution)
    print(f"Solution found for {n} queens in {end_time - start_time:.2f} seconds")