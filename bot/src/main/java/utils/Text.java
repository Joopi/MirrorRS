package utils;

public class Text {
    public static String stripHTML(String s) {
        return s.replaceAll("\\<[^>]*>","");
    }
}
