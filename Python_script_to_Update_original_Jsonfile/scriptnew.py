import time
import collections
start = time.time()
fo = open("original.json",'r')
ft = open("custom.json",'r')
original_lines = fo.readlines()
target_lines = ft.readlines()
origMap={}
targMap={}
bad_chars = [';', '"', '\n', ","," ","'"] 
#print(original_lines)
for i in original_lines:
	#print(i)
	x=i.split(':')
	for j in range(0,len(x)):
		for k in bad_chars : 
			x[j] = x[j].replace(k, '') 
	if len(x)==2:
		origMap[x[0]]=x[1]
	
	if x[0].find("\n")!=-1:
		pass	

#print(target_lines)
for i in target_lines:
	#print(i)
	x=i.split(':')
	for j in range(0,len(x)):
		for k in bad_chars : 
			x[j] = x[j].replace(k, '')
	if len(x)==2:
		targMap[x[0]]=x[1]
	
	if x[0].find("\n")!=-1:
		pass	
	
for key in origMap:
	print(key,origMap[key])
for key in targMap:
	print(key,targMap[key])	
print("original Map")	
print(origMap)
print("target Map")
print(targMap)

for key in targMap:
	origMap[key] = targMap[key]
	
#sortedMap = {}
origMap = collections.OrderedDict(sorted(origMap.items()))
#for key in sorted(origMap.keys()):
    #sortedMap[key] = origMap[key]
	
print("After modification")
print(origMap)	
print("**************************************************")
f = open("xyz.json", 'w')
try:
	f.writelines("{\n")
	l = len(origMap)
	count=0
	for key in origMap:
		if count==l-1:
			f.writelines(''.join(['\"',key,'\"',' : ','\"',origMap[key],'\"','\n}']))
		else:	
			f.writelines(''.join(['\"',key,'\"',' : ','\"',origMap[key],'\"',',\n']))
		count = count+1
finally:
    f.close()
end = time.time()
print("The execution time = ")
print(end-start)
	

#with open("xyz.json", "w") as write_file:
    #json.dump(origMap, write_file)


#['{\n', '"1": "Pradeep",\n', ' "2": "Sarkar", \n', ' "3": "xyz", \n', ' "4": "abc", \n', ' "13": "Hello"\n', ' }\n', ' \n']

