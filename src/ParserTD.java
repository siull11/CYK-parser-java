//import java.util.Arrays;
//import java.util.HashSet;
//
//public class ParserTD implements Parser {
//
//    private int counter;
//    private Grammar g;
//    private String s;
//    private int n;
//    private HashSet<Integer>[][] res;
//    @Override
//    public boolean parse(Grammar g, String s) {
//        this.g = g;
//        this.s = s;
//        n = s.length();
//        res = new Array<>[n][n];
//
//        if (n == 1) return canProduceTerminal(g.start, s.charAt(0));
//        res[0][n-1] = new HashSet<>(g.start);
//
//        System.out.println(Arrays.deepToString(res));
//        return recursive(0, n-1);
//    }
//
//    private boolean recursive(int x, int y) {
//        if (res[x][y] == null) return false;
//        if (y == 0) {
//            for (int nt: res[x][y]) {
//                if (canProduceTerminal(nt, s.charAt(x))) return true;
//            }
//            return false;
//        }
//
//        for (int nt: res[x][y]) {
//            for (int[] ntPair: g.NT_to_NTs[nt]) {
//                res[x][y-1].add(ntPair[0]);
//                res[x+1][y-1].add(ntPair[1]);
//            }
//        }
//
//
//        return false;
//    }
//
//    private boolean canProduceTerminal(int nt, char t) {
//        char[] ts = g.NT_to_T[nt];
//        if (ts != null) {
//            for (char t2: ts) {
//                if (t == t2) {
//                    return true;
//                }
//            }
//        }
//        return false;
//    }
//
//    @Override
//    public int getCounter() {
//        return counter;
//    }
//}
