package counters;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Word {
    public int getWordCount(String content) {
        int counter = 0;
        Pattern pattern = Pattern.compile("[a-zA-Z][\\s,.!?;:\"-]");
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            counter++;
        }
        return counter;
    }
}
