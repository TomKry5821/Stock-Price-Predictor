package pl.polsl.biai.controller;

import pl.polsl.biai.builder.NeuralNetworkBuilder;
import pl.polsl.biai.model.CsvReader;
import pl.polsl.biai.model.DataFrame;
import pl.polsl.biai.model.NeuralNetwork;
import pl.polsl.biai.model.Row;

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

    public NetworkController(ViewController viewController){
        this.viewController=viewController;
    }

    public void setTrainingFile(File trainingFile) {
        this.trainingFile = trainingFile;
    }

    public void setTestingFile(File testingFile) {
        this.testingFile = testingFile;
    }

    public boolean canTrainNetwork() throws Exception {
        if (neuralNetwork != null && neuralNetwork.state == NeuralNetwork.State.TRAINING) {
            throw new Exception("Network is training. Check output window for more information.");
        } else if (neuralNetwork != null && neuralNetwork.state == NeuralNetwork.State.TESTING) {
            throw new Exception("Network is testing. Check output window for more information.");
        } else if (trainingFile == null) {
            throw new Exception("Training file has not been chosen or has wrong format.");
        }
        return true;
    }

    public boolean canTestNetwork() throws Exception {
        if (neuralNetwork != null && neuralNetwork.state == NeuralNetwork.State.TRAINING) {
            throw new Exception("Network is training. Check output window for more information.");
        } else if (neuralNetwork != null && neuralNetwork.state == NeuralNetwork.State.TESTING) {
            throw new Exception("Network is testing. Check output window for more information.");
        } else if (neuralNetwork == null || neuralNetwork.state == NeuralNetwork.State.UNTRAINED) {
            throw new Exception("Network has not been tested yet.");
        } else if (testingFile == null) {
            throw new Exception("Testing file has not been chosen or has wrong format.");
        }
        return true;
    }

    public void testNetwork(){
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

        List<Double> calculated = neuralNetwork.getTestingCalculated();
        List<Double> expected = neuralNetwork.getTestingExpected();

        viewController.print("Testing complete! \nResults:\n");
        for (int i = 0; i < calculated.size(); i++) {
            viewController.print(("Calculated: " + calculated.get(i)) + "\n");
            viewController.print(("Target    : " + expected.get(i)) + "\n");
        }

        viewController.initializeChart(calculated, expected);
    }

    public void trainNetwork(){
        try {
            canTrainNetwork();
            viewController.dataErrorLabel.setVisible(false);
        } catch (Exception e) {
            viewController.dataErrorLabel.setText(e.getMessage());
            viewController.dataErrorLabel.setVisible(true);
            return;
        }

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

        viewController.print("Training started...\n");
        new Thread(() -> {
            neuralNetwork.train(0.1, dataFrame);
            viewController.print("Training complete!\n");
        }).start();
    }
}