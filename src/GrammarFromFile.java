public class GrammarFromFile extends Grammar{
    public GrammarFromFile(String fileName) {
        super("resources/grammars/" + fileName);
    }
}
