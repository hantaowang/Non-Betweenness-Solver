# Non-Betweenness Solver

### Introduction

The Betweeness problem is a well known NP-Complete algorithmic problem in order theory about ordering a collection of items subject to constraints that some items must be placed between others. Its also NP-Complete converse, the Non-Betweeness problem, constrains items such that they must NOT be place between others.

This simple probabilistic algorithm was designed for CS 170 at UC Berkeley during Spring 2017 by Anson Tsai, Hantao Wang, and Jennifer Liu. It won first place in terms of ability to efficiently solve the Non-Betweeness problem. The algorithm can solve all 991 files in `/inputs` in an hour or so when multithreaded. A document on how it works can be found here [here](https://docs.google.com/document/d/1uWx657CyZIjyAFPeXW_qFz8cP9als6RWPrsoK4nNEXE/edit?usp=sharing).

### Usage

The program requires the problem to be formatted as input files like such

    [number of variables]
    [number of constraints]
    x1 y1 z1
    x2 y2 z2
    ...
    xn yn zn
   
Where the constraint `x y z` denotes that `z` is not inbetween `x` or `y`. All variables must appear in at least one constraint (or else it wouldn't matter where that variable is in the ordering). Compile the java files and run with `java Main [path to input file]`. 

### Inputs
There are almost 1000 sample inputs of varriying difficulty to test on in the `inputs/` folder. For the most part, STAFF > INPUTS > SUBMISSION in terms of difficulty. To generate some random ones, you can use the `random_generator.py` script. 
