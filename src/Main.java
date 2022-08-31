public class Main {
    public static void main(String[] args) {
        System.out.println("Start");

        String[] rules = new String[]{"S SS", "S s", "X x", "S XX"};


        Grammar g = new Grammar(rules);

//        System.out.println("id: " + g.ids);
//        System.out.println("reverse id: " + g.reverseIds);
//        System.out.println("rules: " + g.rules);
//        System.out.println("produces t: " + g.prodT);
//        System.out.println("produces nt: " + g.prodNT);
        System.out.println("rules2: " + g.rules2);
        System.out.println("produces t2: " + g.prodT2);

//        Parser p = new ParserNaive();
        Parser p = new ParserBU();

        System.out.println("Ans: " + p.parse(g, "ssxx"));
    }
}

//                InputStream stream;
//                try {
//                    stream = getClass().getClassLoader().getResourceAsStream(fileName);
//                } catch (Exception e) {
//                    System.out.println(e.toString());
//                    System.exit(1);
//                }
//
