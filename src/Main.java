import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        System.out.println("Start");

        String[] rules = new String[]{"S aS", "S a", "X x"};
        Grammar g = createGrammarFromStringArr(rules);

        System.out.println(g.getNonTerminals());
        System.out.println(g.getRules());
        System.out.println(g.getRule('X'));
        Parser.parseNaive(g, "ab");
    }

    private static Grammar createGrammarFromStringArr(String[] rules) {
        Grammar g = new Grammar() {
            @Override
            public void read(String[] input) {
                HashMap<Character, Integer> nonTerminals = getNonTerminals();
                ArrayList<ArrayList<String>> rules = getRules();
                int counter = 0; // Replace counter with nonTerminals.length()-1 ???
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

            @Override
            public void read(String fileName) {
                System.out.println("ERROR: Wrong read method called");
            }
        };
        g.read(rules);
        return g;
    }

    private static Grammar createGrammarFromFile(String fileName) { // NOT implemented !!!
        Grammar g = new Grammar() {
            @Override
            public void read(String[] rules) {
                System.out.println("ERROR: Wrong read method called");
            }

            @Override
            public void read(String fileName) {
                try {
                    InputStream stream = getClass().getClassLoader().getResourceAsStream(fileName);
                } catch (Exception e) {
                    System.out.println(e.toString());
                    System.exit(1);
                }
                HashMap<Character, Integer> nonTerminals = getNonTerminals();
                ArrayList<ArrayList<String>> rules = getRules();

            }
        };
        g.read(fileName);
        return g;
    }
}
