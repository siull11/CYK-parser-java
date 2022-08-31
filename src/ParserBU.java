import java.util.HashSet;

public class ParserBU implements Parser {

    private int counter;

    @Override
    public boolean parse(Grammar g, String s) {
        counter = 0; //increment
        int len = s.length();
//        int memSize = len*(len+1)/2;
//        HashSet[] mem = new HashSet[memSize];
        HashSet[][] mem = new HashSet[len][len];

        // Go through terminals once
        for (int i = 0; i < len; i++) {
            char c = s.charAt(i);
            if (!g.prodT2.containsKey(c)) {
                return false;
            }
//            mem[i] = g.prodT.get(c);
            mem[0][i] = g.prodT2.get(c);
        }

        // Go through mem
        for (int r = 1; r < len; r++) {
            for (int c = 0; c < len-r; c++) {
                HashSet<Character> newCell = new HashSet<>();
                for (int i = 0; i < r; i++) {
                    HashSet under = mem[i][c];
                    HashSet diagonal = mem[r-i-1][c+i+1];
                    for (String rule: g.rules2) {
                        if (under.contains(rule.charAt(2)) && diagonal.contains(rule.charAt(3))) {
                            newCell.add(rule.charAt(0));
                        }
                    }
                }
                mem[r][c] = newCell;
            }
        }

        // Check if start symbol is at the top of mem
        return mem[len-1][0].contains(g.start2);
    }





//    @Override
//    public boolean parse(Grammar g, String s) {
//        int len = s.length();
//        boolean[][][] res = new boolean[len+1][len+1][26];
//
//        for (int i = 0; i < len; i++) {
//            for (int j = 0; j < g.raw.length; j++) {
//                // In form: "S s" and matches
//                if (g.raw[j].length() == 3 && g.raw[j].charAt(2) == s.charAt(i)) {
//                    res[1][i][g.raw[j].charAt(0) - 'A'] = true;
//                }
//            }
//        }
//
//        for (int i = 2; i <= len; i++) {
//            for (int j = 0; j < len-i+1; j++) {
//                for (int k = 1; k <= i-1; k++) {
//                    for (int l = 0; l < g.raw.length; l++) {
//                        if (g.raw[l].length() == 4 && res[k][j][g.raw[l].charAt(2) - 'A'] && res[i - k][j + k][g.raw[l].charAt(3) - 'A'])
//                        {
//                            res[i][j][g.raw[i].charAt(0) - 'A'] = true;
//                        }
//                    }
//                }
//            }
//        }
//
//        return res[len][0][g.reverseIds.get(g.start) - 'A'];
//    }
//    @Override
//    public boolean parse(Grammar g, String s) {
//        ArrayList<HashSet<Integer>> mem = new ArrayList<>();
//        int len = s.length();
//        counter = 0;
//
//        // Go through terminals once
//        for (int i = 0; i < len; i++) {
//            char c = s.charAt(i);
//            if (!g.prodT.containsKey(c)) {
//                return false;
//            }
//            mem.add(g.prodT.get(c));
//        }
//
//        while (counter != len) {
//            for (int i = 0; i < len-counter-1; i++) {
//                HashSet<Integer> under = mem.get(i);
//                HashSet<Integer> diagonal = mem.get(i+1);
//                HashSet<Integer> newCell = new HashSet<>();
//                System.out.println("under:"+under);
//                System.out.println("diago:"+diagonal);
//                for (int id1: under) {
//                    if (!g.prodNT.containsKey(id1)) {
//                        continue;
//                    }
//                    for (int id2: diagonal) {
//                        if (!g.prodNT.containsKey(id2)) {
//                            continue;
//                        }
//                        String res = "" + g.reverseIds.get(id1) + g.reverseIds.get(id2);
//                        for (int j = 0; j < g.rules.size(); j++) {
//                            ArrayList<String> results = g.rules.get(j);
//                            for (String result: results) {
//                                if (res.equals(result)) {
//                                    newCell.addAll(g.prodNT.get(id1));
//                                    break;
//                                }
//                            }
//                        }
//                    }
//                }
//                mem.set(i, newCell);
//            }
////            System.out.println(mem);
//            counter++;
//        }
//
//        return mem.get(0).contains(g.start);
//    }

    @Override
    public int getCounter() {
        return counter;
    }
}
