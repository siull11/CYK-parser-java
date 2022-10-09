package run;

import grammar.CNFGrammar;
import parser.Parser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class RunTest {
/*
    Command line arguments (args): [g, m, s, r]
        g is the grammar file name
        r is the amount of runs to do and average over (3 is min!!! ändra???)
        m is the parser method (t for top-down, tl for linear top-down, b for bottom-up, bb for bottom-up with bool array, n for naive)
        s is the file name for the strings to parse
 */
    public static void main(String[] args) {
        if (args.length != 4) {
            System.out.println("Usage: java -XX:CompileThreshold=1 -jar fileName.jar <grammar file> <runs> <parser method> <string file>");
            System.exit(1);
        }
        System.out.println("Test params: [g: " + args[0] + ", r: " + args[1] + ", m: " + args[2] + ", s: " + args[3] + "]");

        CNFGrammar g = new CNFGrammar(args[0], false); // FIXA SÅ HANTERAR BÅDE LINEAR O CNF!!!
        Parser p = Parse.createParser(args[2]);

        try {
            File file = new File("resources/strings/" + args[3]);
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                try {
                    runTest(g, p, null, sc.nextLine(), Integer.parseInt(args[1]));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (Exception e) {
            System.out.println("Failed to read from file " + args[2] + ". Error: " + e);
            System.exit(e.hashCode());
        }
    }

    public static void runTest(CNFGrammar g, Parser p, FileWriter f, String s, int runs) throws IOException {
        p.parse(g, s); // Dry run
        boolean res = false;
        long count = 0;
        long[] time = new long[runs];
        long maxTime = 0;
        long minTime = Long.MAX_VALUE;

        for (int i = 0; i < runs; i++) { // Do test runs
            // Do run, measure time
            long t1 = System.nanoTime();
            res = p.parse(g, s);
            long t2 = System.nanoTime();

            // Save time and counter
            count = p.getCounter();
            time[i] = t2 - t1;
            if (time[i] > maxTime) maxTime = time[i];
            if (time[i] < minTime) minTime = time[i];
        }

        // Calculate sum (without max and min)
        long sumTime = -maxTime - minTime;
        for (int i = 0; i < runs; i++) {
            sumTime += time[i];
        }

        // Print to file or stdout
        String out = s.length() + "," +
                String.valueOf(res).charAt(0) + "," + count + "," +
                ((double) sumTime/(runs-2))/1000000000;
        if (f != null) {
            f.write(out + "\n");
        }
        System.out.println(out);
    }
}
