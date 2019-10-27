package sample;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.fxmisc.richtext.StyledTextArea;
import vowelcounter.CountVowel;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;

public class Controller {
    @FXML StyledTextArea textArea;
    @FXML MenuItem newMenuItem;
    @FXML MenuItem openMenuItem;
    @FXML MenuBar menuBar;
    @FXML Label wordCountLabel;
    int wordCount = 0;
    PauseTransition pause = new PauseTransition(Duration.seconds(0.5));

    private void exit(Stage primaryStage) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit?", ButtonType.YES,
                ButtonType.NO);
        alert.setTitle("Are You Sure You Want To Exit?");
        alert.setHeaderText("You are about to exit your project. All unsaved progress may be lost.");
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            //save project
            primaryStage.close();
        }
    }

    private void newButton () {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Creating a new document may cause unsaved changes to " +
                "be lost.",
                ButtonType.YES,
                ButtonType.NO);
        alert.setTitle("New?");
        alert.setHeaderText("Are you sure you want to create a new document?");
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            //save project
            textArea.replaceText("");
        }
    }

    private void open(Stage primaryStage) {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "...txt"));
            File selectedFile = fileChooser.showOpenDialog(primaryStage);
            String fileString = new String(Files.readAllBytes(selectedFile.toPath()));
            textArea.replaceText(fileString);
            getWordCount();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getWordCount() {
            String[] sa = textArea.getText().split("[\\s+]");
            sa = Arrays.stream(sa)
                    .filter(s -> (s != null && s.length() > 0))
                    .toArray(String[]::new);
            wordCount = sa.length;
            wordCountLabel.setText(Integer.toString(wordCount));
    }

    public void open(ActionEvent actionEvent) {
        Stage stage = (Stage) menuBar.getScene().getWindow();
        open(stage);
    }
    public void newButton(ActionEvent actionEvent) {
        newButton();
    }
    public void exit(ActionEvent actionEvent) {
        Stage stage = (Stage) menuBar.getScene().getWindow();
        exit(stage);
    }
    public void cut(ActionEvent actionEvent) {
        textArea.cut();
    }
    public void copy(ActionEvent actionEvent) {
        textArea.copy();
    }
    public void paste(ActionEvent actionEvent) {
        textArea.paste();
    }

    public void wordCount(KeyEvent keyEvent) {
        if (keyEvent.getText().matches("[\\sa-zA-Z*]")) {
            pause.setOnFinished(e -> getWordCount());
            pause.playFromStart();
        }
    }

    public void getSyllables(ActionEvent actionEvent) {
        CountVowel cv = new CountVowel();
        System.out.println(cv.getSyllables(textArea.getText()));
    }
}
