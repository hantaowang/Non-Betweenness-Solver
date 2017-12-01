f  = open("all_submissions.txt", "r")
submissions = f.read().split("\n\n")
f.close()

for submission in submissions:
    lines = submission.strip().split("\n")
    name = lines[0].split(" ")[3]
    name = name.split("/")[-1].replace(".in", ".out")
    sol = " ".join(lines[4].split(" ")[2:])
    f = open("../all_submissions_output/outputs/" + name, "w")
    f.write(sol)
    f.close()
