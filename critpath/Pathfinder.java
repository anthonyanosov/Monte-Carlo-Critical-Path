import java.util.ArrayList;
import java.util.List;

// Class to find all paths in the SAN
public class Pathfinder {
    public int nodes;
    public ArrayList<Integer>[] adjList;
    public ArrayList<ArrayList<Integer>> paths = new ArrayList<>();

    public Pathfinder(int n) {
        this.nodes = n;
        initialize();
    }

    private void initialize() {
        adjList = new ArrayList[nodes];
        for (int i = 0; i < nodes; i++) {
            adjList[i] = new ArrayList<>();
        }
    }

    public void addEdge(int start, int end) {
        adjList[start].add(end);
    }

    public void findPaths(int start, int end) {
        boolean[] isVisited = new boolean[nodes];
        ArrayList<Integer> pathList = new ArrayList<>();
        pathList.add(start);
        traverse(start, end, isVisited, pathList);
    }

    private void traverse(Integer start, Integer end, boolean[] visited, List<Integer> path) {
        if (start.equals(end)) {
            ArrayList<Integer> p = new ArrayList<>(path);
            paths.add(p);
            return;
        }

        visited[start] = true;

        for (Integer i : adjList[start]) {
            if (!visited[i]) {
                path.add(i);
                traverse(i, end, visited, path);
                path.remove(i);
            }
        }
        visited[start] = false;
    }
}
