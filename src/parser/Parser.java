package parser;

import grammar.CNFGrammar;

public interface Parser {

    boolean parse(CNFGrammar g, String s);

    long getCounter();
}
