public class GrammarParenthesis extends Grammar{
    public GrammarParenthesis() {
        super(new String[]{"S SS", "S LA", "S LR", "A SR", "L (", "R )"});
    }
}
