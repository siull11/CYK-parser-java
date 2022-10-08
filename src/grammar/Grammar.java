package grammar;

import java.util.HashMap;

public abstract class Grammar {
    private final int start;
    protected int numNT;
    protected HashMap<Character, Integer> ids;
    protected char[][] NT_to_Ts;

    public Grammar() {
        start = 0;
        ids = new HashMap<>();
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
}
