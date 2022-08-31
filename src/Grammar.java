import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Grammar {
    public String[] raw;
    public int start;
    public HashMap<Character, Integer> ids;
    public ArrayList<Character> reverseIds;
    private int idCount;
    public ArrayList<ArrayList<String>> rules;
    public HashMap<Character, HashSet<Integer>> prodT;
    public HashMap<Integer, HashSet<Integer>> prodNT;

    public char start2;
    public ArrayList<String> rules2;
    public HashMap<Character, HashSet<Character>> prodT2;

    public Grammar(String[] input) {
        raw = input;
        start = 0; // First inputted nonTerminal
        idCount = 0;
        ids = new HashMap<>();
        reverseIds = new ArrayList<>();
        rules = new ArrayList<>();
        prodT = new HashMap<>();
        prodNT = new HashMap<>();

        start2 = input[0].charAt(0);
        rules2 = new ArrayList<>();
        prodT2 = new HashMap<>();

        for (String rule : input) {
            char nt = rule.charAt(0);
            String res = rule.substring(2);
            addId(nt);
            int parentId = ids.get(nt);
            rules.get(parentId).add(res);

            if (res.length() == 1) { // Produces terminal
                char prod = res.charAt(0);
                if (!prodT.containsKey(prod)) {
                    prodT.put(prod, new HashSet<>());
                }
                prodT.get(prod).add(parentId);

                //New
                if (!prodT2.containsKey(prod)) {
                    prodT2.put(prod, new HashSet<>());
                }
                prodT2.get(prod).add(nt);
            } else { // Produces non terminal

                //New
                rules2.add(rule);

                for (int i = 0; i < res.length(); i++) {
                    nt = res.charAt(i);
                    addId(nt);
                    int id = ids.get(nt);
                    if (!prodNT.containsKey(id)) {
                        prodNT.put(id, new HashSet<>());
                    }
                    prodNT.get(id).add(parentId);
                }
            }
        }
    }

    private void addId(char nt) {
        if (!ids.containsKey(nt)) {
            ids.put(nt, idCount++);
            reverseIds.add(nt);
            rules.add(new ArrayList<>());
        }
    }
}
