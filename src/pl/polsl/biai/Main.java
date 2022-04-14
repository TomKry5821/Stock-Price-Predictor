package pl.polsl.biai;

import pl.polsl.biai.model.DataFrame;
import pl.polsl.biai.model.Row;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        CsvReader csvReader = new CsvReader();
        List<Row> rows = new ArrayList<>();
        rows = csvReader.readFromCsv();

        rows.stream().forEach(row -> {
            System.out.printf("%f, %f, %f, %f, %f\n", row.getHighRate(), row.getDayBeforeCloseRate(), row.getLowRate(), row.getOpenRate(), row.getVolume());
        });
        DataFrame dataFrame = new DataFrame();
        dataFrame.setRows(rows);
        dataFrame.minMaxNormalize(1.0, 0.0);
        dataFrame.getRows().stream().forEach(row -> {
            System.out.printf("%f, %f, %f, %f, %f\n", row.getHighRate(), row.getDayBeforeCloseRate(), row.getLowRate(), row.getOpenRate(), row.getVolume());
        });
    }
}
