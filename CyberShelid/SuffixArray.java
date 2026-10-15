/** Builds a suffix array with the prefix-doubling algorithm. */
public final class SuffixArray {
    private SuffixArray() { }

    public static int[] build(String text) {
        int length = text.length();
        int[] suffixes = new int[length];
        int[] ranks = new int[length];
        int[] nextRanks = new int[length];
        int[] mergeBuffer = new int[length];

        for (int i = 0; i < length; i++) {
            suffixes[i] = i;
            ranks[i] = text.charAt(i);
        }

        for (int width = 1; width < length; width *= 2) {
            mergeSort(suffixes, mergeBuffer, ranks, width, 0, length);
            if (length == 0) break;

            nextRanks[suffixes[0]] = 0;
            for (int i = 1; i < length; i++) {
                int previous = suffixes[i - 1];
                int current = suffixes[i];
                boolean different = ranks[previous] != ranks[current]
                        || secondRank(ranks, previous, width) != secondRank(ranks, current, width);
                nextRanks[current] = nextRanks[previous] + (different ? 1 : 0);
            }

            for (int i = 0; i < length; i++) ranks[i] = nextRanks[i];
            if (ranks[suffixes[length - 1]] == length - 1) break;
            if (width > length / 2) break;
        }
        return suffixes;
    }

    private static int secondRank(int[] ranks, int position, int width) {
        return position + width < ranks.length ? ranks[position + width] : -1;
    }

    private static void mergeSort(int[] values, int[] buffer, int[] ranks,
                                  int width, int start, int end) {
        if (end - start < 2) return;
        int middle = (start + end) / 2;
        mergeSort(values, buffer, ranks, width, start, middle);
        mergeSort(values, buffer, ranks, width, middle, end);

        int left = start;
        int right = middle;
        int output = start;
        while (left < middle || right < end) {
            if (right >= end || (left < middle
                    && compareSuffixes(values[left], values[right], ranks, width) <= 0)) {
                buffer[output++] = values[left++];
            } else {
                buffer[output++] = values[right++];
            }
        }
        for (int i = start; i < end; i++) values[i] = buffer[i];
    }

    private static int compareSuffixes(int first, int second, int[] ranks, int width) {
        if (ranks[first] != ranks[second]) return ranks[first] < ranks[second] ? -1 : 1;
        int firstSecondRank = secondRank(ranks, first, width);
        int secondSecondRank = secondRank(ranks, second, width);
        return Integer.compare(firstSecondRank, secondSecondRank);
    }
}
