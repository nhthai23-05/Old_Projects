'''
Traveling salesman problem: 1 người du lịch muốn tham quan n thành phố T1,T2,...,Tn. Hành trình xuất phát từ thành phố 1 đi qua
tất cả các thành phố còn lại, mỗi thành phố đúng 1 lần, rồi quay trở lại thành phố xuất phát. Biết cij là chi phí đi từ thành
phố Ti đến thành phố Tj (i,j = 1,2,...,n). Biết khi i = j --> cij = 0. Tìm hành trình với tổng chi phí là nhỏ nhất
------------------------------------------------------------------------------------------------------------------------------
Lời giải: x = (x[1],x[2],..,x[n]) : x[1] = 1
Hàm mục tiêu: f(x) = c[x[1],x[2]] + c[x[2],x[3]] + ... + c[x[n-1],x[n]] + c[x[n],x[1]]
'''

# Khởi tạo các biến
n = int(input()) # Số thành phố
x = [0]*(n+1) # Tập đáp án (x[1],x[2],...,x[n])
x[1] = 1
visited = [False] * (n+1) # visited[i] = False : thành phố i chưa được đi qua , = true : thành phố i đã đi qua
visited[1] = True
sigma = 0 # Tổng chi phí khi đi qua mỗi thành phố
fOPT = float('inf') # Giá trị tối ưu
c = [[0]*(n+1) for _ in range(n+1)] # c[i][j] : chi phí đi từ thành phố i sang thành phố j
c[1][1], c[1][2], c[1][3], c[1][4], c[1][5] = 0,3,14,18,16
c[2][1], c[2][2], c[2][3], c[2][4], c[2][5] = 3,0,4,22,20
c[3][1], c[3][2], c[3][3], c[3][4], c[3][5] = 17,9,0,16,4
c[4][1], c[4][2], c[4][3], c[4][4], c[4][5] = 9,20,7,0,8
c[5][1], c[5][2], c[5][3], c[5][4], c[5][5] = 9,15,11,5,0
xOPT = [0]*(n+1) # Phương án tối ưu
cmin = float('inf')
for i in range(1,n+1):
	for j in range(1,n+1):
		if i!=j and cmin > c[i][j]:
			cmin = c[i][j]

print(f'Hành trình có giá trị bé nhất (cmin) là: {cmin}')
g = 0 # cận dưới dự đoán (tổng chi phí hành trình đến thành phố hiện tại + (số hành trình còn lại+1)*cmin)

def Try(k):
	global g, sigma, fOPT
	#S1 = {1,2,...,n} x[1] = 1
	#S2 = {1,2,...,n} \ x[1] --> {2,3,...,n}
	#Sk = {1,2,...,n} \ x[1] + x[2] + ... + x[k-1]
	for i in range(2,n+1):
		if visited[i] == False:
			x[k] = i
			visited[i] = True
			sigma += c[x[k-1]][x[k]]
			if k == n:
				f = sigma + c[x[n]][x[1]]
				if f < fOPT:
					fOPT = f 
					for j in range(1,n+1):
						xOPT[j] = x[j]
			else:
				g = sigma + (n-k+1)*cmin
				if g < fOPT:
					Try(k+1)
			visited[i] = False
			sigma -= c[x[k-1]][x[k]]
Try(2)
print(f'Chi phí hành trình tốt nhất là: {fOPT}')
print(f'Đi theo thứ tự các thành phố là:')
for i in range(1,n+1):
	print(xOPT[i],end = '-->')
print(x[1])
