n=int(input())
table=[list(map(int,input().split(' '))) for i in range(n)]
for i in range(n):
    know=list(map(min,table))
    knew=list(map(max,table))
cost=[0]
Cm=min(know)
fmin=[max(knew)*n]
choice=list(range(1,n))
use=[False for i in range(n-1)]
result=[0 for i in range(n)]
def TSP(i):
    for j in range(n-1):
        if use[j]==False:
            result[i]=choice[j]
            cost[0]+=table[result[i-1]][result[i]]
            g=cost[0]+(n-i)*Cm
            use[j]=True
            if i==n-1:
                cost[0]+=table[result[i]][0]
                if cost[0]< fmin[0]:
                    fmin[0]=cost[0]
                    print(result, fmin,cost)
                cost[0]-=table[result[i]][0]
            elif g< fmin[0]:
                TSP(i+1)
            cost[0]-=table[result[i-1]][result[i]]
            use[j]=False