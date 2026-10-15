/** Levenshtein distance with adjacent-character transposition as one edit. */
public final class DamerauLevenshteinDistance {
    private DamerauLevenshteinDistance() { }

    public static int calculate(String first, String second) {
        int rows = first.length();
        int columns = second.length();
        int[][] distance = new int[rows + 1][columns + 1];

        for (int row = 0; row <= rows; row++) distance[row][0] = row;
        for (int column = 0; column <= columns; column++) distance[0][column] = column;

        for (int row = 1; row <= rows; row++) {
            for (int column = 1; column <= columns; column++) {
                int substitutionCost = first.charAt(row - 1) == second.charAt(column - 1) ? 0 : 1;
                distance[row][column] = Math.min(
                        distance[row - 1][column] + 1,
                        Math.min(distance[row][column - 1] + 1,
                                distance[row - 1][column - 1] + substitutionCost));

                if (row > 1 && column > 1
                        && first.charAt(row - 1) == second.charAt(column - 2)
                        && first.charAt(row - 2) == second.charAt(column - 1)) {
                    distance[row][column] = Math.min(distance[row][column],
                            distance[row - 2][column - 2] + 1);
                }
            }
        }
        return distance[rows][columns];
    }
}
