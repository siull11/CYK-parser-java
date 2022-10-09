package grammar;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public abstract class Grammar { // KOMMENTERA hela fil!!!
    private final int start;
    protected int numNT;
    protected HashMap<Character, Integer> ids;
    protected String[] rules;
    protected char[][] NT_to_Ts;

    public Grammar(String fileName) {
        start = 0;
        ids = new HashMap<>();
        try {
            File file = new File("resources/grammars/" + fileName);
            Scanner sc = new Scanner(file);
            ArrayList<String> rules = new ArrayList<>();
            while (sc.hasNextLine()) {
                rules.add(sc.nextLine());
            }
            this.rules = rules.toArray(String[]::new);
        } catch (Exception e) {
            System.out.println("Failed to read from file " + "resources/grammars/" + fileName + ". Error: " + e);
            System.exit(e.hashCode());
        }
    }

    public boolean canProduceTerminal(int nt, char t) {
        char[] ts = NT_to_Ts[nt];
        if (ts != null) {
            for (char t2: ts) {
                if (t == t2) {
                    return true;
                }
            }
        }
        return false;
    }

    protected void addNTtoT(int nt, char t) {
        if (NT_to_Ts[nt] == null) {
            NT_to_Ts[nt] = new char[1];
            NT_to_Ts[nt][0] = t;
        } else {
            char[] arr = new char[NT_to_Ts[nt].length + 1];
            System.arraycopy(NT_to_Ts[nt], 0, arr, 0, NT_to_Ts[nt].length);
            arr[arr.length - 1] = t;
            NT_to_Ts[nt] = arr;
        }
    }

    protected void addId(char nt) {
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

    @Override
    public String toString() {
        return "Grammar{" + "rules=" + Arrays.toString(rules) + '}';
    }
}
