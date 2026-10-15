/** Counts distinct substrings using a suffix automaton with array-based transitions. */
public final class SuffixAutomaton {
    private static final int ALPHABET_SIZE = 128;

    private SuffixAutomaton() { }

    public static long countDistinctSubstrings(String text) {
        int capacity = 2 * text.length() + 1;
        int[] suffixLink = new int[capacity];
        int[] stateLength = new int[capacity];
        int[][] transition = new int[capacity][ALPHABET_SIZE];
        for (int state = 0; state < capacity; state++) {
            suffixLink[state] = -1;
            for (int character = 0; character < ALPHABET_SIZE; character++) {
                transition[state][character] = -1;
            }
        }

        int stateCount = 1;
        int lastState = 0;
        for (int i = 0; i < text.length(); i++) {
            int character = text.charAt(i);
            if (character >= ALPHABET_SIZE) {
                throw new IllegalArgumentException("Suffix automaton input must use ASCII characters");
            }

            int current = stateCount++;
            stateLength[current] = stateLength[lastState] + 1;
            int previous = lastState;
            while (previous != -1 && transition[previous][character] == -1) {
                transition[previous][character] = current;
                previous = suffixLink[previous];
            }

            if (previous == -1) {
                suffixLink[current] = 0;
            } else {
                int target = transition[previous][character];
                if (stateLength[previous] + 1 == stateLength[target]) {
                    suffixLink[current] = target;
                } else {
                    int clone = stateCount++;
                    stateLength[clone] = stateLength[previous] + 1;
                    suffixLink[clone] = suffixLink[target];
                    for (int c = 0; c < ALPHABET_SIZE; c++) {
                        transition[clone][c] = transition[target][c];
                    }
                    while (previous != -1 && transition[previous][character] == target) {
                        transition[previous][character] = clone;
                        previous = suffixLink[previous];
                    }
                    suffixLink[target] = clone;
                    suffixLink[current] = clone;
                }
            }
            lastState = current;
        }

        long total = 0;
        for (int state = 1; state < stateCount; state++) {
            total += stateLength[state] - stateLength[suffixLink[state]];
        }
        return total;
    }
}
