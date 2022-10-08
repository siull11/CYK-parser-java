package grammar;

public class CNFGrammarFromFile extends CNFGrammar {
    public CNFGrammarFromFile(String fileName) {
        super("resources/grammars/" + fileName);
    }

    public CNFGrammarFromFile(String fileName, boolean linear) {
        super("resources/grammars/" + fileName, linear);
    }
}
