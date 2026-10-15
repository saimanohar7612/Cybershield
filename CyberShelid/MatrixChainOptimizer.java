/** Finds the minimum scalar multiplication count for a matrix chain. */
public final class MatrixChainOptimizer {
    private MatrixChainOptimizer() { }

    public static long minimumMultiplications(int[] dimensions) {
        if (dimensions.length < 2) {
            throw new IllegalArgumentException("At least one matrix dimension pair is required");
        }
        int matrixCount = dimensions.length - 1;
        long[][] cost = new long[matrixCount][matrixCount];

        for (int chainLength = 2; chainLength <= matrixCount; chainLength++) {
            for (int start = 0; start + chainLength <= matrixCount; start++) {
                int end = start + chainLength - 1;
                cost[start][end] = Long.MAX_VALUE;
                for (int split = start; split < end; split++) {
                    long candidate = cost[start][split] + cost[split + 1][end]
                            + (long) dimensions[start] * dimensions[split + 1] * dimensions[end + 1];
                    if (candidate < cost[start][end]) cost[start][end] = candidate;
                }
            }
        }
        return cost[0][matrixCount - 1];
    }
}
