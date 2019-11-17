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
        Pattern patternSentence = Pattern.compile("[a-zA-Z][.!?]");
        Matcher sentence = patternSentence.matcher(s);
        Pattern patternWord = Pattern.compile("[a-zA-Z][\\s,.!?;:\"-]");
        Matcher word = patternWord.matcher(s);
        Pattern patternSyl = Pattern.compile("[aeiouy]{1,2}");
        Matcher syllable = patternSyl.matcher(s);

        int counter = 0;
        while (counter < s.length()) {
            if (sentence.find())
                sentCount++;
            if (word.find())
                wordCount++;
            if (syllable.find()) {
                sylCount++;
                sylCount = silentE(s, sylCount);
            }
            counter++;
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
