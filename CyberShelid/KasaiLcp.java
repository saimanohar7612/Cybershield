/** Computes the LCP array in suffix-array order using Kasai's algorithm. */
public final class KasaiLcp {
    private KasaiLcp() { }

    public static int[] build(String text, int[] suffixArray) {
        int length = text.length();
        if (suffixArray.length != length) {
            throw new IllegalArgumentException("Suffix array length must match text length");
        }

        int[] inverse = new int[length];
        int[] lcp = new int[length];
        for (int rank = 0; rank < length; rank++) inverse[suffixArray[rank]] = rank;

        int commonLength = 0;
        for (int position = 0; position < length; position++) {
            int rank = inverse[position];
            if (rank == 0) continue;

            int previousSuffix = suffixArray[rank - 1];
            while (position + commonLength < length
                    && previousSuffix + commonLength < length
                    && text.charAt(position + commonLength)
                    == text.charAt(previousSuffix + commonLength)) {
                commonLength++;
            }
            lcp[rank] = commonLength;
            if (commonLength > 0) commonLength--;
        }
        return lcp;
    }
}
