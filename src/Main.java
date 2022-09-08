
public class Main {
    public static void main(String[] args) {
        System.out.println("Start of program");

//        Grammar g = new GrammarFromFile("test.txt");
        Grammar g = new GrammarDyck();
//        Grammar g = new GrammarStupid();

        System.out.println(g.ids);

//        Parser p = new ParserNaive();
//        Parser p = new ParserTD();
        ParserBU p = new ParserBU();


// (()()()(()))(()()()(()))(()()()(()))(()()()(()))(()()()(()))(()()()(()))
        printRes(p.parse(g, "(()()()(()))(()()()(()))(()()()(()))(()()()(()))(()()()(()))(()()()(()))"), p.getCounter());
    }

    private static void runTests() {

    }

    private static void printRes(boolean accept, int counter) {
        System.out.println("Accept: " + accept + ", counter: " + counter);
    }
}
