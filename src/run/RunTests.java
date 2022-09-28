package run;

import grammar.*;
import parser.*;

import java.io.FileWriter;
import java.io.IOException;

public class RunTests {
/*
    Command line arguments (args): [g, m, s, r] FIXA DENNA!!!
        g is the grammar file name
        m is the parser method (t for top-down, b for bottom-up, n for naive)
        s is the file name for the strings to parse
        r is the amount of runs to do and average over
 */
    // runs min is 3 ÄNDRA???!!!
    // args[1] needs to be divisible by args[2] and args[1]/args[2] needs to be divisible by 2 (ACTUALL)
    public static void main(String[] args) {  //Städa upp o ändra/radera denna!!!

        System.out.println("Test settings: \n" +
                "runs: " + args[0] + ", " + "max len: " + args[1] + ", " + "dist len: " + args[2]);

        try {
            runTests(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
        for (String s: ts.getDyckInsideStrings()) RunTest.runTest(gd, td, file, s, runs);
        file.close();

        System.out.println("TD Dyck repeat");
        file = createFileWriter("td-dyck-repeat.csv");
        for (String s: ts.getDyckRepeatStrings()) RunTest.runTest(gd, td, file, s, runs);
        file.close();

        System.out.println("TD Dyck repeat fail before");
        file = createFileWriter("td-dyck-repeat-before.csv");
        for (String s: ts.getDyckRepeatStringsFailBefore()) RunTest.runTest(gd, td, file, s, runs);
        file.close();

        System.out.println("TD Dyck repeat fail after");
        file = createFileWriter("td-dyck-repeat-after.csv");
        for (String s: ts.getDyckRepeatStringsFailAfter()) RunTest.runTest(gd, td, file, s, runs);
        file.close();

        System.out.println("TD stupid");
        file = createFileWriter("td-stupid.csv");
        for (String s: ts.getStupidStrings()) RunTest.runTest(gs, td, file, s, runs);
        file.close();

        // BU
        System.out.println("BU Dyck inside");
        file = createFileWriter("bu-dyck-inside.csv");
        for (String s: ts.getDyckInsideStrings()) RunTest.runTest(gd, bu, file, s, runs);
        file.close();

        System.out.println("BU Dyck repeat");
        file = createFileWriter("bu-dyck-repeat.csv");
        for (String s: ts.getDyckRepeatStrings()) RunTest.runTest(gd, bu, file, s, runs);
        file.close();

        System.out.println("BU Dyck repeat fail before");
        file = createFileWriter("bu-dyck-repeat-before.csv");
        for (String s: ts.getDyckRepeatStringsFailBefore()) RunTest.runTest(gd, bu, file, s, runs);
        file.close();

        System.out.println("BU Dyck repeat fail after");
        file = createFileWriter("bu-dyck-repeat-after.csv");
        for (String s: ts.getDyckRepeatStringsFailAfter()) RunTest.runTest(gd, bu, file, s, runs);
        file.close();

        System.out.println("BU stupid");
        file = createFileWriter("bu-stupid.csv");
        for (String s: ts.getStupidStrings()) RunTest.runTest(gs, bu, file, s, runs);
        file.close();
    }

    private static void runSmallTests(int runs, int maxLen, int dLen) throws IOException  {

    }

    private static FileWriter createFileWriter(String fileName) {
        try {
            FileWriter file = new FileWriter("results/" + fileName);
            file.write("len,res,count,time\n");
            return file;
        } catch (IOException e) {
            System.out.println("Failed to create file: " + fileName + ". Error: " + e);
            System.exit(1);
        }
        return null;
    }
}
