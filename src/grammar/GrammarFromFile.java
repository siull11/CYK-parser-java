package grammar;

public class GrammarFromFile extends Grammar{
    public GrammarFromFile(String fileName) {
        super("resources/grammars/" + fileName);
    }

    public GrammarFromFile(String fileName, boolean linear) {
        super("resources/grammars/" + fileName, linear);
    }
}
