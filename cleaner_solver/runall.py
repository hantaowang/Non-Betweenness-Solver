import thread
import subprocess
import os
import math
import time
from multiprocessing import Pool
from verify import verify


done = []
finished = 0
path = "../all_submissions"
files = sorted([os.path.join(path, f) for f in os.listdir(path)])

def runF(fileNum):
    file = files[fileNum]
    start = time.time()
    o = subprocess.check_output('java Main ' + file, shell=True)
    end = time.time()
    print "[{0:05d} sec] {1}: {2}".format(int(end - start), fileNum, file)
    print o
    done.append(files)

files = verify()
print files

poo = Pool(8)
try:
    results = poo.map(runF, reversed(range(len(files))))
    print results
except Exception as e:
    print e
    print done
    undone = [f for f in files if f not in done]
    print undone
