/** Standard 2-approximation for minimum vertex cover using a maximal matching. */
public final class VertexCoverApproximation {
    private VertexCoverApproximation() { }

    public static boolean[] approximate(boolean[][] graph) {
        int vertices = graph.length;
        boolean[] cover = new boolean[vertices];
        for (int first = 0; first < vertices; first++) {
            for (int second = first + 1; second < vertices; second++) {
                if (graph[first][second] && !cover[first] && !cover[second]) {
                    cover[first] = true;
                    cover[second] = true;
                }
            }
        }
        return cover;
    }

    public static int size(boolean[] cover) {
        int count = 0;
        for (boolean selected : cover) if (selected) count++;
        return count;
    }
}
