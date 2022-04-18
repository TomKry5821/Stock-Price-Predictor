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
        rows = csvReader.readFromCsv("resources/train.csv");
        System.out.println("Before normalization");
        rows.stream().forEach(row -> {
            System.out.printf("%f, %f, %f, %f, %f\n", row.getHighRate(), row.getCloseRate(), row.getLowRate(), row.getOpenRate(), row.getVolume());
        });
        DataFrame dataFrame = new DataFrame();
        dataFrame.setRows(rows);
        System.out.println("After normalization");
        dataFrame.prepareToMinMaxNormalizationAndNormalize(1.0, 0.0);
        dataFrame.getRows().stream().forEach(row -> {
            System.out.printf("%f, %f, %f, %f, %f\n", row.getHighRate(), row.getCloseRate(), row.getLowRate(), row.getOpenRate(), row.getVolume());
        });
        dataFrame.setExpectedOutputs();
        NeuralNetwork nn = new NeuralNetwork();
        nn.setInputLayer(5);
        nn.setHiddenLayer(3);
        nn.setOutputLayer(1);
        nn.setEpochs(5000);
        nn.printLayers();
        nn.train(0.5, dataFrame);
        System.out.println("After training");
        //List<Row> testRows = new ArrayList<>();
       // testRows = csvReader.readFromCsv("resources/test.csv");
       // DataFrame testDataFrame = new DataFrame();
       // testDataFrame.setRows(testRows);
       // testDataFrame.prepareToMinMaxNormalizationAndNormalize(1.0, 0.0);
       // testDataFrame.setExpectedOutputs();
       // nn.test(testRows, testDataFrame.getExpectedOutputs());

    }
}
