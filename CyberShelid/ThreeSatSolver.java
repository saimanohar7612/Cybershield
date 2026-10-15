/** Brute-force solver for small 3-SAT instances. Literals use signed variable numbers. */
public final class ThreeSatSolver {
    private ThreeSatSolver() { }

    public static boolean isSatisfiable(int variableCount, int[][] clauses) {
        if (variableCount < 0 || variableCount > 30) {
            throw new IllegalArgumentException("Brute-force 3-SAT supports 0 to 30 variables");
        }
        validateClauses(variableCount, clauses);
        int assignments = 1 << variableCount;
        for (int assignment = 0; assignment < assignments; assignment++) {
            boolean allClausesSatisfied = true;
            for (int[] clause : clauses) {
                boolean clauseSatisfied = false;
                for (int literal : clause) {
                    int variable = Math.abs(literal) - 1;
                    boolean value = (assignment & (1 << variable)) != 0;
                    if ((literal > 0 && value) || (literal < 0 && !value)) {
                        clauseSatisfied = true;
                        break;
                    }
                }
                if (!clauseSatisfied) {
                    allClausesSatisfied = false;
                    break;
                }
            }
            if (allClausesSatisfied) return true;
        }
        return false;
    }

    static void validateClauses(int variableCount, int[][] clauses) {
        for (int[] clause : clauses) {
            if (clause.length != 3) throw new IllegalArgumentException("Each 3-SAT clause must have 3 literals");
            for (int literal : clause) {
                if (literal == 0 || Math.abs(literal) > variableCount) {
                    throw new IllegalArgumentException("Literal refers to an invalid variable");
                }
            }
        }
    }
}
