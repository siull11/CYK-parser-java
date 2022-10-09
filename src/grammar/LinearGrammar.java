package grammar;

import java.util.ArrayList;

public class LinearGrammar extends Grammar {
    private PairNTT[][] NT_to_NTT;
    private PairNTT[][] NT_to_TNT;

    public LinearGrammar(String fileName) {
        super(fileName);
        parseGrammar(rules);
    }

    private void parseGrammar(String[] rules) {
        super.rules = rules;
        ArrayList<String> ntLeftRules = new ArrayList<>();
        ArrayList<String> ntRightRules = new ArrayList<>();
        ArrayList<String> tRules = new ArrayList<>();

        // Categorize rules
        for (String rule: rules) {
            char nt = rule.charAt(0);
            addId(nt);

            if (rule.length() == 4) { // Produces non-terminal
                if (Character.isUpperCase(rule.charAt(2))) {
                    ntLeftRules.add(rule);
                } else {
                    ntRightRules.add(rule);
                }
                addId(rule.charAt(2));
                addId(rule.charAt(3));
            } else { // Produces terminal
                tRules.add(rule);
            }
        }

        numNT = ids.size();
        NT_to_NTT = new PairNTT[numNT][];

        // Parse non-terminal terminal rules
        for (String rule: ntLeftRules) {
            int sourceId = ids.get(rule.charAt(0));
            int resId = ids.get(rule.charAt(2));
            char resT = rule.charAt(3);

            // Fill non-terminal to non-terminal terminal
            addNTTRule(NT_to_NTT, sourceId, resId, resT);
        }

        NT_to_TNT = new PairNTT[numNT][];

        // Parse terminal non-terminal rules
        for (String rule: ntRightRules) {
            int sourceId = ids.get(rule.charAt(0));
            char resT = rule.charAt(2);
            int resId = ids.get(rule.charAt(3));

            // Fill non-terminal to terminal non-terminal
            addNTTRule(NT_to_TNT, sourceId, resId, resT);
        }

        NT_to_Ts = new char[numNT][];

        // Parse terminal rules
        for (String rule: tRules) {
            int nt = ids.get(rule.charAt(0));
            char t = rule.charAt(2);

            // Fill non-terminal to terminal
            addNTtoT(nt, t);
        }
    }

    private void addNTTRule(PairNTT[][] rules, int sourceId, int resId, char resT) {
        if (rules[sourceId] == null) {
            rules[sourceId] = new PairNTT[]{new PairNTT(resId, resT)};
        } else {
            PairNTT[] arr = new PairNTT[rules[sourceId].length + 1];
            System.arraycopy(rules[sourceId], 0, arr, 0, rules[sourceId].length);
            arr[rules[sourceId].length] = new PairNTT(resId, resT);
            rules[sourceId] = arr;
        }
    }

    public PairNTT[][] getNT_to_NTT() {
        return NT_to_NTT;
    }

    public PairNTT[][] getNT_to_TNT() {
        return NT_to_TNT;
    }

    public static class PairNTT {
        public final int nt;
        public final char t;

        public PairNTT(int nt, char t) {
            this.nt = nt;
            this.t = t;
        }
    }
}
