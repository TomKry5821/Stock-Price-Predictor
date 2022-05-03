package pl.polsl.biai;

import pl.polsl.biai.model.CsvReader;
import pl.polsl.biai.model.DataFrame;
import pl.polsl.biai.model.NeuralNetwork;
import pl.polsl.biai.model.Row;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        CsvReader csvReader = new CsvReader();

        List<Row> rows = new ArrayList<>();
        try {
            rows = csvReader.readFromCsv("resources/train.csv");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        DataFrame dataFrame = new DataFrame();
        dataFrame.setRows(rows);
        dataFrame.setExpectedOutputs();
        dataFrame.prepareToMinMaxNormalizationAndNormalize(1.0, 0.0);

        NeuralNetwork nn = new NeuralNetwork();
        nn.setInputLayer(5);
        nn.setHiddenLayer(5);
        nn.setOutputLayer(1);
        nn.setEpochs(5000);
        nn.printLayers();
        nn.train(0.5, dataFrame);

        System.out.println("After training");
        nn.printLayers();

        List<Row> testRows = new ArrayList<>();
        try {
            testRows = csvReader.readFromCsv("resources/test.csv");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        DataFrame testDataFrame = new DataFrame();
        testDataFrame.setRows(testRows);
        testDataFrame.setExpectedOutputs();
        testDataFrame.prepareToMinMaxNormalizationAndNormalize(1.0, 0.0);

        nn.test(testRows, testDataFrame.getExpectedOutputs());
        nn.printLayers();
    }
}
