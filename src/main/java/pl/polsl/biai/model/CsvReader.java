package pl.polsl.biai.model;

import com.opencsv.bean.CsvToBeanBuilder;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

public class CsvReader {
    /**
     * Method that reads csv file with passed path
     *
     * @param filePath path to the csv file
     * @return list of records read from file
     * @throws FileNotFoundException when csv file is not found
     */
    public List<Row> readFromCsv(String filePath) throws FileNotFoundException {
        return (List<Row>) new CsvToBeanBuilder(new FileReader(filePath)).withType(Row.class).build().parse();
    }
}
