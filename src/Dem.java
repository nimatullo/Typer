import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.util.HashMap;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Dem extends Thread {
    public static void main(String[] args) {
        String s = "Hello my friend! How are you today.";
        Pattern pattern = Pattern.compile("([a-zA-Z][\\s,;:\"-])|([a-zA-Z][.!?])|([aeiouy]{1,2})");
        Matcher matcher = pattern.matcher(s);

        while (matcher.find()) {
            String x = matcher.group(1);
            String y = matcher.group(2);
            String z = matcher.group(3);

            if (x != null) {
                System.out.println("Words " + x);
            }
            else if (y != null) {
                System.out.println("Sentences " + y);
            }
            else if (z != null) {
                System.out.println("Syllables " + z);
            }
        }

    }
}
