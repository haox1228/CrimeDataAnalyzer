import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * @author Nick Ma, haoxuanm
 * This is the class that used to readfile and filter by date, also is the driver of the
 * project, which will prompt user for data range and call necessary classes to solve
 * the TSP, as well as the Optimal, and ask user to generate the KML for google map
 */
public class Driver {
    public static void main(String[] args) {
        String s1, s2;
        int counter = 1;
        StringBuilder sb = new StringBuilder();
        sb.append("haoxuanm\n\n");
        boolean quit = false;
        Scanner sc = new Scanner(System.in);
        while (!quit) {

            System.out.println("Enter start date: ");
            s1 = sc.nextLine();
            System.out.println("Enter end date: ");
            s2 = sc.nextLine();
            LinkedList<String[]> ll = readCSV();
            CrimeEntry[] ll2 = filter(ll, new CrimeDate(s1), new CrimeDate(s2));
            System.out.println("Crime records between " + s1 + " and " + s2);
            System.out.println(Arrays.toString(ll2));
            System.out.println();
            WeightedGraph wg = new WeightedGraph(ll2);
            System.out.println("Hamiltonian Cycle (not necessarily optimum):");
            System.out.println(wg.hamiltonianCycle);
            double pathSum = wg.pathSumMiles(wg.hamiltonianCycle);
            System.out.println("Length Of cycle: " + pathSum + " miles");
            System.out.println();
            System.out.println("Looking at every permutation to find the optimal solution");
            System.out.println("The best permutation");
            System.out.println(wg.optimalRoute);
            System.out.println();
            double optimalSum = wg.pathSumMiles(wg.optimalRoute);
            System.out.println("Optimal cycle length = " + optimalSum);
            System.out.println("Do you want to quit? (y/n)");
            String y = sc.nextLine();
            if (y.equals("y")) {
                quit = true;
            }
            System.out.println("Do you want to generate KML for this? (y/n)");
            y = sc.nextLine();
            if (y.equals("y")) {
                KML kml = new KML(wg.hamiltonianCycle, wg.optimalRoute, ll2);
                kml.generateKML();
            }
            sb.append("TestCase").append(counter).append("\n").append("Hamilton Cycle\n")
                    .append(printLL(wg.hamiltonianCycle)).append("\nLength\n")
                    .append(pathSum).append("\nOptimum path\n").append(printLL(wg.optimalRoute))
                    .append("\nLength\n").append(optimalSum).append("\n\n");
            counter++;
        }
        try {
            File file = new File("src/result.txt");
            PrintWriter pw = new PrintWriter("src/result.txt");
            pw.println(sb.toString());
            pw.close();
        } catch (IOException e) {
            System.out.println("IO Exception");
        }
    }

    /**
     * Read the csv file
     *
     * @return the Linkedlist of string that store all the entries of the csv file , except the header
     */
    private static LinkedList<String[]> readCSV() {
        LinkedList<String[]> res = new LinkedList<>();
        Scanner sc = null;
        try {
            sc = new Scanner(new FileReader(new File("src/CrimeLatLonXY1990.csv")));
            while (sc.hasNextLine()) {
                String thisLine = sc.nextLine();
                if (thisLine.startsWith("X,")) {
                    continue;
                }
                String[] arr = thisLine.split(",");
                res.add(arr);
            }
        } catch (IOException e) {
            System.out.println("IO Exception");
        }
        return res;
    }

    /**
     * Filter out the crimes that is within the date
     *
     * @param input the total entries
     * @param start start date
     * @param end   end date
     * @return
     */
    private static CrimeEntry[] filter(LinkedList<String[]> input, CrimeDate start, CrimeDate end) {
        LinkedList<String[]> res = new LinkedList<>();
        CrimeDate cur;
        for (int i = 0; i < input.size(); i++) {
            cur = new CrimeDate(input.get((i))[5]);
            if (cur.compareTo(start) >= 0 && cur.compareTo(end) <= 0) {
                res.add(input.get(i));
            }
        }
        CrimeEntry[] ces = new CrimeEntry[res.size()];
        for (int i = 0; i < res.size(); i++) {
            ces[i] = new CrimeEntry(res.get(i));
        }
        return ces;
    }

    /**
     * Use this to print a linkedlist as excatly format
     *
     * @param ll the input linkedlist
     * @return
     */
    private static String printLL(LinkedList<Integer> ll) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < ll.size(); i++) {
            if (i == ll.size() - 1) {
                sb.append(ll.get(i));
            } else {
                sb.append(ll.get(i)).append(" ");
            }
        }
        return sb.toString();
    }

}
