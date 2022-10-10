package run;

import grammar.*;
import parser.*;

public class Temp {
    public static void main(String[] args) {
// Language from spec: (ab?)^nbc^n
        String input = "ababbcc"; // "aabaaaabcccccc";

        LinearGrammar lg = new LinearGrammar("linearGrammarEx.txt");
        LinearParserTD lp = new LinearParserTD(lg);
        System.out.println(lp.parse(input) + ", " + lp.getCounter());
        System.out.println(lg);


        CNFGrammar cg = new CNFGrammar("linearGrammarEx.txt", true);
        Parser cp = new CNFParserTD(cg);
        System.out.println(cp.parse(input) + ", " + cp.getCounter());
        System.out.println(cg);
    }
}
