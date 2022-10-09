package grammar;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public abstract class CNFGrammar extends Grammar { //REFACTORISERA???
    private int[][][] NT_to_NTs;
    private Integer[][][] NTs_to_NT;
    private HashMap<Character, Integer[]> T_to_NTs;

    public CNFGrammar(String[] rules) {
        parseGrammar(rules);
    } //Flytta dessa till superklass???

    public CNFGrammar(String fileName) { //Flytta dessa till superklass???
        try {
            File file = new File(fileName);
            Scanner sc = new Scanner(file);
            ArrayList<String> rules = new ArrayList<>();
            while (sc.hasNextLine()) {
                rules.add(sc.nextLine());
            }
            String[] a = rules.toArray(String[]::new);
            parseGrammar(a);
        } catch (Exception e) {
            System.out.println("Failed to read from file " + fileName + ". Error: " + e);
            System.exit(e.hashCode());
        }
    }

    public CNFGrammar(String fileName, boolean linear) { //Flytta dessa till superklass???
        try {
            File file = new File(fileName);
            Scanner sc = new Scanner(file);
            ArrayList<String> rules = new ArrayList<>();
            while (sc.hasNextLine()) {
                rules.add(sc.nextLine());
            }
            String[] a = rules.toArray(String[]::new);
            parseLinearGrammar(a);
        } catch (Exception e) {
            System.out.println("Failed to read from file " + fileName + ". Error: " + e);
            System.exit(e.hashCode());
        }
    }

    private void parseLinearGrammar(String[] rules) { // KOMMETERA!!!
        HashMap<Character, Boolean> nts = new HashMap<>();
        HashMap<Character, Character> TtoNT = new HashMap<>();

        for (String rule: rules) {
            char nt = rule.charAt(0);
            if (nts.containsKey(nt)) {;
                nts.put(nt, nts.get(nt) && rule.length() == 3);
            } else {
                nts.put(nt, rule.length() == 3);
            }
        }

        for (String rule: rules) {
            char nt = rule.charAt(0);
            if (rule.length() == 3 && nts.get(nt)) {
                TtoNT.put(rule.charAt(2), nt);
            }
        }

        ArrayList<String> rulesCNF = new ArrayList<>();

        for (String rule: rules) {
            if (rule.length() == 4) { // Produces non-terminal & terminal
                char t = rule.charAt(Character.isUpperCase(rule.charAt(3)) ? 2 : 3);
                char nt = 'A';
                if (TtoNT.containsKey(t)) {
                    nt = TtoNT.get(t);
                } else {
                    while (nts.containsKey(nt)) nt++;
                    nts.put(nt, true);
                }

                if (Character.isUpperCase(rule.charAt(3))) {
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

        parseGrammar(rulesCNF.toArray(String[]::new));
    }

    private void parseGrammar(String[] rules) {
        super.rules = rules;
        ArrayList<String> ntRules = new ArrayList<>();
        ArrayList<String> tRules = new ArrayList<>();

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
            int ntId = ids.get(rule.charAt(0));
            char t = rule.charAt(2);

            // Fill non-terminal to terminal
            if (NT_to_Ts[ntId] == null) {
                NT_to_Ts[ntId] = new char[1];
                NT_to_Ts[ntId][0] = t;
            } else {
                char[] arr = new char[NT_to_Ts[ntId].length + 1];
                System.arraycopy(NT_to_Ts[ntId], 0, arr, 0, NT_to_Ts[ntId].length);
                arr[arr.length - 1] = t;
                NT_to_Ts[ntId] = arr;
            }

            // Fill terminal to non-terminal
            if (T_to_NTs.containsKey(t)) {
                Integer[] arr = new Integer[T_to_NTs.get(t).length + 1];
                System.arraycopy(T_to_NTs.get(t), 0, arr, 0, T_to_NTs.get(t).length);
                arr[arr.length - 1] = ntId;
                T_to_NTs.put(t, arr);
            } else {
                T_to_NTs.put(t, new Integer[]{ntId});
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