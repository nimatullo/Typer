package vowelcounter;

import java.util.StringTokenizer;

public class CountVowel {
    final private String VOWELS = "[^aeiouy]{1,2}";

    private int count(String word) {
        word = word.toLowerCase();
        if (word.length() == 0) {
            return 0;
        } else if (word.length() <= 2) {
            return 1;
        }

        word = word.replaceFirst("^y", "");

        String[] vowels = word.split(VOWELS);
        if (vowels.length < 1) {
            return 0;
        }

        int counter;
        if (vowels[0].equals("")) {
            counter = vowels.length - 1;
        } else {
            counter = vowels.length;
        }
        counter = silentE(word,counter);

        if (counter == 0)
            return 1;
        return counter;
    }

    private int silentE(String word,int counter) {
        if (word.charAt(word.length() - 1) == 'e') {
            return --counter;
        } else
            return counter;
    }

    public int getSyllables(String s) {
        int count = 0;
        try {
            StringTokenizer tokens = new StringTokenizer(s);
            while (tokens.hasMoreTokens()) {
                String token = tokens.nextToken().replaceAll("[^a-zA-Z0-9 ]","");
                if (!token.matches("[a-zA-Z]*")) {
                    count++;
                }
                else {
                    count += count(token);
                }
                if (count == 245) {
                    System.out.println("ok");
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            e.getMessage();
        }
        return count;
    }
}