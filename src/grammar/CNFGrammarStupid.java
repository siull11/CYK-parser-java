package grammar;

public class CNFGrammarStupid extends CNFGrammar {
    public CNFGrammarStupid() {
        super(new String[]{"S ST", "S TS", "T a"});
    }
}
