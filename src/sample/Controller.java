package sample;

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
import vowelcounter.CountVowel;

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
    @FXML CheckMenuItem wordCountMenuItem;
    @FXML CheckMenuItem syllableCountMenuItem;
    @FXML MenuBar menuBar;
    @FXML Label wordCountLabel;
    @FXML Label wordCountText;
    @FXML Label syllableCountLabel;
    @FXML Label syllableText;
    @FXML ColorPicker colorPicker;
    @FXML Spinner<Integer> fontSizeSpinner;
    int wordCount = 0;
    PauseTransition pauseWordCount = new PauseTransition(Duration.seconds(0.5));
    PauseTransition pauseSyllableCount = new PauseTransition(Duration.seconds(0.5));

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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getWordCount() {
        int counter = 0;
        Pattern pattern = Pattern.compile("[a-zA-Z][\\s,.!?;:]");
        Matcher matcher = pattern.matcher(textArea.getText());
        while (matcher.find()) {
            counter++;
        }
        wordCount = counter;
        wordCountLabel.setText(Integer.toString(wordCount));
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
        pauseSyllableCount.setOnFinished(e -> getSyllables());
        pauseWordCount.setOnFinished(e -> getWordCount());
        pauseSyllableCount.playFromStart();
        pauseWordCount.playFromStart();
    }

    private void getSyllables() {
        CountVowel countVowel = new CountVowel();
        syllableCountLabel.setText(Integer.toString(countVowel.getSyllables(textArea.getText())));
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
}
