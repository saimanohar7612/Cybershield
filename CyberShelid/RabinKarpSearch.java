/** Rabin-Karp rolling-hash matching with exact verification on hash hits. */
public final class RabinKarpSearch {
    private static final long MODULUS = 1_000_000_007L;
    private static final long BASE = 257L;

    private RabinKarpSearch() { }

    public static int count(String text, String pattern) {
        int patternLength = pattern.length();
        if (patternLength == 0 || patternLength > text.length()) return 0;

        long patternHash = 0;
        long windowHash = 0;
        long highestPower = 1;
        for (int i = 0; i < patternLength; i++) {
            patternHash = (patternHash * BASE + pattern.charAt(i)) % MODULUS;
            windowHash = (windowHash * BASE + text.charAt(i)) % MODULUS;
            if (i < patternLength - 1) highestPower = highestPower * BASE % MODULUS;
        }

        int matches = 0;
        for (int start = 0; start <= text.length() - patternLength; start++) {
            if (patternHash == windowHash && text.regionMatches(start, pattern, 0, patternLength)) {
                matches++;
            }
            if (start < text.length() - patternLength) {
                long leading = text.charAt(start) * highestPower % MODULUS;
                windowHash = (windowHash - leading + MODULUS) % MODULUS;
                windowHash = (windowHash * BASE + text.charAt(start + patternLength)) % MODULUS;
            }
        }
        return matches;
    }
}
