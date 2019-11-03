package links;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

public class MasterLinkedList extends LinkedList<MasterLink> {

    public void addToList(String word, String secondWord) {
        MasterLink newLink = containsLink(word);
        if (!contains(newLink)) {
            if (!isEmpty()) {
                getLast().setNext(newLink);
            }
            add(newLink);
        }
        BabyLink babyLink = new BabyLink(secondWord);
        if (!newLink.getLinkedList().isEmpty()) {
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