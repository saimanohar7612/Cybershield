/** Checks the identity S is independent iff V minus S is a vertex cover. */
public final class IndependentSetVertexCover {
    private IndependentSetVertexCover() { }

    public static boolean complementIdentityHolds(boolean[][] graph) {
        int vertices = graph.length;
        if (vertices > 22) throw new IllegalArgumentException("Identity check enumerates at most 22 vertices");
        int subsets = 1 << vertices;
        for (int subset = 0; subset < subsets; subset++) {
            boolean independent = true;
            boolean complementIsCover = true;
            for (int first = 0; first < vertices; first++) {
                for (int second = first + 1; second < vertices; second++) {
                    if (graph[first][second]
                            && (subset & (1 << first)) != 0
                            && (subset & (1 << second)) != 0) {
                        independent = false;
                        complementIsCover = false;
                    }
                }
            }
            if (independent != complementIsCover) return false;
        }
        return true;
    }
}
