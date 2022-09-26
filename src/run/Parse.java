package run;

import grammar.Grammar;
import grammar.GrammarFromFile;
import parser.Parser;

public class Parse {
/*
    Command line arguments (args): [g, m, s]
        g is the grammar file name
        m is the parser method (t for top-down, b for bottom-up, n for naive)
        s is the string to parse
 */
    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Usage: java -XX:CompileThreshold=1 -jar fileName.jar <grammar file> <parser method> <string>");
            System.exit(1);
        }
        Grammar g = new GrammarFromFile(args[0]);
        Parser p = createParser(args[1]);
        System.out.println("String: " + args[2] + ", accept: " + p.parse(g, args[2]) + ", count: " + p.getCounter());
    }

    public static Parser createParser(String method) {
        switch (method) {
            case "t":
                return new parser.ParserTD();
            case "b":
                return new parser.ParserBU();
            case "n":
                return new parser.ParserNaive();
            default:
                System.out.println("Unknown parser method: " + method);
                System.exit(1);
        }
        return null;
    }
}
