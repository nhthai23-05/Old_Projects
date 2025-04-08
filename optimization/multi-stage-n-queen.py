import random as rd 

n = 200
x = [0 for i in range(n)] # x[i] is the row of the queen on column i 
# constraints:  x[i] != x[j]
#               x[i] + i != x[j] + j 
#               x[i] - i != x[j] - j 
# constraint violations: number of pairs of 2 queens attacking each other 
violations = 0 # number of violations of the current solution during the local search
  
def Violations():
 v = 0
 for i in range(n):
  for j in range(i+1,n):
   if x[i] == x[j]:
    v += 1
   if x[i] + i == x[j] + j:
    v += 1
   if x[i] - i == x[j] - j:
    v += 1
 return v 

def ViolationsQueen(q): # violations of the queen q (number of other queens attacked)
 v = 0
 for i in range(n):
  if i != q:
   if x[i] == x[q]:
    v += 1 
   if x[i] + i == x[q] + q:
    v += 1
   if x[i] - i == x[q] - q:
    v += 1
 return v     
  
def SelectMostViolatingQueen():
 maxV = 0
 L = []
 # collect all most violating queens, store in a list L 
 for i in range(n):
  v = ViolationsQueen(i)
  if maxV < v:
   maxV = v
   L = []
   L.append(i)
  elif maxV == v:
   L.append(i)
   
 idx = rd.randint(0,len(L)-1) # generate randomly an index of L 
 q = L[idx] # select randomly an elements from L 
 return q   
 
def GetDelta(q,r): # return the number of violations reduced when x[q] is assigned to r
 oldV = x[q] # keep track of the current value of x[q] (row of the queen on column q)
 x[q] = r # move the queen q to the new row r 
 newViolations = Violations() # compute the violations 
 delta = newViolations - violations # compute the violations difference 
 x[q] = oldV # recover the position of the queen q 
 return delta 
 
def SelectMostPromissingRow(q):
 minDelta = 10000000
 L = []
 for r in range(n):
  delta = GetDelta(q,r)  
  if delta < minDelta:
   minDelta = delta
   L = []
   L.append(r)
  elif delta == minDelta:
   L.append(r)
 
 idx = rd.randint(0,len(L)-1) 
 sel_r = L[idx]
 return sel_r    
 
def GenerateInitialSolution():
 for q in range(n):
  x[q] = 0 # all queens are located on row 0 
  
def LocalSearch(maxIter):
 global violations
 GenerateInitialSolution()
 violations = Violations()
 for it in range(maxIter):
  if violations == 0:
   break
  q = SelectMostViolatingQueen()
  r = SelectMostPromissingRow(q)
  #print('select queen ',q,' select row ',r)
  x[q] = r # local move (replace the current solution by a neighboring solution 
  violations = Violations()
  print('Step ',it,' violations = ',violations)
   

LocalSearch(10000)