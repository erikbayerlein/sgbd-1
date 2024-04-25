import csv.CsvReader;
import models.Directory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        List<Integer> csvYears = new ArrayList<>();

        // ------------------------- Adicionar lógica de ler in.txt -------------------------
        final String finalPath = "src/io/in.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(finalPath))) {
            String firstLine = br.readLine();
            int closeIconPosition = firstLine.lastIndexOf(">"); // não precisa

            String globalDepth = firstLine.substring(3, 4);
            Directory directory = new Directory(Integer.parseInt(globalDepth));

            List<String[]> shoppingData = CsvReader.readCsv();
            for (String[] row : shoppingData) {
                csvYears.add(Integer.parseInt(row[2])); // row[0] = id, row[1] = value, row[2] = year
            }

            csvYears.forEach(directory::insert);

            List<Integer> depths;

            String line;
            while ((line = br.readLine()) != null) {
                switch (line.substring(0, 3)){
                    case "INC":
                        depths = directory.insert(Integer.parseInt(line.substring(4)));
                        System.out.println("INC:" + line.substring(4) + "," + depths.get(0) + "," + depths.get(1));
                        break;
                    case "REM":
                        directory.remove(Integer.parseInt(line.substring(4)));
                        break;
                    case "BUS":
                        directory.search(Integer.parseInt(line.substring(5)));
                        break;
                    default:
                        System.err.println("Invalid command: " + line);
                        break;
                }
            }
        } catch (Exception e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }
}