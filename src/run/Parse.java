package run;

import grammar.*;
import parser.*;

import java.util.Objects;

public class Parse {
/*
    Command line arguments (args): [g, l, m, s]
        g is the grammar file name
        l is the weather the grammar file is in linear format or not (l for linear, c for not linear (CNF))
        m is the parser method (t for top-down, tl for linear top-down, b for bottom-up, bb for bottom-up with bool array, n for naive)
        s is the string to parse
 */
    public static void main(String[] args) {
        if (args.length != 4) {
            System.out.println("Usage: java -XX:CompileThreshold=1 -jar fileName.jar <grammar file> <linear> <parser method> <string>");
            System.exit(1);
        }

        Parser p = createParser(args[0], Objects.equals(args[1], "l"), args[2]);

        System.out.println("String: " + args[3] + ", accept: " + p.parse(args[3]) + ", count: " + p.getCounter());
    }

    public static Parser createParser(String grammarFile, boolean linearGrammar, String method) {
        if (linearGrammar && Objects.equals(method, "tl")) // Linear parse
            return new LinearParserTD(new LinearGrammar(grammarFile));
        else // Grammar file non-linear
            return createCNFParser(new CNFGrammar(grammarFile, linearGrammar), method);
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
