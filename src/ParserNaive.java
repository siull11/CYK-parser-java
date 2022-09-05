import java.util.HashSet;

public class ParserNaive implements Parser {
    private int counter;
    private Grammar g;
    private String s;
    private int n;

    @Override
    public boolean parse(Grammar g, String s) {
        this.g = g;
        this.s = s;
        this.n = s.length();
        counter = 0;
        return recursive(g.start,0);
    }

    public boolean recursive(int nt, int i) {
        char[] ts = g.NT_to_T[nt];
        if (ts != null) {
            for (char t: ts) {
                if (t == s.charAt(i)) {
                    counter++;
                    return true;
                }
            }
            return false;
        }

        int[][] nts = g.NT_to_NTs[nt];
        if (nts == null) return false;


        return false;
//        if (i == s.length()) {
//            return true;
//        }
//        char c = s.charAt(i);
//        if (!g.prodT.containsKey(c)) {
//            return false;
//        }
//        HashSet<Integer> under = g.prodT.get(c);
//        HashSet<Integer> diagonal = g.prodT.get(s.charAt(i+1));
//        HashSet<Integer> newCell = new HashSet<>();
//        for (int j: under) {
//            for (int k: diagonal) {
//                if (j == k) {
//                    newCell.add(k);
//                }
//            }
//        }
//        if (newCell.isEmpty()) {
//            return false;
//        }
//        counter++;
//        return recursive(g, s, i+1);
    }

    @Override
    public int getCounter() {
        return counter;
    }
}
