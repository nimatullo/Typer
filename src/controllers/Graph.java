package controllers;

import counters.MultiLoopCounter;
import counters.SingleLoopCounter;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

public class Graph implements Initializable{
    @FXML
    BorderPane pane;

    @Override
    public void initialize(URL url,ResourceBundle resourceBundle) {
        final NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("Percentage of Text");
        final NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Runtime (milliseconds)");
        final LineChart<Number, Number> lineChart= new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("One Loop vs. Multiple Loops");

        XYChart.Series multipleLoops = new XYChart.Series();
        multipleLoops.setName("Multiple Loop O(3n)");

        XYChart.Series oneLoop = new XYChart.Series();
        oneLoop.setName("One Loop O(n)");
        long [] timesMLC = getNumbersMLC(Controller.paragraphText);
        long [] timesSLC = getNumbersSLC(Controller.paragraphText);


        for (int i = 1; i <= 10; i++) {
            multipleLoops.getData().add(new XYChart.Data<>(i*10, timesMLC[i-1]));
        }

        for (int i = 1; i <= 10; i++) {
            oneLoop.getData().add(new XYChart.Data<>(i*10, timesSLC[i-1]));
        }

        lineChart.getData().addAll(oneLoop, multipleLoops);

        lineChart.setOnZoom(e -> {
            xAxis.setUpperBound(30);
        });
        pane.setCenter(lineChart);
    }

    public long [] getNumbersMLC(String content){
        long [] times = new long[10];
        double factor = 0.1;
        for (int i = 0; i < 10; i++) {
            int div = (int)Math.round(content.length() * factor);
            long time = System.nanoTime();
            updateNumbersMLC(content.substring(0, div));
            times[i] = TimeUnit.MILLISECONDS.convert(System.nanoTime() - time, TimeUnit.NANOSECONDS);
            factor += 0.1;
        }
        return times;
    }

    public long [] getNumbersSLC(String content) {
        long [] times = new long[10];
        double factor = 0.1;
        for (int i = 0; i < 10; i++) {
            int div = (int)Math.round(content.length() * factor);
            long time = System.nanoTime();
            updateNumbersSLC(content.substring(0, div));
            times[i] = TimeUnit.MILLISECONDS.convert(System.nanoTime() - time, TimeUnit.NANOSECONDS);
            factor += 0.1;
        }
        return times;
    }

    private void updateNumbersMLC(String content){
        MultiLoopCounter mlc = new MultiLoopCounter();
        mlc.getSentenceCount(content);
        mlc.getSyllables(content);
        mlc.getWordCount(content);
    }

    private void updateNumbersSLC(String content) {
        SingleLoopCounter slc = new SingleLoopCounter();
        slc.getCount(content);
    }
}
