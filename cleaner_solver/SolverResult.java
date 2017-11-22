import java.util.HashMap;

/** A SOLVERRESULT object that contains a tuple COMPLETED, Integer SATISFIED,
* and a HashMap ORDER. Used to transfer results from SOLVER to MAIN.
*/
public class SolverResult {
    /** Boolean COMPLETED. */
    private boolean completed;
    /** Integer SATISFIED. */
    private int satisfied;
    /** HashMap ORDER. */
    private HashMap<String, Integer> order;

    /** Constructor for SOLVERRESULT. */
    public SolverResult(boolean completed, int satisfied, HashMap<String, Integer> order) {
        this.completed = completed;
        this.satisfied = satisfied;
        this.order = order;
    }

    /** Returns boolean COMPELTED. */
    public boolean getComp() {
        return completed;
    }

    /** Returns integer SATISFIED. */
    public int getSat() {
        return satisfied;
    }

    /** Returns HashMap ORDER. */
    public HashMap<String, Integer> getOrder() {
        return order;
    }
}
