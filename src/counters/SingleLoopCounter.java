package counters;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SingleLoopCounter {
    public int [] getCount(String s) {

        int sentCount = 0;
        int wordCount = 0;
        int sylCount = 0;

        s = s.toLowerCase();
        s = cleanUp(s);
        if (s.matches("(.*ing$)")) {
            s = s.replaceAll("ing", "");
            sylCount++;
        }

        Pattern pattern = Pattern.compile("([a-zA-Z][\\s,;:\"-])|([a-zA-Z][.!?])|([aeiouy]{1,2})");
        Matcher matcher = pattern.matcher(s);

        while (matcher.find()) {
            if (matcher.group(1) != null) {
                sentCount++;
                wordCount++;
            }
            if (matcher.group(2) != null)
                wordCount++;
            if (matcher.group(3) != null) {
                sylCount++;
                sylCount = silentE(s, sylCount);
            }
        }
        return new int[]{sentCount, wordCount, sylCount};
    }

    private String cleanUp(String match) {
        match = match.replaceFirst("^y", "");
        return match;
    }

    private int silentE(String word,int counter) {
        if (word.charAt(word.length() - 1) == 'e') {
            return --counter;
        } else
            return counter;
    }
}
