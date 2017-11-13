import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * Created by willwang on 11/12/17.
 */
public class Main {
    public static void main(String[] args) {
        String[] wizards = new String[0];
        ArrayList<String> constraints = new ArrayList<>();
        String wizardOrder = "";

        try {
            // Read and parse input file
            BufferedReader reader = new BufferedReader(new FileReader(args[0]));
            reader.readLine();
            wizardOrder = reader.readLine();
            wizards = wizardOrder.split(" ");
            reader.readLine();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] con = line.split(" ");
                constraints.add(con[0]);
                constraints.add(con[1]);
                constraints.add(con[2]);
            }
            reader.close();

            // Set up the solver class
            Solver s = new Naive(wizards, constraints);

            long startTime = System.currentTimeMillis();
            int solved = s.solve();
            long endTime = System.currentTimeMillis();
            double solvedTime = (endTime - startTime)/ 1000.0;

            // Prints results
            System.out.println("TOOK: " + Double.toString(solvedTime) + " seconds");
            System.out.println("MAX SAT: " + s.getMaxSat());
            System.out.println("CUR SAT: " + solved);
            System.out.print("MAX SOL: ");
            System.out.println(s.getBestAnswer());
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
