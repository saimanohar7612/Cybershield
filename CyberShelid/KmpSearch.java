/** Knuth-Morris-Pratt exact substring matching. */
public final class KmpSearch {
    private KmpSearch() { }

    public static int count(String text, String pattern) {
        if (pattern.length() == 0) return 0;
        int[] prefix = buildPrefixTable(pattern);
        int matches = 0;
        int textIndex = 0;
        int patternIndex = 0;

        while (textIndex < text.length()) {
            if (text.charAt(textIndex) == pattern.charAt(patternIndex)) {
                textIndex++;
                patternIndex++;
                if (patternIndex == pattern.length()) {
                    matches++;
                    patternIndex = prefix[patternIndex - 1];
                }
            } else if (patternIndex > 0) {
                patternIndex = prefix[patternIndex - 1];
            } else {
                textIndex++;
            }
        }
        return matches;
    }

    private static int[] buildPrefixTable(String pattern) {
        int[] prefix = new int[pattern.length()];
        int matched = 0;
        for (int i = 1; i < pattern.length(); i++) {
            while (matched > 0 && pattern.charAt(i) != pattern.charAt(matched)) {
                matched = prefix[matched - 1];
            }
            if (pattern.charAt(i) == pattern.charAt(matched)) matched++;
            prefix[i] = matched;
        }
        return prefix;
    }
}
