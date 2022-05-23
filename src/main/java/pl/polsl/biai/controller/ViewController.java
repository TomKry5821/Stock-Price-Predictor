package pl.polsl.biai.controller;

import javafx.event.ActionEvent;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;

public class ViewController {

    //region javafx variables
    public Label trainingFilePathLabel;
    public Label testingFilePathLabel;
    public Label dataErrorLabel;
    public TextArea outputTextArea;
    public LineChart resultsChart;
    public TableView resultsTable;
    public DatePicker queryDatePicker;
    public TextField queryVolumeField;
    public TextField queryLowField;
    public TextField queryHighField;
    public TextField queryCloseField;
    public TextField queryOpenField;
    public ChoiceBox queryPredictionsChoiceBox;
    public TableView queryTable;
    public Label queryErrorLabel;
    //endregion

    NetworkController networkController = new NetworkController(this);

    public void print(String s){
        outputTextArea.appendText(s);
    }

    public void initializeChart(List<Double> calculated, List<Double> expected){
        XYChart.Series calculatedSeries = new XYChart.Series();
        calculatedSeries.setName("Calculated");
        XYChart.Series expectedSeries = new XYChart.Series();
        expectedSeries.setName("Target");

        for (int i = 0; i < calculated.size(); i++) {
            calculatedSeries.getData().add(new XYChart.Data(Integer.toString(i), calculated.get(i)));
            expectedSeries.getData().add(new XYChart.Data(Integer.toString(i), expected.get(i)));
        }
        resultsChart.getData().clear();
        resultsChart.getData().addAll(calculatedSeries, expectedSeries);
        resultsChart.setVisible(true);
    }

    public void chooseTrainingFileButtonClicked(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Training File");
        fileChooser.setInitialDirectory(new File("resources"));
        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            networkController.setTrainingFile(file);
            trainingFilePathLabel.setVisible(true);
            trainingFilePathLabel.setText(file.getParentFile().getParentFile().getName()
                    + "/" + file.getParentFile().getName()
                    + "/" + file.getName());
        }
    }

    public void chooseTestingFileButtonClicked(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Testing File");
        fileChooser.setInitialDirectory(new File("resources"));
        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            networkController.setTestingFile(file);
            testingFilePathLabel.setVisible(true);
            testingFilePathLabel.setText(file.getParentFile().getParentFile().getName()
                    + "/" + file.getParentFile().getName()
                    + "/" + file.getName());
        }
    }

    public void trainButtonClicked(ActionEvent actionEvent) {
        networkController.trainNetwork();
    }

    public void testButtonClicked(ActionEvent actionEvent) throws InterruptedException {
        networkController.testNetwork();
    }

    public void showButtonClicked(ActionEvent actionEvent) {

    }
}