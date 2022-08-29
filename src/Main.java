import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        System.out.println("Start");

        String[] rules = new String[]{"S aS", "S a", "X x"};
        Grammar g = createGrammarFromStringArr(rules);

        System.out.println(g.getNonTerminals());
        System.out.println(g.getRules());
        Parser.parseNaive(g, "ab");
    }

    private static Grammar createGrammarFromStringArr(String[] rules) {
        Grammar g = new Grammar() {
            @Override
            public void readGrammar(String[] input) {
                HashMap<Character, Integer> nonTerminals = getNonTerminals();
                ArrayList<ArrayList<String>> rules = getRules();
                int counter = 0;
                for (String rule : input) {
                    char nonTerminal = rule.charAt(0);
                    if (!nonTerminals.containsKey(nonTerminal)) {
                        nonTerminals.put(nonTerminal, counter++);
                        rules.add(new ArrayList<>());
                    }
                    int index = nonTerminals.get(nonTerminal);
                    String result = rule.substring(2);
                    rules.get(index).add(result);
                }
            }
        };
        g.readGrammar(rules);
        return g;
    }
}
