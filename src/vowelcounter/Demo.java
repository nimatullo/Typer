package vowelcounter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Demo {
    public static void main(String[] args) {
        String s = "hello, my, friends, how are 12312 you";
        CountVowel countVowel = new CountVowel();
        System.out.println(countVowel.getSyllables(s));
    }
}
