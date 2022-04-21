package pl.polsl.biai.model;

import com.opencsv.bean.CsvToBeanBuilder;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

public class CsvReader {

    public List<Row> readFromCsv(String filePath) throws FileNotFoundException {
        List<Row> rows = new CsvToBeanBuilder(new FileReader(filePath))
                .withType(Row.class).build().parse();

        return rows;
    }
}
