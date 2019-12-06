import java.util.StringTokenizer;

public class Dem {
    public static void main(String[] args) {
        String s = "Hello my friends welcome to the new generation.";
        int length = s.length();
        System.out.println(s.length());
        double factor = 0.1;
        for (int i = 0; i < 10; i++) {
            int newlength = (int)Math.round(s.length() * factor);
            System.out.println(s.substring(0, newlength));
            factor += 0.1;
        }
    }
}
