import java.util.ArrayList;
import java.util.HashMap;

public abstract class Grammar {
    private HashMap<Character, Integer> nonTerminals;
    private ArrayList<ArrayList<String>> rules;

    public Grammar() {
        nonTerminals = new HashMap<>();
        rules = new ArrayList<>();
    }

    public abstract void readGrammar(String[] rules);

    public HashMap<Character, Integer> getNonTerminals() {
        return nonTerminals;
    }

    public ArrayList<ArrayList<String>> getRules() {
        return rules;
    }
}
