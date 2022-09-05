public class Main {
    public static void main(String[] args) {
        System.out.println("Start of program");
        Grammar g = new GrammarParenthesis();
        Parser p = new ParserBU();
        System.out.println("Ans: " + p.parse(g, "((())(()))") + ", Counter: " + p.getCounter());
    }
}

//InputStream stream;
//try {
//    stream = getClass().getClassLoader().getResourceAsStream(fileName);
//} catch (Exception e) {
//    System.out.println(e.toString());
//    System.exit(1);
//}

