import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public abstract class Grammar {
    private final HashMap<Character, Integer> nonTerminals;
    private final ArrayList<ArrayList<String>> rules;

    public Grammar() {
        nonTerminals = new HashMap<>();
        rules = new ArrayList<>();
    }

    public abstract void read(String[] rules);

    public abstract void read(String fileName);

    public HashMap<Character, Integer> getNonTerminals() {
        return nonTerminals;
    }

    public ArrayList<ArrayList<String>> getRules() {
        return rules;
    }

    public ArrayList<String> getRule(char nonTerminal) {
        try { // Ksk ändra till if-sats ???
            return rules.get(nonTerminals.get(nonTerminal));
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}
