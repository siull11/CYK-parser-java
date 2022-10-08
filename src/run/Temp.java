package run;

import grammar.*;
import parser.*;

public class Temp {
    public static void main(String[] args) {
// Language from spec: (ab?)^nbc^n
        String input = "ababbcc"; // "aabaaaabcccccc";

        LinearGrammar lg = new LinearGrammarFromFile("linearGrammarEx.txt");
        LinearParserTD lp = new LinearParserTD();
        System.out.println(lp.parse(lg, input));

        CNFGrammar cg = new CNFGrammarFromFile("linearGrammarEx.txt", true);
        Parser cp = new ParserTD();
        System.out.println(cp.parse(cg, input));
    }
}
