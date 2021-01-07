package utils;

public class Text {
    public static String stripHTML(String s) {
        int idx = s.indexOf(">");
        if (idx == s.length() - 1)
            return "";

        return s.substring(idx + 1);
    }
}
