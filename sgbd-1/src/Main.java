import models.Directory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        final String finalPath = "src/io/in.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(finalPath))) {
            String firstLine = br.readLine();
            int closeIconPosition = firstLine.indexOf("/"); // não precisa

            String globalDepth = firstLine.substring(closeIconPosition + 1);
            Directory directory = new Directory(Integer.parseInt(globalDepth));

            BufferedWriter writer = new BufferedWriter(new FileWriter("src/io/out.txt"));
            writer.write("PG/" + globalDepth + "\n");

            List<int[]> tuplesAdded;
            int[] tuplesRemoved;
            int tuplesFound;

            String line;
            while ((line = br.readLine()) != null) {
                switch (line.substring(0, 3)){
                    case "INC":
                        tuplesAdded = directory.insert(Integer.parseInt(line.substring(4)), writer);
                        writer.write("INC:" + line.substring(4) + "/" + tuplesAdded.get(0)[0] + "," + tuplesAdded.get(0)[1] + "\n");
                        break;
                    case "REM":
                        tuplesRemoved = directory.remove(Integer.parseInt(line.substring(4)));
                        writer.write("REM:" + line.substring(4) + "/" + tuplesRemoved[0] + "," + tuplesRemoved[1] + "," + tuplesRemoved[2] + "\n");
                        break;
                    case "BUS":
                        tuplesFound = directory.search(Integer.parseInt(line.substring(5)));
                        writer.write("BUS:" + line.substring(5) + "/" + tuplesFound + "\n");
                        break;
                    default:
                        System.err.println("Invalid command: " + line);
                        break;
                }
            }

            writer.write("PN:/" + directory.getGlobalDepth());

            writer.close();
        } catch (Exception e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }
}