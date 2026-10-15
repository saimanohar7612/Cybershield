/** Enumerates subsets of security rules under a budget and bitmask conflicts. */
public final class BitmaskRuleSelection {
    private BitmaskRuleSelection() { }

    public static int bestScore(int[] scores, int[] conflictMasks, int budget) {
        if (scores.length != conflictMasks.length) {
            throw new IllegalArgumentException("Each rule needs a score and conflict mask");
        }
        if (scores.length > 30) {
            throw new IllegalArgumentException("Bitmask subset search supports at most 30 rules");
        }

        int best = 0;
        int subsets = 1 << scores.length;
        for (int selected = 0; selected < subsets; selected++) {
            int cost = 0;
            int score = 0;
            boolean valid = true;

            for (int rule = 0; rule < scores.length; rule++) {
                if ((selected & (1 << rule)) != 0) {
                    cost += conflictMasks[rule];
                    score += scores[rule];
                }
            }
            if (cost > budget) valid = false;

            for (int first = 0; first < scores.length && valid; first++) {
                if ((selected & (1 << first)) == 0) continue;
                for (int second = first + 1; second < scores.length; second++) {
                    if ((selected & (1 << second)) != 0
                            && (conflictMasks[first] & conflictMasks[second]) != 0) {
                        valid = false;
                        break;
                    }
                }
            }
            if (valid && score > best) best = score;
        }
        return best;
    }
}
