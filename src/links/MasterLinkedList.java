package links;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

public class MasterLinkedList extends LinkedList<MasterLink> {

//    public void addToList(String word, String secondWord) {
//        MasterLink newLink = containsLink(word);
//        if (!contains(newLink)) {
//            if (!isEmpty()) {
//                System.out.println("adding " + word + " to masterList.");
//                getLast().setNext(newLink);
//            }
//            add(newLink);
//        }
//        BabyLink babyLink = new BabyLink(secondWord);
//        if (!newLink.getLinkedList().isEmpty()) {
//            System.out.println("adding " + secondWord + " to " + word);
//            newLink.getLinkedList().getLast().setNext(babyLink);
//        }
//        newLink.addBabyLink(babyLink);
//    }

    public void addToList(String word, String secondWord) {
        word = word.replaceAll("[^a-zA-Z0-9 ]","");
        MasterLink newLink = search(word);
        if (!contains(newLink)) {
            if (!isEmpty()) {
                System.out.println("adding " + word + " to masterList.");
                MasterLink temp = getLast();
                newLink.setPrev(temp);
                add(newLink);
                temp.setNext(newLink);
            }
            else {
                add(newLink);
            }
        }
        BabyLink babyLink = new BabyLink(secondWord);
        if (!newLink.getLinkedList().isEmpty()) {
            System.out.println("adding " + secondWord + " to " + word);
            newLink.getLinkedList().getLast().setNext(babyLink);
        }
        newLink.addBabyLink(babyLink);
    }

    private MasterLink containsLink(String word) {
        for (MasterLink masterLink : this) {
            if (masterLink.getWord().equals(word)) {
                return masterLink;
            }
        }
        return new MasterLink(word);
    }

    public MasterLink search(String word) {
        MasterLink current = null;
        for (MasterLink masterLink : this) {
            if (masterLink.getWord().equals(word)) {
                current = masterLink;
                break;
            }
        }
        if (indexOf(current) == 0) {
            return current;
        }
        if (current == null) {
            return new MasterLink(word);
        }
        else {
            while (current.prev.prev != null) {
                int i = indexOf(current);
                MasterLink prev = current.getPrev();
                MasterLink next = current.getNext();
                current.next = (prev);
                current.prev = (prev.prev);
                prev.prev.next = (current);
                prev.next = (next);

                MasterLink temp = current;
                set(i, current);
                set(i-1, prev);
            }
            getFirst().next = current.next;
            getFirst().prev = current;
            current.next = getFirst();
            current.prev = null;
            set(1, getFirst());
            set(0, current);
        }
        return current;
    }

    public String generateParagraph(String startingWord, int numberOfWords) {
        int random;
        MasterLink current = containsLink(startingWord);
        String paragraph = startingWord + " ";
        for (int i = 0; i < numberOfWords; i++) {
            random = (int)(Math.random() * current.getLinkedList().size());
            paragraph += (current.getLinkedList().get(random).getValue() + " ");
            current = current.getNext();
        }
        writeToFile(paragraph);
        return paragraph;
    }

    private void writeToFile(String paragraph) {
        File newFile = createPath();
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(newFile));
            writer.write(paragraph);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private File createPath() {
        File directory = new File("output/");
        directory.mkdir();
        File file = new File("output/output.txt");
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }
}