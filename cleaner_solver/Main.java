import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Collections;

/** The MAIN class that executes SOLVER until it finds a solution or reaches
* MAXITERATIONS iterations, whichever comes first. This class reads an INPUT file
* and prints out the most optimal solution found.
*/
public class Main {
    public static void main(String[] args) {

        // Integer MAXITERATIONS that holds the maximum number of iterations.
        final int maxIterations = 1000000;

        ArrayList<Constraint> constraints = new ArrayList<>();
        String wizardOrder = "";
        int maxSat = 0;
        int removed = 0;
        HashMap<String, Integer> optOrder = new HashMap<>();
        ArrayList<String> wizardSet = new ArrayList<>();

        // Reads in file, parses into wizards and constraints
        try {
	        BufferedReader reader = new BufferedReader(new FileReader(args[0]));
            String a = reader.readLine();
            String b = reader.readLine();
            wizardOrder.split(" ");
            String line;
            while ((line = reader.readLine()) != null) {
                String[] con = line.split(" ");
                Constraint c = new Constraint(con[0], con[1], con[2]);
                if (constraints.contains(c)) {
                    System.out.printf("WARNING: Removing duplicate constraint %s is not inbetween %s and %s\n",
                            c.getC(), c.getA(), c.getB());
                    removed++;
                    continue;
                }
                constraints.add(new Constraint(con[0], con[1], con[2]));
                if (!wizardSet.contains(con[0])) {
                    wizardSet.add(con[0]);
                }
                if (!wizardSet.contains(con[1])) {
                    wizardSet.add(con[1]);
                }
                if (!wizardSet.contains(con[1])) {
                    wizardSet.add(con[1]);
                }
            }
            assert wizardSet.size() == Integer.parseInt(a);
            assert constraints.size() / 3 == Integer.parseInt(b);
            reader.close();
        } catch (Exception e) {
            System.out.println("Error while reading input file.");
            e.printStackTrace();
            return;
        }

        // Removes unnecessary constraints, checks for conflicts
        ArrayList<Constraint> removeList = new ArrayList<>();
        for (Constraint c: constraints) {
            if (c.getA().equals(c.getB()) || c.getA().equals(c.getC()) || c.getB().equals(c.getC())) {
                System.out.printf("WARNING: Removing unnecessary constraint %s is not inbetween %s and %s\n",
                        c.getC(), c.getA(), c.getB());
                removed++;
                removeList.add(c);
            } else if (constraints.contains(new Constraint(c.getC(), c.getA(), c.getB())) &&
                    constraints.contains(new Constraint(c.getC(), c.getB(), c.getA()))) {
                throw new IllegalArgumentException(String.format("ERROR: Conflicting constraints %s is not inbetween %s and %s\n",
                        c.getC(), c.getA(), c.getB()));
            }
        }
        System.out.println(removed);
        for (Constraint c: removeList) {
            constraints.remove(c);
        }

        Solver s = new Solver(wizardSet, constraints);
        long startTime = System.currentTimeMillis();
        int numIterations = 0;
        boolean completed = false;
        SolverResult result;

        // Runs solver and shuffles
        while (!completed && (numIterations < maxIterations)) {
            System.out.println("~~~~~~~~~~~~~~~~~ SHUFFLED ~~~~~~~~~~~~~~~~~");
            result = s.solve();
            completed = result.getComp();
            if (result.getSat() > maxSat) {
                maxSat = result.getSat();
                optOrder = result.getOrder();
                System.out.println("MORE OPTIMAL ORDERING FOUND. MAXSAT: " + Integer.toString(maxSat + removed));
            }
            s = new Solver(wizardSet, constraints);
            numIterations++;
        }

        // Print results
        long endTime = System.currentTimeMillis();
        double solvedTime = (endTime - startTime)/ 1000.0;
        String optOrdering;

        HashMap<Integer, String> reverseOptOrder = new HashMap<>();
        for(Map.Entry<String, Integer> entry : optOrder.entrySet()){
          reverseOptOrder.put(entry.getValue(), entry.getKey());
        }
        ArrayList<String> answerList = new ArrayList<>();
        for(int i = 0; i < optOrder.size(); i++) {
          answerList.add(reverseOptOrder.get(i));
        }
        optOrdering = String.join(" ", answerList);
        System.out.println("~~~~~~~~~~~~~~~~~ FINISHED ~~~~~~~~~~~~~~~~~");
        System.out.println("TOOK: " + Double.toString(solvedTime) + " seconds");
        System.out.println("TOTAL SATISFIED: " + Integer.toString(maxSat + removed) + "/" + Integer.toString(constraints.size() + removed));
        System.out.println("SOLUTION: ");
        System.out.println(optOrdering);
    }
}
