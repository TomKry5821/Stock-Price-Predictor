package pl.polsl.biai.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import pl.polsl.biai.model.data.ResultRow;
import pl.polsl.biai.model.data.Row;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

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
    public TableColumn resultsTableDateColumn;
    public TableColumn resultsTableCalculatedColumn;
    public TableColumn resultsTableTargetColumn;
    public TableColumn resultsTableErrorColumn;
    public Label tableTitle;
    public Label chartTitle;
    //endregion

    NetworkController networkController = new NetworkController(this);

    public void print(String s){
        outputTextArea.appendText(s);
    }

    public void initializeTable(ArrayList<ResultRow> results){
        ObservableList<ResultRow> resultRowObservableList = FXCollections.observableArrayList(results);
        resultsTableDateColumn.setCellValueFactory(new PropertyValueFactory<ResultRow,String>("date"));
        resultsTableCalculatedColumn.setCellValueFactory(new PropertyValueFactory<ResultRow,String>("calculated"));
        resultsTableTargetColumn.setCellValueFactory(new PropertyValueFactory<ResultRow,String>("target"));
        resultsTableErrorColumn.setCellValueFactory(new PropertyValueFactory<ResultRow,String>("error"));
        resultsTable.setItems(resultRowObservableList);
    }

    public void initializeChart(ArrayList<ResultRow> results){
        XYChart.Series calculatedSeries = new XYChart.Series();
        calculatedSeries.setName("Calculated");
        XYChart.Series expectedSeries = new XYChart.Series();
        expectedSeries.setName("Target");

        for (int i = 0; i < results.size(); i++) {
            calculatedSeries.getData().add(new XYChart.Data(results.get(i).getDate(), results.get(i).getCalculated()));
            expectedSeries.getData().add(new XYChart.Data(results.get(i).getDate(), results.get(i).getTarget()));
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

    public void testButtonClicked(ActionEvent actionEvent){
        networkController.testNetwork();
    }

    public void saveButtonClicked(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Table");
        File file = fileChooser.showSaveDialog(new Stage());
        if (file != null) {
            StringBuilder sb = new StringBuilder("");

            resultsTable.getItems().forEach(row ->{
                sb.append(row);
                sb.append("\n");
            });

            try {
                PrintWriter pw = new PrintWriter(file.getAbsolutePath());
                pw.println(sb);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}