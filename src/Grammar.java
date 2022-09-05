import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class Grammar { // byta från public till getters!!!
    public int start;
    public int numNT;
    public HashMap<Character, Integer> ids;
    public int[][][] NT_to_NTs;
    public int[][][] NTs_to_NT;
    public char[] NT_to_T; // multiple nts can produce same t & one NT can produce multiple different t!!!!
    public HashMap<Character, Integer> T_to_NT;

    public Grammar(String[] rules) {
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
            } else {
                tRules.add(rule);
            }
        }

        numNT = ids.size();
        NT_to_NTs = new int[numNT][][];
        NTs_to_NT = new int[numNT][numNT][];

        for (String rule: ntRules) {
            int sourceId = ids.get(rule.charAt(0));
            int resId1 = ids.get(rule.charAt(2));
            int resId2 = ids.get(rule.charAt(3));

            if (NT_to_NTs[sourceId] == null) {
                NT_to_NTs[sourceId] = new int[numNT][2];
            }
            int[][] arr1 = new int[NT_to_NTs[sourceId].length + 1][2]; // kan ta bort 2 !!!
            System.arraycopy(NT_to_NTs[sourceId], 0, arr1, 0, NT_to_NTs[sourceId].length);
            arr1[NT_to_NTs[sourceId].length] = new int[]{resId1, resId2};
            NT_to_NTs[sourceId] = arr1;

            if (NTs_to_NT[resId1][resId2] == null) {
                NTs_to_NT[resId1][resId2] = new int[0];
            }
            int[] arr2 = new int[NTs_to_NT[resId1][resId2].length + 1];
            System.arraycopy(NTs_to_NT[resId1][resId2], 0, arr2, 0, NTs_to_NT[resId1][resId2].length);
            arr2[arr2.length - 1] = sourceId;
            NTs_to_NT[resId1][resId2] = arr2;
        }

        NT_to_T = new char[numNT];
        T_to_NT = new HashMap<>();

        for (String rule: tRules) {
            int ntId = ids.get(rule.charAt(0));
            char t = rule.charAt(2);

            NT_to_T[ntId] = t;
            T_to_NT.put(t, ntId);
        }
    }

    public Grammar(String fileName) {
        InputStream stream;
        try {
            stream = getClass().getClassLoader().getResourceAsStream(fileName);
        } catch (Exception e) {
            System.out.println(e.toString());
            System.exit(404);
        }
    }

    private void addId(char nt) {
        if (!ids.containsKey(nt)) {
            ids.put(nt, ids.size());
        }
    }
}
