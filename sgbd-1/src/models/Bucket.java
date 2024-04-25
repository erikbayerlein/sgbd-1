package models;

import csv.CsvReader;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Bucket {
    private UUID bucketId;
    private String name;
    private List<Shopping> inData = new ArrayList<>();

    public Bucket(String name, List<Shopping> inData) {
        this.setBucketId(UUID.randomUUID());
        this.setName(name);
        this.setInData(inData);
        createBucketCsv();
    }

    public Bucket(UUID bucketId, String name, List<Shopping> inData) {
        this.setBucketId(bucketId);
        this.setName(name);
        this.setInData(inData);
        createBucketCsv();
    }

    public Bucket(UUID bucketId) {
        this.setBucketId(bucketId);
        this.setName(bucketId.toString());
        createBucketCsv();
    }

    public UUID getBucketId() {
        return bucketId;
    }

    public void createBucketCsv() {
        File bucketCsv = new File("src/buckets/" + getName() + ".csv");
        try {
            bucketCsv.createNewFile();
        } catch (Exception err) {
            System.out.println(err);
        }
    }

    public void setBucketId(UUID bucketId) {
        this.bucketId = bucketId;
    }

    public Bucket(String name) {
        this.setBucketId(UUID.randomUUID());
        this.setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = "bucket_" + bucketId;
    }

    public void updateCsv(String filePath) {
        List<Shopping> shoppingList = CsvReader.readBucketCsv(filePath);
        deleteAllLinesFromCsv(filePath);
        shoppingList.forEach(shopping -> insertShoppingIntoCsv(shopping, filePath));
    }

    public void insertShoppingIntoCsv(Shopping shopping, String filePath) {
        try (FileWriter writer = new FileWriter(filePath, true)) {
            String line = String.format("%d,%.2f,%d\n", shopping.getId(), shopping.getValue(), shopping.getYear());
            writer.append(line);
        } catch (IOException e) {
            System.err.println("Erro ao inserir shopping no arquivo CSV: " + e.getMessage());
        }
    }

    public void deleteAllLinesFromCsv(String filePath) {
        try {
            Files.write(Paths.get(filePath), new byte[0]);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public List<Shopping> getInData() {
        return inData;
    }

    public void setInData(List<Shopping> inData) {
        this.inData = inData;
    }

}