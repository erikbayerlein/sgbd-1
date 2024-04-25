import csv.CsvReader;
import models.Directory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        // ------------------------- Adicionar lógica de ler in.txt -------------------------
        final String finalPath = "./in.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(finalPath))) {
            String firstLine = br.readLine();
            int closeIconPosition = firstLine.indexOf("/"); // não precisa

            String globalDepth = firstLine.substring(closeIconPosition + 1);
            Directory directory = new Directory(Integer.parseInt(globalDepth));

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