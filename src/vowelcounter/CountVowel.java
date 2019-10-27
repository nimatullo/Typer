package vowelcounter;

import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CountVowel {
    final private String VOWELS = "[aeiouy]{1,2}";

    private int count(String word) {
        word = word.toLowerCase();
        int counter = 0;
        if (word.length() == 0) {
            return 0;
        } else if(word.length() <= 2) {
            return 1;
        }
        word = word.replaceFirst("^y", "");
        Pattern pattern = Pattern.compile(VOWELS);
        Matcher matcher = pattern.matcher(word);
        while (matcher.find()) {
            counter++;
        }
        counter = silentE(word,counter);
        if (counter ==0)
            return 1;
        return counter;
    }

    private int silentE(String word,int counter) {
        if (word.charAt(word.length() - 1) == 'e') {
            return --counter;
        } else
            return counter;
    }

    public static boolean isVowel(char c) {
        return "AEIOUaeiou".indexOf(c) != -1;
    }

    public int getSyllables(String s) {
        int count = 0;
        StringTokenizer tokens = new StringTokenizer(s);
        while (tokens.hasMoreTokens()) {
            String token = tokens.nextToken().replaceAll("[^a-zA-Z0-9 ]","");
            count += count(token);
        }
        return count;
    }
}