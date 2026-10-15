/** Minimum insertions, deletions, and substitutions to transform one string into another. */
public final class LevenshteinDistance {
    private LevenshteinDistance() { }

    public static int calculate(String first, String second) {
        return WeightedEditDistance.calculate(first, second, 1, 1, 1);
    }
}
