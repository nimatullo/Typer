package sample;

import counters.FleschScore;
import counters.Sentence;
import counters.Word;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.fxmisc.richtext.InlineCssTextArea;
import counters.Syllable;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Controller {
    @FXML InlineCssTextArea textArea;
    @FXML MenuItem newMenuItem;
    @FXML MenuItem openMenuItem;
    @FXML MenuBar menuBar;
    @FXML CheckMenuItem wordCountMenuItem;
    @FXML Label wordCountLabel;
    @FXML Label wordCountText;
    @FXML CheckMenuItem syllableCountMenuItem;
    @FXML Label syllableCountLabel;
    @FXML Label syllableText;
    @FXML CheckMenuItem sentenceCountMenuItem;
    @FXML Label sentenceCountLabel;
    @FXML Label sentenceText;
    @FXML CheckMenuItem fleschScoreMenuItem;
    @FXML Label fleschScoreLabel;
    @FXML Label fleschScoreText;
    @FXML ColorPicker colorPicker;
    @FXML Spinner<Integer> fontSizeSpinner;

    int syllableCount = 0; int wordCount = 0; int sentenceCount = 0; double fleschScore = 0;
    PauseTransition pauseWordCount = new PauseTransition(Duration.seconds(0.5));
    PauseTransition pauseSyllableCount = new PauseTransition(Duration.seconds(0.5));
    PauseTransition pauseSentenceCount = new PauseTransition(Duration.seconds(0.5));
    PauseTransition pauseFleschScore = new PauseTransition(Duration.seconds(0.5));

    private void exit(Stage primaryStage) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit?", ButtonType.YES,
                ButtonType.NO);
        alert.setTitle("Are You Sure You Want To Exit?");
        alert.setHeaderText("You are about to exit your project. All unsaved progress may be lost.");
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            save(new ActionEvent());
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
            save(new ActionEvent());
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
            getSentenceCount();
            getSyllablesCount();
            getFleschScore();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void save(Stage primaryStage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "...txt"));
        fileChooser.setTitle("Save");
        File file = fileChooser.showSaveDialog(primaryStage);
        if (file != null) {
            saveFile(file);
        }
    }

    private void saveFile(File f) {
        try {
            String textContent = textArea.getText();
            BufferedWriter writer = new BufferedWriter(new FileWriter(f));
            writer.write(textContent);

            writer.close();
        } catch (IOException e) {
            System.out.println("Cannot Save");
        }
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

    public void save(ActionEvent actionEvent) {
        Stage primaryStage = (Stage)menuBar.getScene().getWindow();
        save(primaryStage);
    }

    public void updateSyllables(KeyEvent keyEvent) {
        pauseSyllableCount.setOnFinished(e -> getSyllablesCount());
        pauseWordCount.setOnFinished(e -> getWordCount());
        pauseSentenceCount.setOnFinished(e -> getSentenceCount());
        pauseFleschScore.setOnFinished(e -> getFleschScore());
        pauseSyllableCount.playFromStart();
        pauseWordCount.playFromStart();
        pauseSentenceCount.playFromStart();
        pauseFleschScore.playFromStart();
    }

    private void getWordCount() {
        Word word = new Word();
        wordCount = word.getWordCount(textArea.getText());
        wordCountLabel.setText(Integer.toString(wordCount));
    }

    private void getSentenceCount() {
        Sentence sentence = new Sentence();
        sentenceCount = sentence.getSentenceCount(textArea.getText());
        sentenceCountLabel.setText(Integer.toString(sentenceCount));
    }

    private void getSyllablesCount() {
        Syllable syllable = new Syllable();
        syllableCount = syllable.getSyllables(textArea.getText());
        syllableCountLabel.setText(Integer.toString(syllableCount));
    }

    private void getFleschScore() {
        FleschScore fleschScoreDriver = new FleschScore();
        fleschScore = fleschScoreDriver.getFleschScore(sentenceCount, syllableCount, wordCount);
        fleschScoreLabel.setText(Double.toString(fleschScore));
    }

    public void changeColor(ActionEvent actionEvent) {
        String color = colorPicker.getValue().toString();
        color = color.replaceAll("0x","");
        textArea.setStyle(textArea.getSelection().getStart(), textArea.getSelection().getEnd(), "-fx-fill: #" + color +";");
    }

    public void changeFont(MouseEvent mouseEvent) {
        int fontSize = fontSizeSpinner.getValue();
        textArea.setStyle(textArea.getSelection().getStart(), textArea.getSelection().getEnd(),
                "-fx-font-size: " + fontSize + "px;");
    }

    public void showWordCount(ActionEvent actionEvent) {
        if (wordCountMenuItem.isSelected()) {
            wordCountLabel.setOpacity(1);
            wordCountText.setOpacity(1);
        }
        else {
            wordCountLabel.setOpacity(0);
            wordCountText.setOpacity(0);
        }
    }

    public void showSyllableCounter(ActionEvent actionEvent) {
        if (syllableCountMenuItem.isSelected()) {
            syllableCountLabel.setOpacity(1);
            syllableText.setOpacity(1);
        }
        else {
            syllableCountLabel.setOpacity(0);
            syllableText.setOpacity(0);
        }
    }

    public void showSentenceCount(ActionEvent actionEvent) {
        if (sentenceCountMenuItem.isSelected()) {
            sentenceCountLabel.setOpacity(1);
            sentenceText.setOpacity(1);
        }
        else {
            sentenceCountLabel.setOpacity(0);
            sentenceText.setOpacity(0);
        }
    }

    public void showFleschScore(ActionEvent actionEvent) {
        if (fleschScoreMenuItem.isSelected()) {
            fleschScoreLabel.setOpacity(1);
            fleschScoreText.setOpacity(1);
        }
        else {
            fleschScoreText.setOpacity(0);
            fleschScoreLabel.setOpacity(0);
        }
    }
}
