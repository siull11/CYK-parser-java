public interface Parser {
    boolean parse(Grammar g, String s);

    int getCounter();
}
