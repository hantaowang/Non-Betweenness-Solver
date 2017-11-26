import os

def verify():
    with open("all_submissions.txt") as f:
        content = f.readlines()
    content = [x.strip() for x in content]
    i = 0

    done = []
    incomplete = []

    while i < len(content):
        line = content[i]
        i += 1
        if line == "":
            continue
        if line.split()[1] == 'sec]':
            name = line.split()[3]
            done.append(name)
            took = content[i].split()[1]
            total = content[i+1].split()[2]
            curr = content[i+2].split()[2]
            if total != curr:
                incomplete.append(name)
            i += 3

    print "NON OPTIMAL OUTPUTS"
    for i in incomplete:
        print i
    print "HAVE TO DO STILL"
    path = "../all_submissions"
    files = sorted([os.path.join(path, f) for f in os.listdir(path)])
    ret = []
    for i in files:
        if i not in done:
            print i
	    ret.append(i)
    return ret

if __name__ == "__main__":
    need = verify()
    need = [n.split("/")[-1] for n in need]
    print need
    all = "submission_4714775_inputs_input20.in,submission_4620699_input35.in,submission_4714637_input35.in,submission_4716196_input50.in,submission_4713010_inputs_input50.in,submission_4717517_input50.in,submission_4718546_inputs_input50.in,submission_4713488_inputs_input35.in,submission_4718142_input20.in,submission_4709244_input20.in,submission_4718256_inputs_input50.in,submission_4716790_input20.in,submission_4714907_input50.in,submission_4715088_input35.in,submission_4697862_input50.in,submission_4716027_input35.in,submission_4702791_input20.in,submission_4636404_input35.in,submission_4697451_inputs_input50.in,submission_4698322_inputs_input20.in,submission_4708042_input50.in,submission_4670106_inputs_input35.in,submission_4714220_input50.in,submission_4702273_inputs_input50.in,submission_4718820_inputs_input20.in,submission_4706128_input35.in,submission_4718744_input20.in,submission_4703229_inputs_input50.in,submission_4717115_inputs_input20.in,submission_4718783_input35.in,submission_4716271_input50.in,submission_4718636_inputs_input35.in,submission_4717524_inputs_input35.in,submission_4718738_inputs_input50.in,submission_4713185_input50.in,submission_4711880_input50.in,submission_4712152_inputs_input50.in,submission_4695143_input50.in,submission_4717902_input50.in,submission_4711776_input50.in,submission_4718714_input50.in,submission_4717187_input20.in,submission_4716723_input35.in,submission_4717371_inputs_input20.in,submission_4717644_input20.in,submission_4702665_inputs_input35.in,submission_4698322_inputs_input35.in,submission_4718727_input20.in,submission_4708042_input20.in,submission_4718689_inputs_input20.in"
    all = all.split(",")
    for i in all:
        if i in need:
            print i

