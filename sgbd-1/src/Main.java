import csv.CsvReader;
import models.Directory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Directory directory = new Directory(2);

        // ------------------------- Adicionar lógica de ler in.txt -------------------------
        final String finalPath = "./in.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(finalPath))) {
            String firstLine = br.readLine();
            if (firstLine != null) {
                int closeIconPosition = firstLine.lastIndexOf(">");
                String globalDepth = firstLine.substring(4, closeIconPosition);
                directory.setGlobalDepth(Integer.parseInt(globalDepth));
            }

            String line;
            while ((line = br.readLine()) != null) {
                switch (line.substring(0, 3)){
                    case "INC":
                        directory.insert(Integer.parseInt(line.substring(4)));
                        break;
                    case "REM":
                        directory.remove(Integer.parseInt(line.substring(5)));
                        break;
                    case "BUS":
                        directory.search(Integer.parseInt(line.substring(6)));
                        break;
                    default:
                        System.err.println("Invalid command: " + line);
                        break;
                }
            }
        } catch (Exception e) {
            System.err.println("Error reading file: " + e.getMessage());
        }

        //----------------------------------------------------------------------------------

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


    }
}