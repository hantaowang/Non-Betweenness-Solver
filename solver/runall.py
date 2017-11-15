import thread
import subprocess
import os
import math
import time
from multiprocessing import Pool

def runF(file):
    start = time.time()
    o = subprocess.check_output('java Main ' + file, shell=True)
    end = time.time()
    print "[{0:04d} sec] {1}: {2}".format(int(end - start), file, o)
    return "{0}: {1}".format(file, o)

path20 = "./phase2_inputs/inputs20/"
path35 = "./phase2_inputs/inputs35/"
path50 = "./phase2_inputs/inputs50/"

files = [os.path.join(path20, f) for f in os.listdir(path20)]
files += [os.path.join(path35, f) for f in os.listdir(path35)]
files += [os.path.join(path50, f) for f in os.listdir(path50)]
print files

poo = Pool(8) 
results = poo.map(runF, sorted(files))
print(results)
