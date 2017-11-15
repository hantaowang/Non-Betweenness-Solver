import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by willwang on 11/12/17.
 */
public class Main {
    public static void main(String[] args) {
        ArrayList<String> constraints = new ArrayList<>();
        String wizardOrder = "";

        try {
            // Read and parse input file
            HashSet<String> wizardSet = new HashSet<>();
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

            // Set up the solver class
            Solver s = new Naive(wizardSet.toArray(new String[0]), constraints);

            long startTime = System.currentTimeMillis();
            int solved = s.solve();
            long endTime = System.currentTimeMillis();
            double solvedTime = (endTime - startTime)/ 1000.0;

//            Prints results
//            System.out.println("TOOK: " + Double.toString(solvedTime) + " seconds");
//            System.out.println("MAX SAT: " + s.getMaxSat());
//            System.out.println("CUR SAT: " + solved);
//            System.out.print("MAX SOL: ");
//            System.out.println(s.getBestAnswer());
//            System.out.print("CUR SOL: ");
//            System.out.println(s.getCurrAnswer());
//            System.out.print("OPT SOL: ");
//            System.out.println(wizardOrder);
            System.out.print(s.getMaxSat());
            System.out.print("/");
            System.out.print(constraints.size()/3);
            String[] fileNames = args[0].split("/");
            String fileName = fileNames[fileNames.length - 1].replace("input", "output");
            fileName = fileName.replace("in", "out");	
	    PrintWriter writer = new PrintWriter("outputs/" + fileName, "UTF-8");
            writer.print(s.getBestAnswer());
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
