package run;

import grammar.*;
import parser.*;

public class Temp {
    public static void main(String[] args) {
// Language from spec: (ab?)^nbc^n
        /*
        String input = "ababbcc"; // "aabaaaabcccccc";

        LinearGrammar lg = new LinearGrammar("linearGrammarEx.txt");
        LinearParserTD lp = new LinearParserTD(lg);
        System.out.println(lp.parse(input) + ", " + lp.getCounter());
        System.out.println(lg);


        CNFGrammar cg = new CNFGrammar("linearGrammarEx.txt", true);
        Parser cp = new CNFParserTD(cg);
        System.out.println(cp.parse(input) + ", " + cp.getCounter());
        System.out.println(cg);
        */

        String input = "(((((((()";
        CNFGrammar g = new CNFGrammar("dyck.txt", false);
        ParserBUError p = new ParserBUError(g);
        boolean accept = p.parse(input);
        if (!accept) {
            ParserBUError.ErrReturnData ret = p.getErrorCorrections();
            if (ret != null) {
                System.out.println("error: " + ret.corrections);
                System.out.println("string: " + ret.corrected);
            } else {
                System.out.println("can't fix");
            }
        }
        System.out.println(accept + ", " + p.getCounter());
        System.out.println(g);
    }
}
