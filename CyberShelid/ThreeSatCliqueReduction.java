/** Reduces 3-SAT to CLIQUE: each vertex is a literal occurrence in a clause. */
public final class ThreeSatCliqueReduction {
    private ThreeSatCliqueReduction() { }

    public static boolean[][] buildGraph(int variableCount, int[][] clauses) {
        ThreeSatSolver.validateClauses(variableCount, clauses);
        int vertexCount = clauses.length * 3;
        boolean[][] graph = new boolean[vertexCount][vertexCount];
        for (int firstClause = 0; firstClause < clauses.length; firstClause++) {
            for (int secondClause = firstClause + 1; secondClause < clauses.length; secondClause++) {
                for (int firstLiteral = 0; firstLiteral < 3; firstLiteral++) {
                    for (int secondLiteral = 0; secondLiteral < 3; secondLiteral++) {
                        int left = clauses[firstClause][firstLiteral];
                        int right = clauses[secondClause][secondLiteral];
                        if (left != -right) {
                            int firstVertex = firstClause * 3 + firstLiteral;
                            int secondVertex = secondClause * 3 + secondLiteral;
                            graph[firstVertex][secondVertex] = true;
                            graph[secondVertex][firstVertex] = true;
                        }
                    }
                }
            }
        }
        return graph;
    }
}
