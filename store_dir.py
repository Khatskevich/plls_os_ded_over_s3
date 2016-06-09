import sys
import os
import subprocess
import commands

store_dir_path = sys.argv[1]

#subprocess.call(["make","clean"])

def getNameByPath(path):
    path = path.replace('/','-')
    path = path.replace('\n','-')
    path = path.replace(' ','-')
    return path

simple_size = [0]
dedublicated_size = [0]

for file in os.listdir(store_dir_path):
    filepath = store_dir_path + file
    if os.path.isfile(filepath):
        subprocess.call(['java','-jar', "target/plls_os_ded_over_s3-1.0-SNAPSHOT.jar", "store", filepath, getNameByPath(filepath)])
        simple_size.append(os.stat(filepath).st_size + simple_size[-1])
        dedublicated_size.append( int(commands.getoutput('du -s '+"valera/").split()[0]) *512 )
for file in os.listdir(store_dir_path):
    filepath = store_dir_path + file
    if os.path.isfile(filepath):
        temp_file_path = '/tmp/' + getNameByPath(file)
        #subprocess.call(['./a.out','restore', file, temp_file_path])
        subprocess.call(['java','-jar', "target/plls_os_ded_over_s3-1.0-SNAPSHOT.jar", "restore", getNameByPath(filepath), temp_file_path])
        temp_hash = commands.getoutput('shasum '+ temp_file_path).split()[0]
        file_hash = commands.getoutput('shasum '+ filepath).split()[0]
        os.remove(temp_file_path)
        print temp_hash
        if temp_hash != file_hash:
            print "Unsuccessfull file restore : " + filepath
            exit(1)
        else:
            print filepath + " Ok!"

print "Simple Dedublicated"
for i in range(0, len(simple_size)-1):
    print str(simple_size[i]) + " " + str(dedublicated_size[i])
