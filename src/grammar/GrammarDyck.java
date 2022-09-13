package grammar;

public class GrammarDyck extends Grammar {
    public GrammarDyck() {
        super(new String[]{"S SS", "S LA", "S LR", "A SR", "L (", "R )"});
    }
}
