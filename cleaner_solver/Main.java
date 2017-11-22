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
        /** ArrayList CONSTRAINTS that holds each constraint. */
        ArrayList<String> constraints = new ArrayList<>();
        /** String WIZARDORDER that holds the order of wizards. */
        String wizardOrder = "";
        /** Integer MAXSAT that holds the maximum number of satisfied constraints. */
        int maxSat = 0;
        /** HashMap ORDER that holds the wizard order with MAXSAT. */
        HashMap<String, Integer> optOrder = new HashMap<>();
        /** HashSet WIZARDSET to hold the different WIZARDS. */
        HashSet<String> wizardSet = new HashSet<>();
        /** Integer MAXITERATIONS that holds the maximum number of iterations. */
        int maxIterations = 1000000;

        try {
	        BufferedReader reader = new BufferedReader(new FileReader(args[0]));
            String a = reader.readLine();
            String b = reader.readLine();
            wizardOrder.split(" ");
            String line;
            while ((line = reader.readLine()) != null) {
                String[] con = line.split(" ");
                constraints.add(con[0]);
                constraints.add(con[1]);
                constraints.add(con[2]);
                wizardSet.add(con[0]);
                wizardSet.add(con[1]);
                wizardSet.add(con[2]);
            }
            assert wizardSet.size() == Integer.parseInt(a);
            assert constraints.size() / 3 == Integer.parseInt(b);
            reader.close();
        } catch (Exception e) {
            System.out.println("Error while reading input file.");
            e.printStackTrace();
            return;
        }

        Solver s = new Solver(wizardSet.toArray(new String[0]), constraints);
        long startTime = System.currentTimeMillis();
        int numIterations = 0;
        boolean completed = false;
        SolverResult result;

        while (!completed && (numIterations < maxIterations)) {
            System.out.println("~~~~~~~~~~~~~~~~~ SHUFFLED ~~~~~~~~~~~~~~~~~");
            result = s.solve();
            completed = result.getComp();
            if (result.getSat() > maxSat) {
                maxSat = result.getSat();
                optOrder = result.getOrder();
                System.out.println("MORE OPTIMAL ORDERING FOUND. MAXSAT: " + Integer.toString(maxSat));
            }
            s = new Solver(wizardSet.toArray(new String[0]), constraints);
            numIterations++;
        }
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

        System.out.println("TOOK: " + Double.toString(solvedTime) + " seconds");
        System.out.println("TOTAL SAT: " + constraints.size()/3);
        System.out.println("CUR SAT: " + maxSat);
        System.out.print("MAX SOL: ");
        System.out.println(optOrdering);
        return;
    }
}
