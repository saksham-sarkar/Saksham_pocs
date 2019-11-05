import collections
fo = open("finbranchResource_INFENG.json",'r')
ft = open("finbranchResource_PORTUGESE.json",'r')
original_lines = fo.readlines()
target_lines = ft.readlines()
origMap={}
targMap={}
bad_chars = [';', '"', '\n', ",","'"] 
#original json file lines
for i in original_lines:
	x=i.split(':')
	for j in range(0,len(x)):
		for k in bad_chars : 
			x[j] = x[j].replace(k, '') 
	if len(x)==2:
		origMap[x[0]]=x[1]
	
	if x[0].find("\n")!=-1:
		pass	

#target json file lines
for i in target_lines:
	x=i.split(':')
	for j in range(0,len(x)):
		for k in bad_chars : 
			x[j] = x[j].replace(k, '') 
	if len(x)==2:
		targMap[x[0]]=x[1]
	
	if x[0].find("\n")!=-1:
		pass		

for key in targMap:
	origMap[key] = targMap[key]

sortedMapList = sorted(origMap.items())
ignore_chars = ["\r"]
f = open("finbranchResource_INFENG.json", 'w')
try:
	f.writelines("{\n")
	l = len(sortedMapList)
	count=0
	for item in sortedMapList:
		x = item
		key = x[0]
		value = x[1]
		for k in ignore_chars:
			value  = value.replace(k, '')
		if count==l-1:
			f.writelines(''.join(['\"',key,'\"',':','\"',value,'\"','\n}']))
		else:	
			f.writelines(''.join(['\"',key,'\"',':','\"',value,'\"',',\n']))
		count = count+1
finally:
    f.close()


