package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.controls.JFXTextField;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import markov.Markov;
import markov.links.MasterLinkedList;

public class MarkovController {
    @FXML
    VBox vBox;
    @FXML
    JFXSpinner spinner;
    @FXML
    JFXTextField startingWord;
    @FXML
    JFXTextField numberOfWords;
    @FXML
    JFXButton generateButton = new JFXButton();
    private final Markov markov = new Markov();

    public void generateParagraph(ActionEvent actionEvent) {
        JFXSpinner spinner = new JFXSpinner();
        Label label = new Label("Generating Paragraph...");
        vBox.getChildren().setAll(spinner, label);

        Service<Void> backgroundThread = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        MasterLinkedList masterLinks = markov.parser(Controller.paragraphText);
                        Controller.paragraphText = masterLinks.generateParagraph(startingWord.getText(),
                                Integer.parseInt(numberOfWords.getText()));
                        return null;
                    }
                };
            }
        };
        backgroundThread.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent workerStateEvent) {
                Stage stage = (Stage)vBox.getScene().getWindow();
                stage.close();
            }
        });
        backgroundThread.restart();
    }

    public void close(ActionEvent actionEvent) {
        Stage stage = (Stage)vBox.getScene().getWindow();
        stage.close();
    }
}