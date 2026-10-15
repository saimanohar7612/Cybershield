/** Edit distance with caller-defined insertion, deletion, and substitution costs. */
public final class WeightedEditDistance {
    private WeightedEditDistance() { }

    public static int calculate(String first, String second,
                                int insertionCost, int deletionCost, int substitutionCost) {
        if (insertionCost < 0 || deletionCost < 0 || substitutionCost < 0) {
            throw new IllegalArgumentException("Edit costs must be non-negative");
        }

        int[][] distance = new int[first.length() + 1][second.length() + 1];
        for (int row = 0; row <= first.length(); row++) {
            distance[row][0] = row * deletionCost;
        }
        for (int column = 0; column <= second.length(); column++) {
            distance[0][column] = column * insertionCost;
        }

        for (int row = 1; row <= first.length(); row++) {
            for (int column = 1; column <= second.length(); column++) {
                int deletion = distance[row - 1][column] + deletionCost;
                int insertion = distance[row][column - 1] + insertionCost;
                int substitution = distance[row - 1][column - 1]
                        + (first.charAt(row - 1) == second.charAt(column - 1) ? 0 : substitutionCost);
                distance[row][column] = Math.min(deletion, Math.min(insertion, substitution));
            }
        }
        return distance[first.length()][second.length()];
    }
}
