package parser;

import grammar.Grammar;

public interface Parser {

    boolean parse(Grammar g, String s);

    int getCounter();
}
