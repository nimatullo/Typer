package helper;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class PeekableScanner {
    private Scanner scan;
    private String next;

    public PeekableScanner(String source) {
        scan = new Scanner(source);
        if (scan.hasNext())
            next = scan.next();
        else
            next = null;
    }

    public boolean hasNext() {
        return (next != null);
    }

    public String next() {
        String current = next;
        if (scan.hasNext())
            next = scan.next();
        else
            next = null;
        return current;
    }

    public String peek() {
        return next;
    }
}
