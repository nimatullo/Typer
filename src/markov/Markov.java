package markov;

import helper.PeekableScanner;
import links.MasterLinkedList;

import java.io.File;

public class Markov {
    public MasterLinkedList parser(File f) {
        MasterLinkedList masterLinkedList = new MasterLinkedList();
        PeekableScanner scan1 = new PeekableScanner(f);
        while (scan1.hasNext()) {
            String word = scan1.next().toLowerCase();
            masterLinkedList.addToList(word, scan1.peek());
        }
        return masterLinkedList;
    }
}
