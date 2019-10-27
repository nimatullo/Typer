package vowelcounter;

import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CountVowel {
    final private String VOWELS = "[^aeiouy]+";

    private int count(String word) {
        if (word.length() <= 1) {
            return 1;
        }
        word = word.toLowerCase();
        int counter = 0;

        Pattern pattern = Pattern.compile(VOWELS, Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(word);

        while (matcher.find()) {
            String currentLetter = matcher.group(0);
            counter++;
            if ((matcher.start() == (word.length()-1)) && (currentLetter.equals("e"))) {
                counter--;
            }
            if (matcher.start() != word.length()-1) {
                String nextLetter = Character.toString(word.charAt(matcher.end()));
                if (nextLetter.equals(currentLetter)) {
                    matcher.find();
                    counter--;
                }
            }
          }
        return counter;
    }

    public void test(String word) {
        String [] vowels = word.split(VOWELS);
        for (String s : vowels)
            System.out.print(s + " ");
        System.out.println();
    }

    public int getSyllables(String s) {
        int count = 0;
        StringTokenizer tokens = new StringTokenizer(s);
        while (tokens.hasMoreTokens()) {
            count += count(tokens.nextToken());
        }
        return count;
    }
}
