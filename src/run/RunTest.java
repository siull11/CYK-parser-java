package run;

import grammar.*;
import parser.LinearParserTD;
import parser.Parser;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

import static run.Parse.createCNFParser;

public class RunTest {
/*
    Command line arguments (args): [g, l, r, m, s]
        g is the grammar file name
        l is the weather the grammar file is in linear format or not (l for linear, c for not linear (CNF))
        r is the amount of runs to do and average over (min value 3)
        m is the parser method (t for top-down, tl for linear top-down, b for bottom-up, bb for bottom-up with bool array, n for naive)
        s is the file name for the strings to parse
 */
    public static void main(String[] args) {
        if (args.length != 5) {
            System.out.println("Usage: java -XX:CompileThreshold=1 -jar fileName.jar <grammar file> <linear> <runs> <parser method> <string file>");
            System.exit(1);
        }
        System.out.println("Test params: [g: " + args[0] + ", l: " + args[1] + ", r: " + args[2] + ", m: " + args[3] + ", s: " + args[4] + "]");

        boolean linearGrammar = Objects.equals(args[1], "l");
        Parser p;
        if (linearGrammar && Objects.equals(args[3], "tl")) // Linear parse
            p = new LinearParserTD(new LinearGrammar(args[0]));
        else // Grammar file non-linear
            p = createCNFParser(new CNFGrammar(args[0], linearGrammar), args[3]);

        runTests(p, args[4], Integer.parseInt(args[2]));
    }

    private static void runTests(Parser p, String fileName, int runs) {
        try {
            File file = new File("resources/strings/" + fileName);
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) { // For each string in file
                try {
                    runTest(p, sc.nextLine(), runs);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (Exception e) {
            System.out.println("Failed to read from file " + "resources/strings/" + fileName + ". Error: " + e);
            System.exit(e.hashCode());
        }
    }

    private static void runTest(Parser p, String s, int runs) throws IOException {
        p.parse(s); // Dry run
        boolean res = false;
        long count = 0;
        long[] time = new long[runs];
        long maxTime = 0;
        long minTime = Long.MAX_VALUE;

        for (int i = 0; i < runs; i++) { // Do test runs
            // Do run, measure time
            long t1 = System.nanoTime();
            res = p.parse(s);
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

        // Print res
        System.out.println(
            s.length() + "," +
            String.valueOf(res).charAt(0) + "," +
            count + "," +
            ((double) sumTime/(runs-2))/1000000000
        );
    }
}
