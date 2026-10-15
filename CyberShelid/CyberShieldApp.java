import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class CyberShieldApp {
    private static final int MAX_ROWS = 10000;
    private static final int MAX_FIELDS = 16;

    public static void main(String[] args) {
        String path = args.length == 0 ? "security_events.csv" : args[0];
        String[][] events = loadCsv(path);
        if (events == null) return;

        int eventCount = events.length;
        String corpus = buildPayloadCorpus(events);
        System.out.println("=== CyberShield Security Analytics ===");
        System.out.println("Loaded security events: " + eventCount);
        printDatasetSummary(events);

        try (Scanner input = new Scanner(System.in)) {
            boolean running = true;
            while (running) {
                printMenu();
                System.out.print("Select an option: ");
                String choice = input.nextLine().trim();
                switch (choice) {
                    case "1": runStringAlgorithms(corpus); break;
                    case "2": runSuffixAlgorithms(corpus); break;
                    case "3": runDynamicProgramming(events); break;
                    case "4": runNetworkFlow(); break;
                    case "5": runNpAlgorithms(); break;
                    case "6": runRandomizedAlgorithms(events, corpus); break;
                    case "7": runAll(events, corpus); break;
                    case "0":
                        running = false;
                        System.out.println("CyberShield closed.");
                        break;
                    default:
                        System.out.println("Invalid choice. Enter a number from 0 to 7.");
                }
                if (running) System.out.println();
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n========== CyberShield Menu ==========");
        System.out.println("1. String algorithms");
        System.out.println("2. Suffix structures");
        System.out.println("3. Dynamic programming");
        System.out.println("4. Network flow and matching");
        System.out.println("5. NP-completeness and approximation");
        System.out.println("6. Randomized and parallel algorithms");
        System.out.println("7. Run all modules");
        System.out.println("0. Exit");
    }

    private static void runAll(String[][] events, String corpus) {
        runStringAlgorithms(corpus);
        runSuffixAlgorithms(corpus);
        runDynamicProgramming(events);
        runNetworkFlow();
        runNpAlgorithms();
        runRandomizedAlgorithms(events, corpus);
    }

    private static void runStringAlgorithms(String text) {
        System.out.println("\nM1 String matching (CSV payload corpus):");
        System.out.println("KMP 'malware': " + KmpSearch.count(text, "malware"));
        System.out.println("Z 'repeated': " + ZFunctionSearch.count(text, "repeated"));
        System.out.println("Rabin-Karp 'credential': " + RabinKarpSearch.count(text, "credential"));
        System.out.println("Complexity: KMP and Z are O(n+m); Rabin-Karp is average O(n+m).");
    }

    private static void runSuffixAlgorithms(String text) {
        System.out.println("\nM2 Suffix indexing:");
        int[] suffixArray = SuffixArray.build(text);
        int[] lcp = KasaiLcp.build(text, suffixArray);
        System.out.println("Suffix array positions (first 12): " + prefix(suffixArray, 12));
        System.out.println("Kasai max repeated prefix length: " + maximum(lcp));
        System.out.println("Substring 'attack' present: " + SuffixSubstringSearch.contains(text, suffixArray, "attack"));
        System.out.println("Suffix automaton distinct substrings: " + SuffixAutomaton.countDistinctSubstrings(text));
        System.out.println("Complexity: suffix-array prefix doubling O(n log^2 n); Kasai LCP O(n).");
    }

    private static void runDynamicProgramming(String[][] events) {
        System.out.println("\nM3 Dynamic programming:");
        String[] threatPayloads = findDifferentPayloads(events, "malware");
        System.out.println("CSV malware-payload edit distance: " + LevenshteinDistance.calculate(threatPayloads[0], threatPayloads[1]));
        System.out.println("CSV malware-payload Damerau distance: " + DamerauLevenshteinDistance.calculate(threatPayloads[0], threatPayloads[1]));
        System.out.println("CSV malware-payload weighted edit cost (insert=1, delete=2, substitute=3): "
                + WeightedEditDistance.calculate(threatPayloads[0], threatPayloads[1], 1, 2, 3));
        System.out.println("Best compatible rule subset score: "
                + BitmaskRuleSelection.bestScore(new int[]{7, 6, 5, 4}, new int[]{1, 2, 4, 8}, 7));
        System.out.println("Matrix-chain minimum scalar operations: "
                + MatrixChainOptimizer.minimumMultiplications(new int[]{30, 35, 15, 5, 10, 20, 25}));
        System.out.println("Complexity: edit-distance DP O(mn); bitmask selection O(2^r * r^2); matrix-chain DP O(n^3).");
    }

    private static void runNetworkFlow() {
        System.out.println("\nM4 Network flow and matching (illustrative input):");
        int[][] network = {
                {0, 16, 13, 0, 0, 0}, {0, 0, 10, 12, 0, 0},
                {0, 4, 0, 0, 14, 0}, {0, 0, 9, 0, 0, 20},
                {0, 0, 0, 7, 0, 4}, {0, 0, 0, 0, 0, 0}
        };
        System.out.println("Edmonds-Karp max flow: " + EdmondsKarp.maxFlow(network, 0, 5));
        System.out.println("Dinic max flow: " + DinicMaxFlow.maxFlow(network, 0, 5));
        boolean[][] eligible = {{true, true, false}, {true, false, true}, {false, true, true}};
        System.out.println("Analysts assigned to alerts (maximum matching): "
                + BipartiteMatching.maximumMatching(eligible));
        System.out.println("Complexity: Edmonds-Karp O(VE^2); Dinic O(V^2E) for general graphs.");
    }

    private static void runNpAlgorithms() {
        System.out.println("\nM5 NP-completeness and approximation (small illustrative inputs):");
        int[][] clauses = {{1, -2, 3}, {-1, 2, 3}, {1, 2, -3}};
        boolean satisfiable = ThreeSatSolver.isSatisfiable(3, clauses);
        System.out.println("Sample 3-SAT satisfiable: " + satisfiable + "; reduced CLIQUE target: " + clauses.length);
        boolean[][] graph = ThreeSatCliqueReduction.buildGraph(3, clauses);
        System.out.println("Reduced graph has target clique: " + CliqueSearch.containsClique(graph, clauses.length));
        System.out.println("Independent set / vertex cover complement identity checked: "
                + IndependentSetVertexCover.complementIdentityHolds(graph));
        boolean[][] dependencies = {
                {false, true, true, false}, {true, false, true, false},
                {true, true, false, true}, {false, false, true, false}
        };
        boolean[] cover = VertexCoverApproximation.approximate(dependencies);
        System.out.println("Vertex-cover 2-approx size: " + VertexCoverApproximation.size(cover));
        System.out.println("Complexity: exact SAT/CLIQUE search is exponential; vertex-cover approximation is O(V^2).");
    }

    private static void runRandomizedAlgorithms(String[][] events, String corpus) {
        System.out.println("\nM6 Randomized and parallel concepts:");
        long[] priorities = new long[events.length];
        int[] severity = new int[events.length];
        for (int i = 0; i < events.length; i++) {
            severity[i] = Integer.parseInt(events[i][7]);
            priorities[i] = severity[i];
        }
        RandomAlgorithms.randomQuickSort(priorities, 0, priorities.length - 1, System.nanoTime());
        System.out.println("Randomized quicksort severity ranking (ascending): " + prefix(priorities, 12));
        System.out.println("Reservoir sample (k=3 event ids): " + reservoir(events, 3, System.nanoTime()));
        System.out.println("Inclusive prefix scan of severity: " + prefix(RandomAlgorithms.scan(severity), 12));
        System.out.println("Parallel reduction severity total: " + RandomAlgorithms.reduce(severity));
        System.out.println("Miller-Rabin 104729 prime: " + RandomAlgorithms.isProbablePrime(104729L, 8, 17L));
        System.out.println("Monte Carlo simulation estimated P(head): " + RandomAlgorithms.monteCarlo(10000, 31L));
        System.out.println("Las Vegas randomized search index for 'malware': "
                + RandomAlgorithms.lasVegasFind(corpus, "malware", 19L));
        System.out.println("Brent model for W=100000, S=1000, P=8: time <= "
                + RandomAlgorithms.brentBound(100000, 1000, 8));
        System.out.println("Complexity: quicksort expected O(n log n); reservoir sampling O(n); scan/reduce O(n) work.");
    }

    private static String[][] loadCsv(String path) {
        String[][] buffer = new String[MAX_ROWS][MAX_FIELDS];
        int count = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            boolean header = true;
            while ((line = reader.readLine()) != null && count < MAX_ROWS) {
                if (header) { header = false; continue; }
                String[] fields = parseCsv(line);
                if (fields.length >= 8) buffer[count++] = fields;
            }
        } catch (IOException e) {
            System.err.println("Cannot read CSV: " + path + " (" + e.getMessage() + ")");
            return null;
        }
        String[][] events = new String[count][];
        for (int i = 0; i < count; i++) events[i] = buffer[i];
        return events;
    }

    private static String buildPayloadCorpus(String[][] events) {
        StringBuilder corpus = new StringBuilder();
        for (String[] event : events) corpus.append(event[6].toLowerCase()).append('|');
        return corpus.toString();
    }

    private static String[] parseCsv(String line) {
        String[] result = new String[MAX_FIELDS];
        int fields = 0;
        StringBuilder value = new StringBuilder();
        boolean quoted = false;
        for (int i = 0; i < line.length(); i++) {
            char character = line.charAt(i);
            if (character == '"') {
                if (quoted && i + 1 < line.length() && line.charAt(i + 1) == '"') {
                    value.append('"');
                    i++;
                } else quoted = !quoted;
            } else if (character == ',' && !quoted) {
                if (fields < MAX_FIELDS) result[fields++] = value.toString();
                value.setLength(0);
            } else value.append(character);
        }
        if (fields < MAX_FIELDS) result[fields++] = value.toString();
        String[] output = new String[fields];
        for (int i = 0; i < fields; i++) output[i] = result[i];
        return output;
    }

    private static String reservoir(String[][] events, int sampleSize, long seed) {
        int[] sample = new int[Math.min(events.length, sampleSize)];
        long state = seed;
        int used = 0;
        for (int i = 0; i < events.length; i++) {
            if (i < sampleSize) sample[used++] = i;
            else {
                state = RandomAlgorithms.next(state);
                int candidate = (int) Long.remainderUnsigned(state, i + 1L);
                if (candidate < sampleSize) sample[candidate] = i;
            }
        }
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < used; i++) {
            if (i > 0) result.append(',');
            result.append(events[sample[i]][0]);
        }
        return result.toString();
    }

    private static void printDatasetSummary(String[][] events) {
        int highSeverity = 0;
        int severityTotal = 0;
        int uniqueSources = 0;
        for (int i = 0; i < events.length; i++) {
            int severity = Integer.parseInt(events[i][7]);
            severityTotal += severity;
            if (severity >= 4) highSeverity++;
            boolean seen = false;
            for (int j = 0; j < i; j++) {
                if (events[i][2].equals(events[j][2])) { seen = true; break; }
            }
            if (!seen) uniqueSources++;
        }
        double average = events.length == 0 ? 0.0 : (double) severityTotal / events.length;
        System.out.println("Unique source IPs: " + uniqueSources);
        System.out.println("High-severity events (severity >= 4): " + highSeverity);
        System.out.println("Average event severity: " + average);
    }

    private static String[] findDifferentPayloads(String[][] events, String keyword) {
        String first = "";
        for (String[] event : events) {
            String payload = event[6].toLowerCase();
            if (payload.contains(keyword)) {
                if (first.isEmpty()) first = payload;
                else if (!first.equals(payload)) return new String[]{first, payload};
            }
        }
        return new String[]{first, first};
    }

    private static int maximum(int[] values) {
        int maximum = 0;
        for (int value : values) if (value > maximum) maximum = value;
        return maximum;
    }

    private static String prefix(int[] values, int limit) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < values.length && i < limit; i++) {
            if (i > 0) result.append(' ');
            result.append(values[i]);
        }
        return result.toString();
    }

    private static String prefix(long[] values, int limit) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < values.length && i < limit; i++) {
            if (i > 0) result.append(' ');
            result.append(values[i]);
        }
        return result.toString();
    }
}
