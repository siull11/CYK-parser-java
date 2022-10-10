package run;

import grammar.CNFGrammar;
import parser.*;

public class Parse {
/*
    Command line arguments (args): [g, m, s]
        g is the grammar file name
        m is the parser method (t for top-down, tl for linear top-down, b for bottom-up, bb for bottom-up with bool array, n for naive)
        s is the string to parse
 */
    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Usage: java -XX:CompileThreshold=1 -jar fileName.jar <grammar file> <parser method> <string>");
            System.exit(1);
        }
        CNFGrammar g = new CNFGrammar(args[0], false); //FEL!!!
        Parser p = createCNFParser(g, args[1]);
        System.out.println("String: " + args[2] + ", accept: " + p.parse(args[2]) + ", count: " + p.getCounter());
    }

    public static Parser createCNFParser(CNFGrammar g, String method) {
        switch (method) {
            case "t":
                return new CNFParserTD(g);
            case "b":
                return new ParserBU(g);
            case "bb":
                return new ParserBUBool(g);
            case "n":
                return new ParserNaive(g);
            default:
                System.out.println("Unknown CNF parser method: " + method);
                System.exit(1);
        }
        return null;
    }
}
