package run;

import grammar.LinearGrammar;
import grammar.LinearGrammarFromFile;
import parser.LinearParserTD;

public class Temp {
    public static void main(String[] args) {
        LinearGrammar g = new LinearGrammarFromFile("linearGrammarEx.txt");
        LinearParserTD p = new LinearParserTD();

// (ab?)^nbc^n

        System.out.println(p.parse(g, "aabaaaabcccccc"));
    }
}
