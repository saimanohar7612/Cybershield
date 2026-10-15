/** Binary-searches a suffix array for a pattern. */
public final class SuffixSubstringSearch {
    private SuffixSubstringSearch() { }

    public static boolean contains(String text, int[] suffixArray, String pattern) {
        if (pattern.length() == 0) return true;
        int low = 0;
        int high = suffixArray.length - 1;
        while (low <= high) {
            int middle = (low + high) >>> 1;
            int start = suffixArray[middle];
            int end = Math.min(text.length(), start + pattern.length());
            int comparison = text.substring(start, end).compareTo(pattern);
            if (comparison == 0 && start + pattern.length() <= text.length()) return true;
            if (comparison < 0) low = middle + 1;
            else high = middle - 1;
        }
        return false;
    }
}
