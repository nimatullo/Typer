package controllers;

import counters.*;
import javafx.animation.PauseTransition;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.fxmisc.richtext.InlineCssTextArea;

import javax.swing.border.Border;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.*;

public class Controller implements Initializable {
    @FXML CheckBox displayCounterCheckBox;
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
    @FXML ComboBox fontChoiceBox;
    @FXML String path = "";
    @FXML Label saveLabel;
    MultiLoopCounter mlc = new MultiLoopCounter();
    static String paragraphText;
    private double x = 0, y = 0;
    private HashMap<String, String> spellCheck;

    int syllableCount = 0; int wordCount = 0; int sentenceCount = 0; double fleschScore = 0;
    PauseTransition waitForFinishedInput = new PauseTransition(Duration.seconds(0.5));

    private void exit(Stage primaryStage) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you want to exit?",ButtonType.YES,
                ButtonType.NO);
        alert.setTitle("Exit");
        alert.setHeaderText("You are about to exit your project. All unsaved progress may be lost.");
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            primaryStage.close();
        }
    }

    private void newButton () {
        if (!textArea.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Creating a new document may cause unsaved changes to " +
                    "be lost.",
                    ButtonType.YES,
                    ButtonType.NO);
            alert.setTitle("New");
            alert.setHeaderText("Are you sure you want to create a new document?");
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                path = "";
                textArea.replaceText("");
            }
        }
    }

    private void open(Stage primaryStage) {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "...txt"));
            File selectedFile = fileChooser.showOpenDialog(primaryStage);
            path = selectedFile.getPath();
            String fileString = new String(Files.readAllBytes(selectedFile.toPath()));
            textArea.replaceText(fileString);
            updateStatusBarNumbers();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.out.println("Open Operation Cancelled.");
        }
    }

    private void save(Stage primaryStage) {
        if (!path.equals("")) {
            saveFile(new File(path));
        } else {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "...txt"));
            fileChooser.setTitle("Save");
            File file = fileChooser.showSaveDialog(primaryStage);
            try {
                path = file.getPath();
            } catch (NullPointerException ignored) {
            }
            saveFile(file);
        }
    }

    private void saveFile(File f) {
        try {
            String textContent = textArea.getText();
            BufferedWriter writer = new BufferedWriter(new FileWriter(f));
            writer.write(textContent);
            writer.close();
            saveLabel.setVisible(true);
        } catch (IOException  | NullPointerException e) {
            System.out.println("Save operation cancelled or failed.");
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

    public void updateStatusBarNumbers() {
        saveLabel.setVisible(false);
        getWordCount();
        getSentenceCount();
        getSyllablesCount();
        getFleschScore();
    }

    private void getWordCount() {
        wordCount = mlc.getWordCount(textArea.getText());
        wordCountLabel.setText(Integer.toString(wordCount));
    }

    private void getSentenceCount() {
        sentenceCount = mlc.getSentenceCount(textArea.getText());
        sentenceCountLabel.setText(Integer.toString(sentenceCount));
    }

    private void getSyllablesCount() {
        syllableCount = mlc.getSyllables(textArea.getText());
        syllableCountLabel.setText(Integer.toString(syllableCount));
    }

    private void getFleschScore() {
        fleschScore = mlc.getFleschScore(sentenceCount, syllableCount, wordCount);
        fleschScoreLabel.setText(Double.toString(fleschScore));
    }

    public void changeColor(ActionEvent actionEvent) {
        IndexRange selection = textArea.getSelection();
        String color = colorPicker.getValue().toString();
        color = color.replaceAll("0x","");
        String currentStyle = textArea.getDocument().getStyleAtPosition(selection.getEnd());

        if (!currentStyle.contains("-fx-fill")) {
            textArea.setStyle(selection.getStart(), selection.getEnd(), currentStyle + "-fx-fill: #" + color + ";");
        } else {
            textArea.setStyle(textArea.getSelection().getStart(), textArea.getSelection().getEnd(),
                    swapColor(currentStyle, color));
        }
    }

    private String swapColor(String style,String color) {
        return style.replaceAll("(-fx-fill: #\\S+;)", "-fx-fill: #" + color + ";");
    }

    public void changeFontSize(MouseEvent mouseEvent) {
        IndexRange selection = textArea.getSelection();
        int fontSize = fontSizeSpinner.getValue();
        String currentStyle = textArea.getDocument().getStyleAtPosition(selection.getEnd());

        if (!currentStyle.contains("-fx-font-size")) {
            textArea.setStyle(selection.getStart(), selection.getEnd(), currentStyle + "-fx-font-size: " + fontSize + "px;");
        } else {
            textArea.setStyle(textArea.getSelection().getStart(), textArea.getSelection().getEnd(),
                    swapFontSize(currentStyle, fontSize));
        }
    }

    private String swapFontSize(String style,int fontSize) {
        return style.replaceAll("(-fx-font-size: \\d+px;)", "-fx-font-size: " + fontSize + "px;");
    }

    public void changeFontFamily(ActionEvent actionEvent) {
        String font = fontChoiceBox.getValue().toString();
        IndexRange selection = textArea.getSelection();
        String currentStyle = textArea.getDocument().getStyleAtPosition(selection.getEnd());

        if (!currentStyle.contains("-fx-font-family")) {
            String style = currentStyle + "-fx-font-family: '" + font + "';";
            textArea.setStyle(selection.getStart(), selection.getEnd(),  style);
        } else {
            textArea.setStyle(textArea.getSelection().getStart(), textArea.getSelection().getEnd(),
                    swapFontFamily(currentStyle, font));
        }
    }

    private String swapFontFamily(String currentStyle,String font) {
        return currentStyle.replaceAll("(-fx-font-family: '.*';)", "-fx-font-family: '" + font + "';");
    }


    public void showWordCount(ActionEvent actionEvent) {
        if (wordCountMenuItem.isSelected() || displayCounterCheckBox.isSelected()) {
            wordCountLabel.setVisible(true);
            wordCountText.setVisible(true);
        }
        else {
            wordCountLabel.setVisible(false);
            wordCountText.setVisible(false);
        }
    }

    public void showSyllableCounter(ActionEvent actionEvent) {
        if (syllableCountMenuItem.isSelected() || displayCounterCheckBox.isSelected()) {
            syllableCountLabel.setVisible(true);
            syllableText.setVisible(true);
        }
        else {
            syllableCountLabel.setVisible(false);
            syllableText.setVisible(false);
        }
    }

    public void showSentenceCount(ActionEvent actionEvent) {
        if (sentenceCountMenuItem.isSelected() || displayCounterCheckBox.isSelected()) {
            sentenceCountLabel.setVisible(true);
            sentenceText.setVisible(true);
        }
        else {
            sentenceCountLabel.setVisible(false);
            sentenceText.setVisible(false);
        }
    }

    public void showFleschScore(ActionEvent actionEvent) {
        if (fleschScoreMenuItem.isSelected() || displayCounterCheckBox.isSelected()) {
            fleschScoreLabel.setVisible(true);
            fleschScoreText.setVisible(true);
        }
        else {
            fleschScoreText.setVisible(false);
            fleschScoreLabel.setVisible(false);
        }
    }

    public void generateText(ActionEvent actionEvent) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getClassLoader().getResource("view/markov.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Markov Generator");
            stage.initStyle(StageStyle.UNDECORATED);
            root.setEffect(new DropShadow());
            Scene scene = new Scene(root);
            stage.setScene(scene);

            root.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    x = stage.getX() - mouseEvent.getScreenX();
                    y = stage.getY() - mouseEvent.getScreenY();
                }
            });
            root.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    stage.setX(mouseEvent.getScreenX() + x);
                    stage.setY(mouseEvent.getScreenY() + y);
                }
            });

            paragraphText = textArea.getText();
            stage.showAndWait();
            if (!paragraphText.isEmpty()) {
                textArea.replaceText(paragraphText);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void displayCounters(ActionEvent actionEvent) {
        if (displayCounterCheckBox.isSelected()) {
            fleschScoreMenuItem.setSelected(true);
            sentenceCountMenuItem.setSelected(true);
            syllableCountMenuItem.setSelected(true);
            wordCountMenuItem.setSelected(true);
        } else {
            fleschScoreMenuItem.setSelected(false);
            sentenceCountMenuItem.setSelected(false);
            syllableCountMenuItem.setSelected(false);
            wordCountMenuItem.setSelected(false);
        }
        showFleschScore(new ActionEvent());
        showSentenceCount(new ActionEvent());
        showSyllableCounter(new ActionEvent());
        showWordCount(new ActionEvent());
    }

    public void showGraph(ActionEvent actionEvent) {
        Parent root;
        try {
            paragraphText = textArea.getText();
            root = FXMLLoader.load(getClass().getClassLoader().getResource("view/graph.fxml"));
            Stage stage = new Stage();
            stage.setTitle("One Loop vs Multiple Loops");
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url,ResourceBundle resourceBundle) {
        textArea.textProperty().addListener(e -> {
            waitForFinishedInput.setOnFinished(x -> updateStatusBarNumbers());
            waitForFinishedInput.playFromStart();
        });
        spellCheck = new HashMap<>();
        File dictionaryTxt = new File("/Users/sherzodnimatullo/IdeaProjects/CSE 218/Typer/src/controllers/resources/dictionary.txt");
        try {
            Scanner scan = new Scanner(dictionaryTxt);
            while (scan.hasNext()) {
                String value = scan.next();
                spellCheck.put(value, value);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void checkSpelling(ActionEvent actionEvent) {
        ArrayList<String> misspelledWords = new ArrayList<>();
        StringTokenizer tokenizer = new StringTokenizer(textArea.getText());
        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            token = token.toLowerCase().replaceAll("(\\W)|(\\d)", "");
            if (spellCheck.get(token) == null) {
                misspelledWords.add(token);
            }
        }
        showMisspelledWords(misspelledWords);
    }

    private void showMisspelledWords(ArrayList<String> misspelledWords) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Spell Check");
        alert.setHeaderText("You have " + misspelledWords.size() + " misspelled words.");
        String content = "";
        for (String s : misspelledWords) {
            content += s + "\n";
        }
        TextArea area = new TextArea(content);
        area.setEditable(false);
        alert.getDialogPane().setContent(area);
        alert.showAndWait();
    }
}
