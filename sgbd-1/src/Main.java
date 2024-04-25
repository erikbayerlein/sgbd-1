import csv.CsvReader;
import models.Directory;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        // ------------------------- Adicionar lógica de ler in.txt -------------------------

        //----------------------------------------------------------------------------------

        Directory directory = new Directory(2);

        List<Integer> csvYears = new ArrayList<>();

        try {
            List<String[]> shoppingData = CsvReader.readCsv();
            for (String[] row : shoppingData) {
                csvYears.add(Integer.parseInt(row[2])); // row[0] = id, row[1] = value, row[2] = year
            }
        } catch (Exception err) {
            System.err.println("Error reading CSV file: " + err.getMessage());
        }

        csvYears.forEach(directory::insert);

        directory.insert(2014);
        directory.insert(1995);
        directory.insert(2012);
        directory.insert(2013);
        directory.insert(1992);
        directory.insert(2020);
        directory.insert(2024);
        directory.insert(1994);
        directory.insert(1998);

        directory.search(2014);
        directory.search(1995);
        directory.search(2012);
        directory.search(2013);
        directory.search(1992);
        directory.search(2020);
        directory.search(2024);
        directory.search(1994);
        directory.search(1998);

        directory.remove(2014);
        directory.remove(1995);
        directory.remove(2012);
        directory.remove(2013);
        directory.remove(1992);
        directory.remove(2020);
        directory.remove(2024);
        directory.remove(1994);
        directory.remove(1998);

        directory.search(2014);
        directory.search(1995);
        directory.search(2012);
        directory.search(2013);
        directory.search(1992);
        directory.search(2020);
        directory.search(2024);
        directory.search(1994);
        directory.search(1998);

        System.out.println("Hello world!");
    }
}