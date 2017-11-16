/**
 * Created by willwang on 11/12/17.
 */
public interface Solver {

    /**
     * Attempts to find the optimal solution.
     * Returns the number of constraints solved
     */
    int solve();

    /**
     * Returns the current answer
     */
    String getCurrAnswer();

    /**
     * Returns the best answer
     */
    String getBestAnswer();

    /**
     * Returns the most constraints solved
     */
    int getMaxSat();

}
