# CyberShield (Java DSA project)

An educational enterprise security analytics prototype. The supplied `security_events.csv` contains 40 sample events with repeated attack phrases, malware and credential alerts, policy decisions, network activity, and benign events at varied severity levels. This gives the matching, suffix, sampling, and ranking demonstrations useful input. The code uses primitive arrays, loops, and small custom data structures; it does not use Java collection framework classes (`List`, `Map`, `Set`, `Queue`, etc.). Java I/O and basic language/runtime classes are used for CSV input/output and strings.

## Run

From this directory, compile and run with Java 17 or later:

```text
javac *.java
java CyberShieldApp security_events.csv
```

The application reads the CSV once, prints a dataset summary, and opens a menu. Select options 1–6 to run one module, 7 to run every module, or 0 to exit. It derives payload matching, suffix indexing, malware-payload edit distances, source-IP/severity summaries, severity ranking, sampling, and scan/reduce input from the loaded rows. Flow capacities, matching eligibility, SAT clauses, and rule-conflict examples are small illustrative inputs because those fields are not present in this CSV. CSV fields containing commas or quotes should use standard CSV quoting.

## Rubric map

- **M1 / CO1:** KMP, Z-function, and Rabin–Karp threat-signature matching.
- **M2 / CO2:** separate suffix-array, Kasai LCP, suffix substring-search, and suffix-automaton implementations.
- **M3 / CO3:** separate Levenshtein, Damerau-Levenshtein, weighted edit distance, bitmask rule selection, and matrix-chain optimization implementations.
- **M4 / CO4:** separate Edmonds–Karp and Dinic max-flow implementations, plus bipartite analyst-alert matching using a flow network. Ford–Fulkerson is demonstrated by the Edmonds–Karp augmenting-path method.
- **M5 / CO5:** separate 3-SAT solver, 3-SAT-to-CLIQUE reduction, exact CLIQUE search, independent-set/vertex-cover identity check, and vertex-cover 2-approximation.
- **M6 / CO6:** Miller–Rabin, randomized quicksort, reservoir sampling, deterministic scan/reduce counterparts, Monte Carlo and Las Vegas demonstrations, plus a parallel-work/span illustration of Brent's theorem.

Complexities and scope limitations are printed/documented with the corresponding algorithms. NP-hard exact demonstrations are intentionally bounded to small instances. This is a teaching project, not a production security product.
