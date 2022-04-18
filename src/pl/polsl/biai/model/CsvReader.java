package pl.polsl.biai.model;

import com.opencsv.bean.CsvToBeanBuilder;
import pl.polsl.biai.model.Row;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class CsvReader {

    public List<Row> readFromCsv(String filePath) throws FileNotFoundException {
        List<Row> rows = new ArrayList<>();

        rows = new CsvToBeanBuilder(new FileReader(filePath))
                .withType(Row.class).build().parse();

        return rows;
    }
}
