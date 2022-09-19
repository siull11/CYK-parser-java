import grammar.*;
import parser.*;

import java.io.FileWriter;
import java.io.IOException;

public class Main { //FIXA PACKAGE för main methods!!!

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
//        try {
////            runTest(g, p, null, "()", 1);
//            runTest(g, p, null, "(()()()(()))(()()()(()))(()()()(()))(()()()(()))(()()()(()))(()()()(()))", 1);
////            runTest(g, p, null, "(((((((((((((((((((((((((((((((((((((((((((((((((())))))))))))))))))))))))))))))))))))))))))))))))))", 1);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }

    private static void runTests(int runs, int maxLen, int dLen) throws IOException { // Adda mer progress outprints???
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

    private static void printRes(boolean accept, int counter) {
        System.out.println("Accept: " + accept + ", counter: " + counter);
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
