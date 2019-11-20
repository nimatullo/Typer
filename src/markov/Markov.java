package markov;

import helper.PeekableScanner;
import links.MasterLinkedList;

public class Markov {
    public MasterLinkedList parser(String s) {
        MasterLinkedList masterLinkedList = new MasterLinkedList();
        PeekableScanner scan1 = new PeekableScanner(s);
        while (scan1.hasNext()) {
            String word = scan1.next().toLowerCase();
            masterLinkedList.addToList(word, scan1.peek());
        }
        return masterLinkedList;
    }
}
