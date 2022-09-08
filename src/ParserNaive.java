public class ParserNaive implements Parser {
    private int counter;
    private Grammar g;
    private String s;

    @Override
    public boolean parse(Grammar g, String s) {
        this.g = g;
        this.s = s;
        counter = 0;
        return recursive(g.start, 0, s.length()-1);
    }

    private boolean recursive(int nt, int i, int j) {
        if (i == j) {
            char[] ts = g.NT_to_Ts[nt];
            if (ts != null) {
                for (char t: ts) {
                    if (s.charAt(i) == t) {
                        counter++;
                        return true;
                    }
                }
            }
            return false;
        }
        int[][] nts = g.NT_to_NTs[nt];
        if (nts != null) {
            for (int[] ntPair: nts) {
                for (int k = i; k < j; k++) {
                    if (recursive(ntPair[0], i, k) && recursive(ntPair[1], k+1, j)) {
                        counter++;
                        return true;
                    }
                }
            }
        }
        return false;
    }

//        if (nts == null || nts.length + i > n) return false;
//        if (i == n) return true;
//
//        char[] ts = g.NT_to_T[nts[i]];
//        do {
//            for (char t: ts) {
//                if (t == s.charAt(i)) {
//                    return recursive(nts, i+1);
//                }
//            }
//            ts = g.NT_to_T[nts[i]];
//        } while (ts != null);

    @Override
    public int getCounter() {
        return counter;
    }
}
