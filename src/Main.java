import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        System.out.println("Start of program");
        Grammar g = new GrammarFromFile("dyck.txt");
//        Grammar g = new GrammarDyck();
//        Grammar g = new GrammarStupid();

        try {
            runTests(g, 10);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }



//        System.out.println(g.ids);

//        Parser p = new ParserNaive();
//        Parser p = new ParserTD();
//        ParserBU p = new ParserBU();

// (()()()(()))(()()()(()))(()()()(()))(()()()(()))(()()()(()))(()()()(()))
//        printRes(p.parse(g, "(()()()(()))(()()()(()))(()()()(()))(()()()(()))(()()()(()))(()()()(()))"), p.getCounter());
    }

    private static void runTests(Grammar g, int n) throws IOException { // nt array n???
        String[] dyckStringsSmall = new String[]{
                "()",
                "()()",
                "(())",
                "(()())",
                "()()()()()()()()()()", // 10 pairs, len 20
                "(((((((((())))))))))",
//                "()()()()()()()()()()()()()()()()()()()()",  // 20 pairs, len 40
//                "(((((((((((((((((((())))))))))))))))))))",
//                "()()()()()()()()()()()()()()()()()()()()()()()()()()()()()()",  // 30 pairs, len 60
//                "(((((((((((((((((((((((((((((())))))))))))))))))))))))))))))",
        };

        System.out.println("Naive Dyck small");
        Parser naive = new ParserNaive();
        FileWriter file = createFileWriter("naive-dyck.csv");
        for (String s: dyckStringsSmall) runtTest(g, naive, file, s, n);
        file.close();

        System.out.println("TD Dyck small");
        Parser td = new ParserTD();
        file = createFileWriter("td-dyck.csv");
        for (String s: dyckStringsSmall) runtTest(g, td, file, s, n);
        file.close();

        System.out.println("BU Dyck small");
        Parser bu = new ParserBU();
        file = createFileWriter("bu-dyck.csv");
        for (String s: dyckStringsSmall) runtTest(g, bu, file, s, n);
        file.close();
    }

    private static void runtTest(Grammar g, Parser p, FileWriter f, String s, int n) throws IOException {
        boolean dryrun = p.parse(g, s);
        long time = 0;
        int count = 0;
        for (int i = 0; i < n; i++) {
            long t1 = System.nanoTime();
            boolean res = p.parse(g, s);
            long t2 = System.nanoTime();
            time += t2 - t1;
            count += p.getCounter();
        }
        f.write(count/n + "," + time/n + "\n");
    }

    private static void printRes(boolean accept, int counter) {
        System.out.println("Accept: " + accept + ", counter: " + counter);
    }

    private static FileWriter createFileWriter(String fileName) {
        try {
            return new FileWriter("results/" + fileName);
        } catch (IOException e) {
            System.out.println("Failed to create file: " + fileName + ".\n\tError: " + e);
            System.exit(1);
        }
        return null;
    }
}
