import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class SIM {
	public static void main (String[] args)
	{
		// Check args length
		if (args.length < 3) {
			System.exit(1);
		}
		// Command line parameters
		String san = args[2];
		int reps = Integer.parseInt(args[1]);
		String trace = args[0];

		/* PHASE ONE: READ SAN GRAPH */

		// Read SAN graph
		ArrayList<Edge> edges = new ArrayList<>();
		try {
			Scanner scanner = new Scanner(new File(san));
			if (!scanner.hasNext()) {
				System.exit(1);
			}
			while (scanner.hasNext()) {
				Edge edge = new Edge(scanner.nextInt() - 1, scanner.nextInt() - 1, scanner.nextDouble());
				edges.add(edge);
			}
		} catch (FileNotFoundException e) {
			System.exit(1);
		}

		// Find largest node (to help initialize adj list and know sink node)
		int nodes = 0;
		for (Edge e : edges) {
			if (e.finish > nodes) {
				nodes = e.finish;
			}
		}

		// Model SAN graph
		Pathfinder pathfinder = new Pathfinder(nodes + 1); // nodes + 1 because arrays start at 0 but SAN file starts at 1
		for (Edge e : edges) {
			pathfinder.addEdge(e.start, e.finish);
		}
		pathfinder.findPaths(0, nodes);

		// Old paths structure reports node numbers one less than intended,
		// so this is making a copy that adds 1 to each node value to get something
		// more sensible. Note: Elements in ArrayLists are immutable.
		ArrayList<ArrayList<Integer>> paths = new ArrayList<>();
		for (ArrayList<Integer> path : pathfinder.paths) {
			ArrayList<Integer> p = new ArrayList<>();
			for (Integer n : path) {
				Integer n1 = n + 1;
				p.add(n1);
			}
			paths.add(p);
		}

		/* PHASE TWO: ASSIGN WEIGHTS */

		Graph graph = new Graph(paths, edges, trace, reps);
		graph.run();
		System.exit(0);
	}
}
