package csv;

import models.Bucket;
import models.Shopping;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public interface CsvReader {
    static List<Shopping> readShoppingCsv() {
        List<Shopping> data = new ArrayList<>();
        String filePath = "src/csv/shopping.csv";
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                data.add(new Shopping(Integer.parseInt(values[0]), Double.parseDouble(values[1]), Integer.parseInt(values[2])));
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        return data;
    }

    static List<Shopping> readBucketCsv(String filePath) {
        List<Shopping> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                data.add(new Shopping(Integer.parseInt(values[0]), Double.parseDouble(values[1]), Integer.parseInt(values[2])));
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        return data;
    }
}
