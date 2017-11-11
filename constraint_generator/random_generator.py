import random as random
import sys
alphanumeric = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890"
numConstraints = 500

# Generates n random wizards in a random order
def makeWizards(length=20):
    wizards = []
    while len(wizards) < length:
        wiz = ''.join(random.choice(alphanumeric) for i in range(10))
        if (wiz not in wizards):
            wizards.append(wiz)
    return wizards

# Chooses two random wizards in wizard
def chooseTwo(wizards):
    w1 = random.choice(wizards)
    w2 = random.choice(wizards)
    if (w1 == w2):
        w1, w2 = chooseTwo(wizards)
    return w1, w2

# Given the wizard order and the wizard at index i,
# finds two other wizards at indices j, k such that
# i is not inbetween j and k
def notInBetween(wizards, i):
    if (i <= 1):
        w1, w2 = chooseTwo(wizards[i + 1:])
    elif (i >= len(wizards) - 2):
        w1, w2 = chooseTwo(wizards[:i])
    else:
        a = len(wizards[:i])
        b = len(wizards[i+1:])
        ap = a / (a + b)
        bp = b / (a + b)
        r = random.random()
        if (r < ap):
            w1, w2 = chooseTwo(wizards[:i])
        else:
            w1, w2 = chooseTwo(wizards[i+1:])
    return (w1, w2, wizards[i])

def containsConstrain(con, constraints):
    for c in constraints:
        if con[0] == c[0] and con[1] == c[1] and con[2] == c[2]:
            return False
        if con[0] == c[1] and con[1] == c[0] and con[2] == c[2]:
            return False
    return True

# Generates 500 random constraints with at least
# one for each wizard
def makeConstraints(wizards):
    constraints = []
    for i in range(len(wizards)):
        constraints.append(notInBetween(wizards, i))
    while len(constraints) < numConstraints:
        i = random.randint(0, len(wizards) - 1)
        con = notInBetween(wizards, i)
        if (containsConstrain(con, constraints)):
            constraints.append(con)
    random.shuffle(constraints)
    return constraints

if __name__ == "__main__":
    numWizards = int(sys.argv[1])
    wizards = makeWizards(numWizards)
    constraints = makeConstraints(wizards)
    f = open('input{0}.in'.format(numWizards),'w+')
    f.write(str(numWizards) + "\n")
    wizardOrder = " ".join(wizards)
    f.write(wizardOrder  + "\n")
    f.write(str(numConstraints))
    for c in constraints:
        f.write("\n")
        f.write("{0} {1} {2}".format(c[0], c[1], c[2]))
    f.close()
