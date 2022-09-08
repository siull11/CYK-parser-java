import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        System.out.println("Start of program");

//        Grammar g = new GrammarFromFile("test.txt");
        Grammar g = new GrammarDyck();
//        Grammar g = new GrammarStupid();


        System.out.println("ids: "+g.ids);

        System.out.println("nt to nts: "+Arrays.deepToString(g.NT_to_NTs));
        System.out.println("nt to ts: "+Arrays.deepToString(g.NT_to_Ts));

        System.out.println("nts to nt: "+Arrays.deepToString(g.NTs_to_NT));
        System.out.println("t to nts: "+g.T_to_NTs.toString());



//        Parser p = new ParserNaive();
//        Parser p = new ParserTD();
        Parser p = new ParserBU();
//(()()
        printRes(p.parse(g, "(()()"), p.getCounter());
    }

    private static void printRes(boolean accept, int counter) {
        System.out.println("Accept: " + accept + ", counter: " + counter);
    }
}
