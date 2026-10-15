/** Exact subset-enumeration CLIQUE solver for small graphs. */
public final class CliqueSearch {
    private CliqueSearch() { }

    public static boolean containsClique(boolean[][] graph, int targetSize) {
        int vertexCount = graph.length;
        if (targetSize <= 0) return true;
        if (targetSize > vertexCount) return false;
        if (vertexCount > 30) {
            throw new IllegalArgumentException("Brute-force CLIQUE supports at most 30 vertices");
        }

        int subsets = 1 << vertexCount;
        for (int subset = 0; subset < subsets; subset++) {
            if (bitCount(subset) < targetSize) continue;
            boolean isClique = true;
            for (int first = 0; first < vertexCount && isClique; first++) {
                if ((subset & (1 << first)) == 0) continue;
                for (int second = first + 1; second < vertexCount; second++) {
                    if ((subset & (1 << second)) != 0 && !graph[first][second]) {
                        isClique = false;
                        break;
                    }
                }
            }
            if (isClique) return true;
        }
        return false;
    }

    private static int bitCount(int value) {
        int count = 0;
        while (value != 0) {
            value &= value - 1;
            count++;
        }
        return count;
    }
}
