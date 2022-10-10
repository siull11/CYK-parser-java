package run;

import java.io.FileWriter;
import java.io.IOException;

public class TestStrings {
    private final String[] dyckInsideStrings;
    private final String[] dyckRepeatStrings;
    private final String[] dyckRepeatStringsFailBefore;
    private final String[] dyckRepeatStringsFailAfter;
    private final String[] stupidStrings;
    private final String[] abcStrings;
    private final String[] abbcStrings;

/*
    Command line arguments (args): [n, d]
        n is the max length of test strings
        d is the distance in length between two sequential test strings
            n needs to be divisible by d and n/d needs to be divisible by 2
            total amount of test strings will be n/d
 */
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java -jar fileName.jar <max length> <distance>");
            System.exit(1);
        }
        System.out.println("String params: [n: " + args[0] + ", d: " + args[1] + "]");
        TestStrings ts = new TestStrings(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        printToFile("dyck-inside.txt", ts.getDyckInsideStrings());
        printToFile("dyck-repeat.txt", ts.getDyckRepeatStrings());
        printToFile("dyck-repeat-before.txt", ts.getDyckRepeatStringsFailBefore());
        printToFile("dyck-repeat-after.txt", ts.getDyckRepeatStringsFailAfter());
        printToFile("stupid.txt", ts.getStupidStrings());
        printToFile("abc.txt", ts.getABCStrings());
        printToFile("abbc.txt", ts.getABBCStrings());
    }

    public static void printToFile(String fileName, String[] strings) {
        try {
            FileWriter file = new FileWriter("resources/strings/" + fileName);
            for (String s: strings) {
                file.write(s + "\n");
            }
            file.close();
        } catch (IOException e) {
            System.out.println("Failed to create file: " + fileName + ". Error: " + e);
            System.exit(1);
        }
    }

    public TestStrings(int maxLen, int dLen) {
        // Create strings "(((..." and ")))...", with len dLen/2
        StringBuilder sb1 = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        for (int i = 0; i < (int) ((float) dLen/2 + 0.5); i++) {
            sb1.append("(");
            sb2.append(")");
        }
        String before = sb1.toString();
        String after = sb2.toString();
        // Create strings "(((...)))", starting with len dLen, incrementing with dLen until maxLen
        StringBuilder sb = new StringBuilder();
        dyckInsideStrings = new String[maxLen/dLen];
        for (int i = 0; i < maxLen/dLen; i++) {
            sb.insert(0, before);
            sb.append(after);
            dyckInsideStrings[i] = sb.toString();
        }

        // Create string "()()()...", with len dLen
        sb1 = new StringBuilder();
        sb1.append("()".repeat(Math.max(0, dLen/2)));
        String repeat = sb1.toString();
        // Create strings "()()()...", starting with len dLen, incrementing with dLen until maxLen
        // Plus failing strings with an additional ")" at the beginning and strings with an additional "(" at the end
        sb = new StringBuilder();
        dyckRepeatStrings = new String[maxLen/dLen];
        dyckRepeatStringsFailBefore = new String[maxLen/dLen];
        dyckRepeatStringsFailAfter = new String[maxLen/dLen];
        for (int i = 0; i < maxLen/dLen; i++) {
            sb.append(repeat);
            dyckRepeatStrings[i] = sb.toString();
            dyckRepeatStringsFailBefore[i] = ")" + sb;
            dyckRepeatStringsFailAfter[i] = sb + "(";
        }

        // Create string "aaa..." with len dLen
        sb1 = new StringBuilder();
        sb1.append("a".repeat(Math.max(0, dLen)));
        repeat = sb1.toString();
        // Create strings "aaa...", starting with len dLen, incrementing with dLen until maxLen
        sb = new StringBuilder();
        stupidStrings = new String[maxLen/dLen];
        for (int i = 0; i < maxLen/dLen; i++) {
            sb.append(repeat);
            stupidStrings[i] = sb.toString();
        }

        // Create string "aaa..." and "ccc...", with len dLen/2
        sb1 = new StringBuilder();
        sb2 = new StringBuilder();
        for (int i = 0; i < (int) ((float) dLen/2 + 0.5); i++) {
            sb1.append("a");
            sb2.append("c");
        }
        before = sb1.toString();
        after = sb2.toString();
        // Create strings "a^nbc^n", starting with len dLen+1, incrementing with dLen until maxLen+1
        sb = new StringBuilder();
        sb.append("b");
        abcStrings = new String[maxLen/dLen];
        for (int i = 0; i < maxLen/dLen; i++) {
            sb.insert(0, before);
            sb.append(after);
            abcStrings[i] = sb.toString();
        }

        // Create string "abab..." and "ccc...", with len 2dLen/3 and dLen/3
        sb1 = new StringBuilder();
        sb2 = new StringBuilder();
        for (int i = 0; i < (int) ((float) dLen/3 + 0.5); i++) {
            sb1.append("ab");
            sb2.append("c");
        }
        before = sb1.toString();
        after = sb2.toString();
        // Create strings "(ab)^nbc^n", amount of strings will be same as other cases but lengths are a bit fucky
        sb = new StringBuilder();
        sb.append("b");
        abbcStrings = new String[maxLen/dLen];
        for (int i = 0; i < maxLen/dLen; i++) {
            sb.insert(0, before);
            sb.append(after);
            abbcStrings[i] = sb.toString();
        }
    }

    public String[] getDyckInsideStrings() {
        return dyckInsideStrings;
    }

    public String[] getDyckRepeatStrings() {
        return dyckRepeatStrings;
    }

    public String[] getDyckRepeatStringsFailBefore() {
        return dyckRepeatStringsFailBefore;
    }

    public String[] getDyckRepeatStringsFailAfter() {
        return dyckRepeatStringsFailAfter;
    }

    public String[] getStupidStrings() {
        return stupidStrings;
    }

    public String[] getABCStrings() {
        return abcStrings;
    }

    public String[] getABBCStrings() {
        return abbcStrings;
    }
}
