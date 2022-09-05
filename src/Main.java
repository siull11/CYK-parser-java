public class Main {
    public static void main(String[] args) {
        System.out.println("Start of program");

        Grammar g = new GrammarFromFile("dyck.txt");
//        Grammar g = new GrammarDyck();
//        Grammar g = new GrammarStupid();

//        Parser p = new ParserNaive();
//        Parser p = new ParserTD();
        Parser p = new ParserBU();

        printRes(p.parse(g, "((())(()))"), p.getCounter());
//        printRes(p.parse(g, "aaaaa"), p.getCounter());
    }

    private static void printRes(boolean accept, int counter) {
        System.out.println("Accept: " + accept + ", counter: " + counter);
    }
}
