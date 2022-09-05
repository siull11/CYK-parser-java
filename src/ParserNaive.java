//import java.util.HashSet;
//
//public class ParserNaive implements Parser {
//    private int counter;
//
//    @Override
//    public boolean parse(Grammar g, String s) {
//        counter = 0;
//        return recursive(g, s, 0);
//    }
//
//    public boolean recursive(Grammar g, String s, int i) {
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
//    }
//
//    @Override
//    public int getCounter() {
//        return counter;
//    }
//}
