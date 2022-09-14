import java.io.FileWriter;
import java.io.IOException;
import grammar.*;
import parser.*;

public class Main {

    // args[1] needs to be divisible by args[2] and args[1]/args[2] needs to be divisible by 2
    public static void main(String[] args) {
        System.out.println("Start of program");
        System.out.println("Test settings: \n" +
                "runs: " + args[0] + ", " + "max len: " + args[1] + ", " + "dist len: " + args[2]);

        try {
            runTests(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

//        Grammar g =
//                new GrammarDyck();
//                new GrammarStupid();
//                new GrammarFromFile("dyck.txt");

//        Parser p = new ParserNaive();
//        Parser p = new ParserTD();
//        ParserBU p = new ParserBU();

//        printRes(p.parse(g, "(((((((((((((((((((((((((((((((((((((((((((((((((())))))))))))))))))))))))))))))))))))))))))))))))))"), p.getCounter());
//        printRes(p.parse(g, "(()()()(()))(()()()(()))(()()()(()))(()()()(()))(()()()(()))(()()()(()))"), p.getCounter());
    }

    private static void runTests(int runs, int maxLen, int dLen) throws IOException {
        Grammar gd = new GrammarDyck();
        Grammar gs = new GrammarStupid();
        TestStrings ts = new TestStrings(maxLen, dLen);
        Parser td = new ParserTD();
        Parser bu = new ParserBU();

        // TD
        System.out.println("TD Dyck inside");
        FileWriter file = createFileWriter("td-dyck-inside.csv");
        for (String s: ts.getDyckInsideStrings()) runtTest(gd, td, file, s, runs);
        file.close();

        System.out.println("TD Dyck repeat");
        file = createFileWriter("td-dyck-repeat.csv");
        for (String s: ts.getDyckRepeatStrings()) runtTest(gd, td, file, s, runs);
        file.close();

        System.out.println("TD Dyck repeat fail before");
        file = createFileWriter("td-dyck-repeat-before.csv");
        for (String s: ts.getDyckRepeatStringsFailBefore()) runtTest(gd, td, file, s, runs);
        file.close();

        System.out.println("TD Dyck repeat fail after");
        file = createFileWriter("td-dyck-repeat-after.csv");
        for (String s: ts.getDyckRepeatStringsFailAfter()) runtTest(gd, td, file, s, runs);
        file.close();

        System.out.println("TD stupid");
        file = createFileWriter("td-stupid.csv");
        for (String s: ts.getStupidStrings()) runtTest(gs, td, file, s, runs);
        file.close();

        // BU
        System.out.println("BU Dyck inside");
        file = createFileWriter("bu-dyck-inside.csv");
        for (String s: ts.getDyckInsideStrings()) runtTest(gd, bu, file, s, runs);
        file.close();

        System.out.println("BU Dyck repeat");
        file = createFileWriter("bu-dyck-repeat.csv");
        for (String s: ts.getDyckRepeatStrings()) runtTest(gd, bu, file, s, runs);
        file.close();

        System.out.println("BU Dyck repeat fail before");
        file = createFileWriter("bu-dyck-repeat-before.csv");
        for (String s: ts.getDyckRepeatStringsFailBefore()) runtTest(gd, bu, file, s, runs);
        file.close();

        System.out.println("BU Dyck repeat fail after");
        file = createFileWriter("bu-dyck-repeat-after.csv");
        for (String s: ts.getDyckRepeatStringsFailAfter()) runtTest(gd, bu, file, s, runs);
        file.close();

        System.out.println("BU stupid");
        file = createFileWriter("bu-stupid.csv");
        for (String s: ts.getStupidStrings()) runtTest(gs, bu, file, s, runs);
        file.close();
    }

    private static void runtTest(Grammar g, Parser p, FileWriter f, String s, int runs) throws IOException {
        p.parse(g, s); // Dry run
        boolean res = false;
        int[] count = new int[runs];
        long[] time = new long[runs];
        long maxTime = 0;
        long minTime = Long.MAX_VALUE;

        for (int i = 0; i < runs; i++) { // Do runs test runs
            // Do run, measure time
            long t1 = System.nanoTime();
            res = p.parse(g, s);
            long t2 = System.nanoTime();

            // Save time and counter
            count[i] = p.getCounter();
            time[i] = t2 - t1;
            if (time[i] > maxTime) maxTime = time[i];
            if (time[i] < minTime) minTime = time[i];
        }

        for (int i = 0; i < runs; i++) { // Remove max and min time (once)
            if (time[i] == maxTime) {
                maxTime = -1;
                count[i] = 0;
                time[i] = 0;
            }
            if (time[i] == minTime) {
                minTime = -1;
                count[i] = 0;
                time[i] = 0;
            }
        }

        // Calculate sums
        int sumCount = 0;
        long sumTime = 0;
        for (int i = 0; i < runs; i++) {
            sumCount += count[i];
            sumTime += time[i];
        }

        // Write avg of runs to file
        f.write(s.length() + "," +
                String.valueOf(res).charAt(0) + "," +
                sumCount/(runs-2) + "," +
                ((double) sumTime/(runs-2))/1000000000 + "\n");
    }

    private static void printRes(boolean accept, int counter) {
        System.out.println("Accept: " + accept + ", counter: " + counter);
    }

    private static FileWriter createFileWriter(String fileName) {
        try {
            FileWriter file = new FileWriter("results/" + fileName);
            file.write("len,res,count,time\n");
            return file;
        } catch (IOException e) {
            System.out.println("Failed to create file: " + fileName + ".\n\tError: " + e);
            System.exit(1);
        }
        return null;
    }
}
