import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Collections;
import java.util.Arrays;
import java.util.Map;
import java.io.BufferedReader;
import java.io.FileReader;

public class Solver {

  private final int MAX_TRIALS = 10000000;
  ArrayList<Constraint> constraints = new ArrayList<>();
  HashMap<String, Integer> order = new HashMap();

  public Solver(String[] wizards, ArrayList<String> constArr) {
    List<String> list = Arrays.asList(wizards);
    Collections.shuffle(list);
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
    int numSatisfied = 0;
    for (Constraint c: constraints) {
      numSatisfied += c.satisfy();
    }
    return numSatisfied;
  }

  /**
   * Attempts to final the optimal solution in MAX_TRIALS iterations
   * Returns true if so, false otherwise
   */
  public int solve() {
    for (int i = 0; i < MAX_TRIALS; i ++) {
      if (solveOnce() == order.size()) {
        return constraints.size();
      }
    }
    return solveOnce();
  }

  /**
   * Returns the current answer
   */
  public String getBestAnswer() {
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

  public static void main(String[] args) {
    try {
      BufferedReader reader = new BufferedReader(new FileReader("./input50.in"));
      reader.readLine();
      String wizardOrder = reader.readLine();
      String[] wizards = wizardOrder.split(" ");
      reader.readLine();
      String line;
      ArrayList<String> constraints = new ArrayList<>();
      while ((line = reader.readLine()) != null) {
        String[] con = line.split(" ");
        constraints.add(con[0]);
        constraints.add(con[1]);
        constraints.add(con[2]);
      }
      reader.close();
      Solver s =  new Solver(wizards, constraints);
      long startTime = System.currentTimeMillis();
      int solved = s.solve();
      long endTime = System.currentTimeMillis();
      double solvedTime = (endTime - startTime)/ 1000.0;
      System.out.println(String.format("Took %f seconds to satisfy %d constraints", solvedTime, solved));
      System.out.println(s.getBestAnswer());
      System.out.println(wizardOrder);

    } catch (Exception e) {
      System.err.format(e.getMessage());
      e.printStackTrace();
    }
  }

}
