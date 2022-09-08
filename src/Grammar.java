import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public abstract class Grammar { // byta från public attr till getters!!!
    public int start;
    public int numNT;
    public HashMap<Character, Integer> ids;
    public int[][][] NT_to_NTs;
    public int[][][] NTs_to_NT;
    public char[][] NT_to_Ts;
    public HashMap<Character, Integer[]> T_to_NTs;

    public Grammar(String[] rules) {
        parseGrammar(rules);
    }

    public Grammar(String fileName) {
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
            System.out.println("Failed to read from file. Error: " + e);
            System.exit(e.hashCode());
        }
    }

    private void parseGrammar(String[] rules) {
        start = 0;
        ids = new HashMap<>();

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
        NTs_to_NT = new int[numNT][numNT][];

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
                NTs_to_NT[resId1][resId2] = new int[]{sourceId};
            } else {
                int[] arr = new int[NTs_to_NT[resId1][resId2].length + 1];
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

    private void addId(char nt) {
        if (!ids.containsKey(nt)) {
            ids.put(nt, ids.size());
        }
    }
}
