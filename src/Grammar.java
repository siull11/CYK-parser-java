import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public abstract class Grammar { // byta från public till getters!!!
    public int start;
    public int numNT;
    public HashMap<Character, Integer> ids;
    public int[][][] NT_to_NTs;
    public int[][][] NTs_to_NT;
    public char[][] NT_to_T; // multiple nts can produce same t & one NT can produce multiple different t!!!!
    public HashMap<Character, Integer[]> T_to_NT;

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
            if (NT_to_NTs[sourceId] == null) { //GÅr nog att förbättra som för t!!!
                NT_to_NTs[sourceId] = new int[numNT][2];// kan ta bort 2 !!!
            }
            int[][] arr1 = new int[NT_to_NTs[sourceId].length + 1][2]; // kan ta bort 2 !!!
            System.arraycopy(NT_to_NTs[sourceId], 0, arr1, 0, NT_to_NTs[sourceId].length);
            arr1[NT_to_NTs[sourceId].length] = new int[]{resId1, resId2};
            NT_to_NTs[sourceId] = arr1;

            // Fill non-terminals to non-terminal
            if (NTs_to_NT[resId1][resId2] == null) { //GÅr nog att förbättra som för t!!!
                NTs_to_NT[resId1][resId2] = new int[0]; // ÄNDRA!!!
            }
            int[] arr2 = new int[NTs_to_NT[resId1][resId2].length + 1];
            System.arraycopy(NTs_to_NT[resId1][resId2], 0, arr2, 0, NTs_to_NT[resId1][resId2].length);
            arr2[arr2.length - 1] = sourceId;
            NTs_to_NT[resId1][resId2] = arr2;
        }

        NT_to_T = new char[numNT][];
        T_to_NT = new HashMap<>();

        // Parse terminal rules
        for (String rule: tRules) {
            int ntId = ids.get(rule.charAt(0));
            char t = rule.charAt(2);

            // Fill non-terminal to terminal
            if (NT_to_T[ntId] == null) {
                NT_to_T[ntId] = new char[1];
                NT_to_T[ntId][0] = t;
            } else {
                char[] arr = new char[NT_to_T[ntId].length + 1];
                System.arraycopy(NT_to_T[ntId], 0, arr, 0, NT_to_T[ntId].length);
                arr[arr.length - 1] = t;
                NT_to_T[ntId] = arr;
            }

            // Fill terminal to non-terminal
            if (T_to_NT.containsKey(t)) {
                Integer[] arr = new Integer[T_to_NT.get(t).length + 1];
                System.arraycopy(T_to_NT.get(t), 0, arr, 0, T_to_NT.get(t).length);
                arr[arr.length - 1] = ntId;
                T_to_NT.put(t, arr);
            } else {
                T_to_NT.put(t, new Integer[]{ntId});
            }
        }
    }

    private void addId(char nt) {
        if (!ids.containsKey(nt)) {
            ids.put(nt, ids.size());
        }
    }
}
