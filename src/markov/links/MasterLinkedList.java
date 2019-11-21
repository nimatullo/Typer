package markov.links;

import javafx.concurrent.Task;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

public class MasterLinkedList extends LinkedList<MasterLink> {

    public void addToList(String word, String secondWord) {
        word = word.replaceAll("[^a-zA-Z0-9 ]","");
        MasterLink newLink = containsLink(word);
        if (newLink == null) {
            newLink = new MasterLink(word);
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
        if (!newLink.getLinkedList().isEmpty() && secondWord != null) {
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
        return null;
    }

    public String generateParagraph(String startingWord, int numberOfWords) {
        int random;
        MasterLink current = containsLink(startingWord);
        StringBuilder paragraph = new StringBuilder(startingWord + " ");
        for (int i = 0; i < numberOfWords; i++) {
            random = (int)(Math.random() * current.getLinkedList().size());
            paragraph.append(current.getLinkedList().get(random).getValue()).append(" ");
            current = get(i);
        }
        writeToFile(paragraph.toString());
        return paragraph.toString();
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