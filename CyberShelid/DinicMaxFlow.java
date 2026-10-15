/** Dinic maximum-flow algorithm using BFS levels and a blocking-flow DFS. */
public final class DinicMaxFlow {
    private DinicMaxFlow() { }

    public static int maxFlow(int[][] capacity, int source, int sink) {
        validate(capacity, source, sink);
        int vertices = capacity.length;
        int[][] residual = copy(capacity);
        int[] level = new int[vertices];
        int[] nextEdge = new int[vertices];
        int totalFlow = 0;

        while (buildLevels(residual, source, sink, level)) {
            for (int i = 0; i < vertices; i++) nextEdge[i] = 0;
            int pushed;
            while ((pushed = sendFlow(residual, source, sink, Integer.MAX_VALUE, level, nextEdge)) > 0) {
                totalFlow += pushed;
            }
        }
        return totalFlow;
    }

    private static boolean buildLevels(int[][] residual, int source, int sink, int[] level) {
        for (int i = 0; i < level.length; i++) level[i] = -1;
        int[] queue = new int[level.length];
        int head = 0;
        int tail = 0;
        queue[tail++] = source;
        level[source] = 0;

        while (head < tail) {
            int current = queue[head++];
            for (int next = 0; next < level.length; next++) {
                if (level[next] == -1 && residual[current][next] > 0) {
                    level[next] = level[current] + 1;
                    queue[tail++] = next;
                }
            }
        }
        return level[sink] != -1;
    }

    private static int sendFlow(int[][] residual, int current, int sink, int available,
                                int[] level, int[] nextEdge) {
        if (current == sink) return available;
        while (nextEdge[current] < residual.length) {
            int next = nextEdge[current];
            if (residual[current][next] > 0 && level[next] == level[current] + 1) {
                int pushed = sendFlow(residual, next, sink,
                        Math.min(available, residual[current][next]), level, nextEdge);
                if (pushed > 0) {
                    residual[current][next] -= pushed;
                    residual[next][current] += pushed;
                    return pushed;
                }
            }
            nextEdge[current]++;
        }
        return 0;
    }

    private static void validate(int[][] capacity, int source, int sink) {
        int vertices = capacity.length;
        if (source < 0 || source >= vertices || sink < 0 || sink >= vertices || source == sink) {
            throw new IllegalArgumentException("Source and sink must be distinct valid vertices");
        }
        for (int[] row : capacity) {
            if (row.length != vertices) throw new IllegalArgumentException("Capacity matrix must be square");
            for (int value : row) if (value < 0) throw new IllegalArgumentException("Capacities cannot be negative");
        }
    }

    private static int[][] copy(int[][] matrix) {
        int[][] result = new int[matrix.length][matrix.length];
        for (int row = 0; row < matrix.length; row++) {
            for (int column = 0; column < matrix.length; column++) {
                result[row][column] = matrix[row][column];
            }
        }
        return result;
    }
}
