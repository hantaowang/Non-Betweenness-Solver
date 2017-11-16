import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Collections;
import java.util.Arrays;
import java.util.Map;
import java.util.Date;
import java.text.SimpleDateFormat;

public class Naive implements Solver {

  // Whether to run to completion
  private boolean INFINITE_TRIALS = false;

  private final int MAX_TRIALS = 1000000000;
  private ArrayList<Constraint> constraints = new ArrayList<Constraint>();
  private HashMap<String, Integer> order = new HashMap<String, Integer>();
  private String maxSatStr;
  public int maxSat = 0;
  private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

  public Naive(String[] wizards, ArrayList<String> constArr) {
    List<String> list = Arrays.asList(wizards);
    //Collections.shuffle(list);
    //Collections.shuffle(constArr);
    for (int i = 0; i < wizards.length; i++) {
      order.put(list.get(i), i);
    }

    for (int i = 0; i < constArr.size(); i+=3) {
      Constraint c = new Constraint(order, constArr.get(i), constArr.get(i+1), constArr.get(i+2));
      constraints.add(c);
    }
  }

  /**
   * Goes through each constraint once.
   * Returns the number of constraints satisfied.
   */
  private int solveOnce() {
    for (Constraint con: constraints) {
      int x = con.satisfy();
      if (x == 0) {
        con.swap(con.a, con.c);
        int swapA = numSat();
        con.swap(con.c, con.a);
        con.swap(con.b, con.c);
        int swapB = numSat();
        if (swapA > swapB) {
          con.swap(con.c, con.b);
          con.swap(con.a, con.c);
        }
      }
    }
    return numSat();
  }

  private int numSat() {
    int numSatisfied = 0;
    for (Constraint con: constraints) {
      numSatisfied += con.satisfy();
    }
    return numSatisfied;
  }

  /**
   * Attempts to find the optimal solution in MAX_TRIALS iterations
   * Returns the number of constraints solved
   */
  public int solve() {
    int solved = 0;
    int i = 0;
    int repeat = 0;
    while (i < MAX_TRIALS) {
      System.out.println("ITERATION " + Integer.toString(i));
      solved = solveOnce();
      if (solved <= maxSat) {
        repeat++;
        if (repeat >= 50) {
          throw new RuntimeException();
        }
      }
      if (solved > maxSat) {
        maxSat = solved;
        repeat = 0;
        Date date = new Date();
        System.out.print("[" + dateFormat.format(date) + "] ");
        System.out.println(maxSat);
      }
      if (solved == constraints.size()) {
        maxSatStr = getCurrAnswer();
        return constraints.size();
      }
      if (!INFINITE_TRIALS) {
        i++;
      }
    }
    maxSatStr = getCurrAnswer();
    return solved;
  }

  /**
   * Returns the current answer
   */
  public String getCurrAnswer() {
    HashMap<Integer, String> myNewHashMap = new HashMap<>();
    for(Map.Entry<String, Integer> entry : order.entrySet()){
      myNewHashMap.put(entry.getValue(), entry.getKey());
    }
    ArrayList<String> answerList = new ArrayList<>();
    for(int i = 0; i < order.size(); i++) {
      answerList.add(myNewHashMap.get(i));
    }
    return String.join(" ", answerList);
  }

  /**
   * Returns the best answer
   */
  public String getBestAnswer() {
    return maxSatStr;
  }

  /**
   * Returns the best answer
   */
  public int getMaxSat() {
    return maxSat;
  }

}
