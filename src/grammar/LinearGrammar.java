package grammar;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public abstract class LinearGrammar {
    private int start;
    private int numNT;
    private HashMap<Character, Integer> ids;
    private PairNTLeft[][] NT_to_NTT;
    private PairNTRight[][] NT_to_TNT;
    private char[][] NT_to_Ts;

    public LinearGrammar(String fileName) {
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

    private void parseGrammar(String[] rules) {
        start = 0;
        ids = new HashMap<>();

        ArrayList<String> ntLeftRules = new ArrayList<>();
        ArrayList<String> ntRightRules = new ArrayList<>();
        ArrayList<String> tRules = new ArrayList<>();

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
        NT_to_NTT = new PairNTLeft[numNT][];

        // Parse non-terminal terminal rules
        for (String rule: ntLeftRules) {
            int sourceId = ids.get(rule.charAt(0));
            int resId = ids.get(rule.charAt(2));
            char resT = rule.charAt(3);

            // Fill non-terminal to non-terminal terminal
            if (NT_to_NTT[sourceId] == null) {
                NT_to_NTT[sourceId] = new PairNTLeft[]{new PairNTLeft(resId, resT)};
            } else {
                PairNTLeft[] arr = new PairNTLeft[NT_to_NTT[sourceId].length + 1];
                System.arraycopy(NT_to_NTT[sourceId], 0, arr, 0, NT_to_NTT[sourceId].length);
                arr[NT_to_NTT[sourceId].length] = new PairNTLeft(resId, resT);
                NT_to_NTT[sourceId] = arr;
            }
        }

        NT_to_TNT = new PairNTRight[numNT][];

        // Parse terminal non-terminal rules
        for (String rule: ntRightRules) {
            int sourceId = ids.get(rule.charAt(0));
            char resT = rule.charAt(2);
            int resId = ids.get(rule.charAt(3));

            // Fill non-terminal to terminal non-terminal
            if (NT_to_TNT[sourceId] == null) {
                NT_to_TNT[sourceId] = new PairNTRight[]{new PairNTRight(resT, resId)};
            } else {
                PairNTRight[] arr = new PairNTRight[NT_to_TNT[sourceId].length + 1];
                System.arraycopy(NT_to_TNT[sourceId], 0, arr, 0, NT_to_TNT[sourceId].length);
                arr[NT_to_TNT[sourceId].length] = new PairNTRight(resT, resId);
                NT_to_TNT[sourceId] = arr;
            }
        }

        NT_to_Ts = new char[numNT][];

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
        }
    }

    private void addId(char nt) {
        if (!ids.containsKey(nt)) {
            ids.put(nt, ids.size());
        }
    }

    public int getStart() {
        return start;
    }

    public int getNumNT() {
        return numNT;
    }

    public PairNTLeft[][] getNT_to_NTT() {
        return NT_to_NTT;
    }

    public PairNTRight[][] getNT_to_TNT() {
        return NT_to_TNT;
    }

    public char[][] getNT_to_Ts() {
        return NT_to_Ts;
    }

    public class PairNTLeft {
        public final int nt;
        public final char t;

        public PairNTLeft(int nt, char t) {
            this.nt = nt;
            this.t = t;
        }
    }

    public class PairNTRight {
        public final char t;
        public final int nt;

        public PairNTRight(char t, int nt) {
            this.t = t;
            this.nt = nt;
        }
    }
}
