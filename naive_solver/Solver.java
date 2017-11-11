import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Collections;
import java.util.Arrays;
import java.util.Map;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Date;
import java.text.SimpleDateFormat;

public class Solver {

  private final int MAX_TRIALS = 10000000;
  ArrayList<Constraint> constraints = new ArrayList<Constraint>();
  HashMap<String, Integer> order = new HashMap<String, Integer>();
  String maxSatStr;
  int maxSat = 0;
  SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

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
   * Returns the number of constraints solved
   */
  public int solve() {
    int solved = 0;
    for (int i = 0; i < MAX_TRIALS; i ++) {
      solved = solveOnce();
      if (solved > maxSat) {
        maxSat = solved;
        maxSatStr = getCurrAnswer();
        Date date = new Date();
        System.out.print("[" + dateFormat.format(date) + "] ");
        System.out.println(maxSat);
      }
      if (solved == order.size()) {
        return constraints.size();
      }
    }
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

  public static void main(String[] args) {
    try {

      // Read and parse input file
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

      // Solves and times constraints
      Solver s =  new Solver(wizards, constraints);
      long startTime = System.currentTimeMillis();
      int solved = s.solve();
      long endTime = System.currentTimeMillis();
      double solvedTime = (endTime - startTime)/ 1000.0;

      // Prints results
      System.out.println("TOOK: " + Double.toString(solvedTime) + " seconds");
      System.out.println("MAX SAT: " + s.maxSat);
      System.out.println("CUR SAT: " + solved);
      System.out.print("MAX SOL: ");
      System.out.println(s.maxSatStr);
      System.out.print("CUR SOL: ");
      System.out.println(s.getCurrAnswer());
      System.out.print("OPT SOL: ");
      System.out.println(wizardOrder);

    } catch (Exception e) {
      System.err.format(e.getMessage());
      e.printStackTrace();
    }
  }

}
