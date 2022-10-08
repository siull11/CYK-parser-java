package grammar;

public class CNFGrammarDyck extends CNFGrammar {
    public CNFGrammarDyck() {
        super(new String[]{"S SS", "S LA", "S LR", "A SR", "L (", "R )"});
    }
}
