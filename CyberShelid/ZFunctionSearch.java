/** Z-function based exact substring matching. */
public final class ZFunctionSearch {
    private ZFunctionSearch() { }

    public static int count(String text, String pattern) {
        if (pattern.length() == 0 || text.length() < pattern.length()) return 0;
        int[] patternZ = buildZ(pattern);
        int[] z = new int[text.length()];
        int left = 0;
        int right = -1;
        int matches = 0;

        for (int i = 0; i < text.length(); i++) {
            if (i <= right) z[i] = Math.min(right - i + 1, patternZ[i - left]);
            while (z[i] < pattern.length() && i + z[i] < text.length()
                    && pattern.charAt(z[i]) == text.charAt(i + z[i])) {
                z[i]++;
            }
            if (i + z[i] - 1 > right) {
                left = i;
                right = i + z[i] - 1;
            }
            if (z[i] >= pattern.length()) matches++;
        }
        return matches;
    }

    private static int[] buildZ(String value) {
        int[] z = new int[value.length()];
        int left = 0;
        int right = -1;
        for (int i = 1; i < value.length(); i++) {
            if (i <= right) z[i] = Math.min(right - i + 1, z[i - left]);
            while (i + z[i] < value.length() && value.charAt(z[i]) == value.charAt(i + z[i])) z[i]++;
            if (i + z[i] - 1 > right) {
                left = i;
                right = i + z[i] - 1;
            }
        }
        return z;
    }
}
