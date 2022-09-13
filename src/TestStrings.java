public class TestStrings {
    private final String[] dyckInsideStrings;
    private final String[] dyckRepeatStrings;
    private final String[] dyckRepeatStringsFailBefore;
    private final String[] dyckRepeatStringsFailAfter;
    private final String[] stupidStrings;

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
}
