package pl.polsl.biai.controller;

import pl.polsl.biai.builder.NeuralNetworkBuilder;
import pl.polsl.biai.model.NeuralNetwork;
import pl.polsl.biai.model.data.CsvReader;
import pl.polsl.biai.model.data.DataFrame;
import pl.polsl.biai.model.data.ResultRow;
import pl.polsl.biai.model.data.Row;
import pl.polsl.biai.normalizationmethod.MinMaxNormalizable;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class NetworkController {

    private ViewController viewController;

    private File trainingFile;
    private File testingFile;
    private NeuralNetwork neuralNetwork;
    private CsvReader csvReader;

    public NetworkController(ViewController viewController) {
        this.viewController = viewController;
    }

    public void setTrainingFile(File trainingFile) {
        this.trainingFile = trainingFile;
    }

    public void setTestingFile(File testingFile) {
        this.testingFile = testingFile;
    }

    private boolean canTrainNetwork() throws Exception {
        if (neuralNetwork != null && neuralNetwork.state == NeuralNetwork.State.TRAINING) {
            throw new Exception("Network is training. Check output window for more information.");
        } else if (neuralNetwork != null && neuralNetwork.state == NeuralNetwork.State.TESTING) {
            throw new Exception("Network is testing. Check output window for more information.");
        } else if (trainingFile == null) {
            throw new Exception("Training file has not been chosen or has wrong format.");
        }
        return true;
    }

    private boolean canTestNetwork() throws Exception {
        if (neuralNetwork != null && neuralNetwork.state == NeuralNetwork.State.TRAINING) {
            throw new Exception("Network is training. Check output window for more information.");
        } else if (neuralNetwork != null && neuralNetwork.state == NeuralNetwork.State.TESTING) {
            throw new Exception("Network is testing. Check output window for more information.");
        } else if (neuralNetwork == null || neuralNetwork.state == NeuralNetwork.State.UNTRAINED) {
            throw new Exception("Network has not been trained yet.");
        } else if (testingFile == null) {
            throw new Exception("Testing file has not been chosen or has wrong format.");
        }
        return true;
    }

    public void testNetwork() {
        try {
            canTestNetwork();
            viewController.dataErrorLabel.setVisible(false);
        } catch (Exception e) {
            viewController.dataErrorLabel.setText(e.getMessage());
            viewController.dataErrorLabel.setVisible(true);
            return;
        }

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

        viewController.print("Testing started...\n");
        Thread t1 = new Thread(() -> neuralNetwork.test(finalTestRows, testDataFrame.getExpectedOutputs()));
        t1.start();
        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        viewController.print("Testing complete! \nResults:\nDate | Calculated | Target\n");

        List<Double> calculated = neuralNetwork.getTestingCalculated();
        List<Double> expected = neuralNetwork.getTestingExpected();

        denormalizeOutput(testDataFrame, calculated, expected);

        ArrayList<ResultRow> results = new ArrayList<>();
        for (int i = 0; i < testRows.size(); i++) {
            results.add(new ResultRow(testRows.get(i).getDate(), calculated.get(i), expected.get(i)));

        }
        for (var result : results) {
            viewController.print(result.toString());
        }

        viewController.chartTitle.setText("Test results for file " + testingFile.getName());
        viewController.initializeChart(results);
        viewController.tableTitle.setText("Test results for file " + testingFile.getName());
        viewController.initializeTable(results);
    }

    private void denormalizeOutput(DataFrame testDataFrame, List<Double> calculated, List<Double> expected) {
        MinMaxNormalizable denormalizator = new MinMaxNormalizable() {
        };
        testDataFrame.findMaximums();
        double max = testDataFrame.getMaxCloseRate();
        double min = testDataFrame.getMinCloseRate();
        double denormalizedOutput;
        for (int i = 0; i < calculated.size(); i++) {
            denormalizedOutput = denormalizator.minMaxDenormalization(max, min, calculated.get(i));
            calculated.set(i, denormalizedOutput);
        }
        for (int i = 0; i < calculated.size(); i++) {
            denormalizedOutput = denormalizator.minMaxDenormalization(max, min, expected.get(i));
            expected.set(i, denormalizedOutput);
        }
    }

    public void trainNetwork() {
        try {
            canTrainNetwork();
            viewController.dataErrorLabel.setVisible(false);
        } catch (Exception e) {
            viewController.dataErrorLabel.setText(e.getMessage());
            viewController.dataErrorLabel.setVisible(true);
            return;
        }

        csvReader = new CsvReader();
        List<Row> rows;

        try {
            rows = csvReader.readFromCsv(trainingFile.getAbsolutePath());

            DataFrame dataFrame = new DataFrame();
            dataFrame.setRows(rows);
            dataFrame.setExpectedOutputs();
            dataFrame.prepareToMinMaxNormalizationAndNormalize(1.0, 0.0);

            NeuralNetworkBuilder neuralNetworkBuilder = new NeuralNetworkBuilder();
            neuralNetwork = neuralNetworkBuilder.buildInputLayer(5).buildHiddenLayer(9).buildHiddenLayer(9).buildHiddenLayer(9).buildOutputLayer(1).build();
            neuralNetwork.setEpochs(5000);

            viewController.print("Training started...\n");
            new Thread(() -> {
                neuralNetwork.train(0.9, dataFrame);
                viewController.print("Training complete!\n");
            }).start();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<ResultRow> predict(Row data) {
        ArrayList<ResultRow> results = new ArrayList<>();

        System.out.println(data.toString());

        return results;
    }
}
