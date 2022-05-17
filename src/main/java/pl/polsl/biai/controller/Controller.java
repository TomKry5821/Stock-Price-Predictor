package pl.polsl.biai.controller;

import javafx.event.ActionEvent;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import pl.polsl.biai.builder.NeuralNetworkBuilder;
import pl.polsl.biai.model.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    public Label trainingFilePathLabel;
    public Label testingFilePathLabel;
    public Label errorLabel;
    public TextArea outputTextArea;
    public LineChart resultsChart;

    private File trainingFile;
    private File testingFile;
    private NeuralNetwork neuralNetwork;
    private CsvReader csvReader;

    public void chooseTrainingFile(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Training File");
        fileChooser.setInitialDirectory(new File("resources"));
        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            trainingFile = file;
            trainingFilePathLabel.setVisible(true);
            trainingFilePathLabel.setText(file.getParentFile().getParentFile().getName()
                    + "/" + file.getParentFile().getName()
                    + "/" + file.getName());
        }
    }

    public void chooseTestingFile(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Testing File");
        fileChooser.setInitialDirectory(new File("resources"));
        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            testingFile = file;
            testingFilePathLabel.setVisible(true);
            testingFilePathLabel.setText(file.getParentFile().getParentFile().getName()
                    + "/" + file.getParentFile().getName()
                    + "/" + file.getName());
        }
    }

    public void train(ActionEvent actionEvent) {
        if (!canTrain()) return;

        csvReader = new CsvReader();
        List<Row> rows = new ArrayList<>();

        try {
            rows = csvReader.readFromCsv(trainingFile.getAbsolutePath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        DataFrame dataFrame = new DataFrame();
        dataFrame.setRows(rows);
        dataFrame.setExpectedOutputs();
        dataFrame.prepareToMinMaxNormalizationAndNormalize(1.0, 0.0);

        NeuralNetworkBuilder neuralNetworkBuilder = new NeuralNetworkBuilder();
        neuralNetwork = neuralNetworkBuilder.buildInputLayer(5).buildHiddenLayer(5).buildOutputLayer(1).build();
        neuralNetwork.setEpochs(5000);

        outputTextArea.appendText("Training started...\n");
        new Thread(() -> {
            neuralNetwork.train(0.1, dataFrame);
            outputTextArea.appendText("Training complete!\n");
        }).start();
    }

    private boolean canTrain() {
        boolean test = false;
        if (neuralNetwork != null && neuralNetwork.state == NeuralNetwork.State.TRAINING) {
            errorLabel.setText("Network is training. Check output window for more information.");
            errorLabel.setVisible(true);
        } else if (neuralNetwork != null && neuralNetwork.state == NeuralNetwork.State.TESTING) {
            errorLabel.setText("Network is training. Check output window for more information.");
            errorLabel.setVisible(true);
        } else if (trainingFile == null) {
            errorLabel.setText("Training file has not been chosen or has wrong format.");
            errorLabel.setVisible(true);
        } else {
            errorLabel.setVisible(false);
            test = true;
        }
        return test;
    }

    public void test(ActionEvent actionEvent) throws InterruptedException {
        if (!canTest()) return;

        ArrayList<Row> testRows = new ArrayList<>();
        try {
            testRows = csvReader.readFromCsv(testingFile.getAbsolutePath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        DataFrame testDataFrame = new DataFrame();
        testDataFrame.setRows(testRows);
        testDataFrame.setExpectedOutputs();
        testDataFrame.prepareToMinMaxNormalizationAndNormalize(1.0, 0.0);

        ArrayList<Row> finalTestRows = testRows;

        outputTextArea.appendText("Testing started...\n");
        Thread t1 = new Thread(() -> {
            neuralNetwork.test(finalTestRows, testDataFrame.getExpectedOutputs());
        });
        t1.start();
        t1.join();

        List<Double> calculated = neuralNetwork.getTestingCalculated();
        List<Double> expected = neuralNetwork.getTestingExpected();

        outputTextArea.appendText("Testing complete! \nResults:\n");
        for (int i = 0; i < calculated.size(); i++) {
            outputTextArea.appendText(("Calculated: " + calculated.get(i)) + "\n");
            outputTextArea.appendText(("Target    : " + expected.get(i)) + "\n");
        }

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

    private boolean canTest() {
        boolean test = false;
        if (neuralNetwork != null && neuralNetwork.state == NeuralNetwork.State.TRAINING) {
            errorLabel.setText("Network is training. Check output window for more information.");
            errorLabel.setVisible(true);
        } else if (neuralNetwork != null && neuralNetwork.state == NeuralNetwork.State.TESTING) {
            errorLabel.setText("Network is training. Check output window for more information.");
            errorLabel.setVisible(true);
        } else if (neuralNetwork == null || neuralNetwork.state == NeuralNetwork.State.UNTRAINED) {
            errorLabel.setText("Network has not been tested yet.");
            errorLabel.setVisible(true);
        } else if (testingFile == null) {
            errorLabel.setText("Testing file has not been chosen or has wrong format.");
            errorLabel.setVisible(true);
        } else {
            errorLabel.setVisible(false);
            test = true;
        }
        return test;
    }
}