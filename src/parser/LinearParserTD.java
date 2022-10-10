package parser;

import grammar.LinearGrammar;

// CYK algorithm top-down implementation for linear grammars
public class LinearParserTD extends ParserTD {
    private final LinearGrammar g;

    public LinearParserTD(LinearGrammar g) {
        this.g = g;
    }

    @Override
    public boolean parse(String s) {
        int n = init(g, s);
        return recursive(g.getStart(), 0, n-1);
    }

    private boolean recursive(int nt, int i, int j) {
        Boolean res = baseCase(nt, i, j);
        if (res != null) return res;

        // Check all the rules that produces a non-terminal followed by a terminal
        LinearGrammar.PairNTT[] left = g.getNT_to_NTT()[nt];
        if (left != null) {
            for (LinearGrammar.PairNTT production: left) {
                if (s.charAt(j) == production.t && recursive(production.nt, i, j-1)) {
                    table[i][j][nt] = true;
                    return true;
                }
            }
        }

        // Check all the rules that produces a terminal followed by a non-terminal
        LinearGrammar.PairNTT[] right = g.getNT_to_TNT()[nt];
        if (right != null) {
            for (LinearGrammar.PairNTT production: right) {
                if (s.charAt(i) == production.t && recursive(production.nt, i+1, j)) {
                    table[i][j][nt] = true;
                    return true;
                }
            }
        }

        table[i][j][nt] = false;
        return false;
    }
}

