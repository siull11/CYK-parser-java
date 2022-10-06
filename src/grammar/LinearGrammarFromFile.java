package grammar;

public class LinearGrammarFromFile extends LinearGrammar {
    public LinearGrammarFromFile(String fileName) {
        super("resources/grammars/" + fileName);
    }
}
