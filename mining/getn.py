import os
path="F:\\hello.txt"

L=[]
file=open(path,"r");
for num in file:
	num=num.strip('\n')
	L.append(float(num))
	
max=0
index=0
for i in range(0,len(L)-1):
	if L[i+1]-L[i]>max:
		max=L[i+1]-L[i]
		index=i+2
		
print (max)
print (index)