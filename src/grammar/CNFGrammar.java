package grammar;

import java.util.ArrayList;
import java.util.HashMap;

public class CNFGrammar extends Grammar {
    private int[][][] NT_to_NTs;
    private Integer[][][] NTs_to_NT;
    private HashMap<Character, Integer[]> T_to_NTs;

    public CNFGrammar(String fileName, boolean linear) {
        super(fileName);
        if (linear) parseLinearGrammar();
        else parseGrammar();
    }

    private void parseLinearGrammar() { // FIXA BUGG!!!
        HashMap<Character, Boolean> nts = new HashMap<>();
        HashMap<Character, Character> TtoNT = new HashMap<>();

        // Check if non-terminals only produces a single terminal
        for (String rule: rules) {
            char nt = rule.charAt(0);
            if (nts.containsKey(nt)) {
                nts.put(nt, nts.get(nt) && rule.length() == 3); //SÄTT T FALSE IST KSK LÖSER BUGG???
            } else {
                nts.put(nt, rule.length() == 3); // BUGG OM DEN HAR FLERA REGLER SOM GÅR TILL 1 terminal!!!
            }
        }

        // Add all non-terminal to terminal rules in data structure
        for (String rule: rules) {
            char nt = rule.charAt(0);
            if (rule.length() == 3 && nts.get(nt)) {
                TtoNT.put(rule.charAt(2), nt);
            }
        }

        ArrayList<String> rulesCNF = new ArrayList<>();

        // Create new set of rules in CNF form
        for (String rule: rules) {
            if (rule.length() == 4) { // Produces non-terminal & terminal
                char t = rule.charAt(Character.isUpperCase(rule.charAt(3)) ? 2 : 3);
                char nt = 'A';
                if (TtoNT.containsKey(t)) { // HÄR ÄR NOG NÅGOT FEL OXÅ!!!
                    nt = TtoNT.get(t);
                } else {
                    while (nts.containsKey(nt)) nt++;
                    nts.put(nt, true);
                }

                if (Character.isUpperCase(rule.charAt(3))) { // SAMMA DETTA MÅSTE NOG ÄNDRAS OXÅ
                    rulesCNF.add(rule.charAt(0) + " " + nt + rule.charAt(3));
                } else {
                    rulesCNF.add(rule.charAt(0) + " " + rule.charAt(2) + nt);
                }

                if (!TtoNT.containsKey(t)) {
                    TtoNT.put(t, nt);
                    rulesCNF.add(nt + " " + t);
                }
            } else { // Produces terminal
                rulesCNF.add(rule);
            }
        }

        // Set rules to equivalent rules in CNF form and parse
        rules = rulesCNF.toArray(String[]::new);
        parseGrammar();
    }

    private void parseGrammar() {
        ArrayList<String> ntRules = new ArrayList<>();
        ArrayList<String> tRules = new ArrayList<>();

        // Categorize rules
        for (String rule: rules) {
            char nt = rule.charAt(0);
            addId(nt);

            if (rule.length() == 4) { // Produces non-terminals
                ntRules.add(rule);
                addId(rule.charAt(2));
                addId(rule.charAt(3));
            } else { // Produces terminal
                tRules.add(rule);
            }
        }

        numNT = ids.size();
        NT_to_NTs = new int[numNT][][];
        NTs_to_NT = new Integer[numNT][numNT][];

        // Parse non-terminal rules
        for (String rule: ntRules) {
            int sourceId = ids.get(rule.charAt(0));
            int resId1 = ids.get(rule.charAt(2));
            int resId2 = ids.get(rule.charAt(3));

            // Fill non-terminal to non-terminals
            if (NT_to_NTs[sourceId] == null) {
                NT_to_NTs[sourceId] = new int[1][];
                NT_to_NTs[sourceId][0] = new int[]{resId1, resId2};
            } else {
                int[][] arr = new int[NT_to_NTs[sourceId].length + 1][];
                System.arraycopy(NT_to_NTs[sourceId], 0, arr, 0, NT_to_NTs[sourceId].length);
                arr[NT_to_NTs[sourceId].length] = new int[]{resId1, resId2};
                NT_to_NTs[sourceId] = arr;
            }

            // Fill non-terminals to non-terminal
            if (NTs_to_NT[resId1][resId2] == null) {
                NTs_to_NT[resId1][resId2] = new Integer[]{sourceId};
            } else {
                Integer[] arr = new Integer[NTs_to_NT[resId1][resId2].length + 1];
                System.arraycopy(NTs_to_NT[resId1][resId2], 0, arr, 0, NTs_to_NT[resId1][resId2].length);
                arr[arr.length - 1] = sourceId;
                NTs_to_NT[resId1][resId2] = arr;
            }
        }

        NT_to_Ts = new char[numNT][];
        T_to_NTs = new HashMap<>();

        // Parse terminal rules
        for (String rule: tRules) {
            int nt = ids.get(rule.charAt(0));
            char t = rule.charAt(2);

            // Fill non-terminal to terminal
            addNTtoT(nt, t);

            // Fill terminal to non-terminal
            if (T_to_NTs.containsKey(t)) {
                Integer[] arr = new Integer[T_to_NTs.get(t).length + 1];
                System.arraycopy(T_to_NTs.get(t), 0, arr, 0, T_to_NTs.get(t).length);
                arr[arr.length - 1] = nt;
                T_to_NTs.put(t, arr);
            } else {
                T_to_NTs.put(t, new Integer[]{nt});
            }
        }
    }

    public int[][][] getNT_to_NTs() {
        return NT_to_NTs;
    }

    public Integer[][][] getNTs_to_NT() {
        return NTs_to_NT;
    }

    public HashMap<Character, Integer[]> getT_to_NTs() {
        return T_to_NTs;
    }
}
