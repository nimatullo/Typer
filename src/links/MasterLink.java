package links;

import java.util.LinkedList;

public class MasterLink {
    private String word;
    private LinkedList<BabyLink> linkedList;
    public MasterLink next;
    public MasterLink prev;

    MasterLink(String word) {
        this.word = word;
        next = null;
        prev = null;
        linkedList = new LinkedList<>();
    }

    void setNext(MasterLink nextLink) {
        this.next = nextLink;
    }

    void setPrev(MasterLink prevLink) {
        this.prev = prevLink;
    }

    MasterLink getNext() {
        return next;
    }

    MasterLink getPrev() {
        return prev;
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
