import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Graph {
    public Integer reps;
    public String trace;
    public ArrayList<ArrayList<Integer>> paths;
    public ArrayList<Edge> edges;
    public Map<ArrayList<Integer>, Double> results = new HashMap<>();
    public Map<ArrayList<Integer>, Integer> probabilities = new HashMap<>();


    public Graph(ArrayList<ArrayList<Integer>> p, ArrayList<Edge> e, String t, Integer r) {
        paths = p;
        edges = e;
        trace = t;
        reps = r;
        for (ArrayList<Integer> key : p) {
            probabilities.put(key, 0);
        }
    }

    public void run() {
        // Initialize map
        for (ArrayList<Integer> path : paths) {
            results.put(path, 0.0);
        }
        // Trials
        try {
            Scanner scanner = new Scanner(new File(trace));
            for (int i = 0; i < reps; i++) {
                for (Edge e : edges) {
                    if (scanner.hasNext()) {
                        e.finalWeight = e.weight * scanner.nextDouble();
                    } else {
                        System.exit(1);
                    }
                }
                weighPaths();
            }
            scanner.close();
            printPaths();
        } catch (FileNotFoundException e) {
            System.exit(1);
        }
    }

    private void weighPaths() {
        for (ArrayList<Integer> path : paths) {
            double total = 0;
            for (int i = 0; i < path.size(); i++) {
                if (i < path.size() - 1) {
                    for (Edge e : edges) {
                        if (e.start + 1 == path.get(i) && e.finish + 1 == path.get(i + 1)) {
                            total += e.finalWeight;
                        }
                    }
                }
            }
            results.put(path, total);
        }
        ArrayList<Integer> best = results.entrySet().stream()
                .max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1)
                .get()
                .getKey();

        double top = results.get(best);

        for (ArrayList<Integer> key : results.keySet()) {
            if (results.get(key).equals(top)) {
                probabilities.put(key, probabilities.get(key) + 1);
            }
        }
    }

    private void printPaths() {
        for (ArrayList<Integer> p : paths) {
            System.out.print("OUTPUT " + ":");
            for (int i = 0; i < p.size(); i++) {
                if (i < p.size() - 2) {
                    System.out.print("a" + p.get(i) + "/" + p.get(i + 1) + ",");
                } else if (i < p.size() - 1) {
                    System.out.print("a" + p.get(i) + "/" + p.get(i + 1));
                }
            }
            Formatter formatter = new Formatter();
            formatter.format("%6.5e", probabilities.get(p) / (double) reps);
            System.out.print(": " + formatter + "\n");
        }
    }
}