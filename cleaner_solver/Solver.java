import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Collections;
import java.util.Arrays;
import java.util.Date;
import java.text.SimpleDateFormat;

/** A SOLVER that iterates through all the CONSTRAINTS and tries to satisfy all
* of them. It will repeat until it finds the ORDER that satisfies all CONSTRAINTS
* or until it reaches MAXREPETITIONS iterations without any improvement, whichever
* comes first.
* Returns a SOLVERRESULT object that carries the result of SOLVER.
*/
public class Solver {
    /** ArrayList of CONSTRAINTS. */
    private ArrayList<Constraint> constraints = new ArrayList<Constraint>();
    /** HashMap ORDER that will hold WIZARDS and their placement. */
    private HashMap<String, Integer> order = new HashMap<String, Integer>();
    /** Integer MAXSAT that holds the maximum number of CONSTRAINTS satisfied. */
    private int maxSat = 0;
    /** Integer MAXREPETITIONS that defines the max number of reptitions SOLVER
    * executes without any improvement.
    */
    private int maxRepetitions = 10;
    /** SimpleDateFormat DATEFORMAT used as timestamps for each MAXSAT improvement. */
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    /** Constructor for SOLVER. Shuffles given order of WIZARDS and CONSTRAINTS. */
    public Solver(String[] wizards, ArrayList<String> constraintArray) {
        List<String> wizardList = Arrays.asList(wizards);
        Collections.shuffle(wizardList);
        for (int i =  0; i < wizards.length; i++) {
            order.put(wizardList.get(i), i);
        }

        for (int i = 0; i < constraintArray.size(); i += 3) {
            Constraint c = new Constraint(order, constraintArray.get(i), constraintArray.get(i+1), constraintArray.get(i+2));
            constraints.add(c);
        }
        Collections.shuffle(constraints);
    }

    /** Returns the number of CONSTRAINTS satisfied */
    private int numSat() {
        int numSatisfied = 0;
        for (Constraint c: constraints) {
            numSatisfied += c.satisfy();
        }
        return numSatisfied;
    }

    /** Iterates through each CONSTRAINT once and returns the number satisfied. */
    private int solveOnce() {
        for (Constraint constraint: constraints) {
            int sat = constraint.satisfy();
            if (sat == 0) {
                constraint.swap(constraint.getA(), constraint.getC());
                int swapA = numSat();
                constraint.swap(constraint.getC(), constraint.getA());
                constraint.swap(constraint.getB(), constraint.getC());
                int swapB = numSat();
                if (swapA > swapB) {
                  constraint.swap(constraint.getC(), constraint.getB());
                  constraint.swap(constraint.getA(), constraint.getC());
                }
            }
        }
        return numSat();
    }

    /** Iterates through each CONSTRAINT MAXREPETITIONS number of times and
    * returns the optimal ORDER or the maximum ORDER that satisfies the most
    * CONSTRAINTS satisfied if optimal solution was not found.
    */
    public SolverResult solve() {
        int numSolved = 0;
        int repetitions = 0;
        int iteration = 1;
        boolean completed = false;

        while (repetitions < maxRepetitions) {
            System.out.println("ITERATION " + Integer.toString(iteration));
            numSolved = solveOnce();
            if (numSolved <= maxSat) {
                repetitions++;
                if (repetitions >= maxRepetitions) {
                    break;
                }
            } else {
                maxSat = numSolved;
                repetitions = 0;
                Date date = new Date();
                System.out.print("[" + dateFormat.format(date) + "] ");
                System.out.println(maxSat);
            }
            if (numSolved == constraints.size()) {
                completed = true;
                break;
            }
            iteration++;
        }

        return new SolverResult(completed, numSat(), order);
    }
}
