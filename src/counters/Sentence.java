package counters;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Sentence {
    public int getSentenceCount(String content) {
        int counter = 0;
        Pattern pattern = Pattern.compile("[a-zA-Z][.!?\n]");
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            counter++;
        }
       return counter;
    }
}
