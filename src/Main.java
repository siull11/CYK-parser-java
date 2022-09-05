public class Main {
    public static void main(String[] args) {
        System.out.println("Start of program");
        Grammar g = new GrammarDyck();
//        Grammar g = new GrammarStupid();
        Parser p = new ParserBU();
        System.out.println("Ans: " + p.parse(g, "((())(()))") + ", c: " + p.getCounter());
//        System.out.println("Ans: " + p.parse(g, "aaaaa") + ", c: " + p.getCounter());
    }
}
