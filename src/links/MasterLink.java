package links;

import java.util.LinkedList;

public class MasterLink {
    private String word;
    private LinkedList<BabyLink> linkedList;
    private MasterLink next;

    MasterLink(String word) {
        this.word = word;
        next = null;
        linkedList = new LinkedList<>();
    }

    void setNext(MasterLink nextLink) {
        this.next = nextLink;
    }

    MasterLink getNext() {
        return next;
    }

    public void display() {
        System.out.println(word + " ");
    }

    String getWord() {
        return word;
    }

    void addBabyLink(BabyLink babyLink) {
        linkedList.add(babyLink);
    }

    LinkedList<BabyLink> getLinkedList() {
        return linkedList;
    }
}
