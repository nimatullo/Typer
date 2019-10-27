package vowelcounter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Demo {
    public static void main(String[] args) {
        String s = "hello, my friends 10, ok, yeah, free!";
        Pattern pattern = Pattern.compile("[aeiouy]{1,2}");
        Matcher matcher = pattern.matcher(s);
        while (matcher.find()) {
            System.out.println(matcher.group());
        }
    }
}
