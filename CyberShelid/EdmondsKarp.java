/** Edmonds-Karp maximum flow using BFS to find augmenting paths. */
public final class EdmondsKarp {
    private EdmondsKarp() { }

    public static int maxFlow(int[][] capacity, int source, int sink) {
        validate(capacity, source, sink);
        int vertices = capacity.length;
        int[][] residual = copy(capacity);
        int[] parent = new int[vertices];
        int[] queue = new int[vertices];
        int totalFlow = 0;

        while (true) {
            for (int i = 0; i < vertices; i++) parent[i] = -1;
            parent[source] = source;
            int head = 0;
            int tail = 0;
            queue[tail++] = source;

            while (head < tail && parent[sink] == -1) {
                int current = queue[head++];
                for (int next = 0; next < vertices; next++) {
                    if (parent[next] == -1 && residual[current][next] > 0) {
                        parent[next] = current;
                        queue[tail++] = next;
                    }
                }
            }
            if (parent[sink] == -1) break;

            int pathFlow = Integer.MAX_VALUE;
            for (int node = sink; node != source; node = parent[node]) {
                pathFlow = Math.min(pathFlow, residual[parent[node]][node]);
            }
            for (int node = sink; node != source; node = parent[node]) {
                int previous = parent[node];
                residual[previous][node] -= pathFlow;
                residual[node][previous] += pathFlow;
            }
            totalFlow += pathFlow;
        }
        return totalFlow;
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
