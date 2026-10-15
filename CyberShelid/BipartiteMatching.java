/** Maximum-cardinality matching via a flow network built from an eligibility matrix. */
public final class BipartiteMatching {
    private BipartiteMatching() { }

    public static int maximumMatching(boolean[][] eligible) {
        if (eligible.length == 0) return 0;
        int analysts = eligible.length;
        int alerts = eligible[0].length;
        for (boolean[] row : eligible) {
            if (row.length != alerts) throw new IllegalArgumentException("Eligibility matrix must be rectangular");
        }

        int source = analysts + alerts;
        int sink = source + 1;
        int[][] capacity = new int[sink + 1][sink + 1];
        for (int analyst = 0; analyst < analysts; analyst++) {
            capacity[source][analyst] = 1;
            for (int alert = 0; alert < alerts; alert++) {
                if (eligible[analyst][alert]) capacity[analyst][analysts + alert] = 1;
            }
        }
        for (int alert = 0; alert < alerts; alert++) {
            capacity[analysts + alert][sink] = 1;
        }
        return EdmondsKarp.maxFlow(capacity, source, sink);
    }
}
