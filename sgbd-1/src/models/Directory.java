package models;

import csv.CsvReader;
import hashFunction.Hasher;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

public class Directory {
    private int globalDepth;
    private List<DirectoryLine> directoryLines = new ArrayList<>();

    Logger logger = Logger.getLogger(getClass().getName());

    public Directory(int globalDepth) {
        setGlobalDepth(globalDepth);

        List<String> binaryNumbers = generateBinaryNumbers(globalDepth);
        binaryNumbers.forEach(binary -> {
            Bucket bucket = new Bucket(binary);
            DirectoryLine line = new DirectoryLine(binary, bucket, globalDepth);
            directoryLines.add(line);
        });

//        directoryLines.add(new DirectoryLine("00", new Bucket("A"), 2));
//        directoryLines.add(new DirectoryLine("01", new Bucket("B"), 2));
//        directoryLines.add(new DirectoryLine("10", new Bucket("C"),2));
//        directoryLines.add(new DirectoryLine("11", new Bucket("D"),2));
    }


    public int getGlobalDepth() {
        return globalDepth;
    }

    public void setGlobalDepth(int globalDepth) {
        this.globalDepth = globalDepth;
    }

    public List<DirectoryLine> getDirectoryLines() {
        return directoryLines;
    }

    public void setDirectoryLines(List<DirectoryLine> directoryLines) {
        this.directoryLines = directoryLines;
    }

    private static List<String> generateBinaryNumbers(int e) {
        List<String> binaryNumbers = new ArrayList<>();
        int totalNumbers = (int) Math.pow(2, e);

        for (int i = 0; i < totalNumbers; i++) {
            String binary = Integer.toBinaryString(i);
            while (binary.length() < e) {
                binary = "0" + binary;
            }
            binaryNumbers.add(binary);
        }

        return binaryNumbers;
    }

    public DirectoryLine searchByIndex(String index) {
        return directoryLines.stream()
                .filter(directoryLine -> Objects.equals(directoryLine.getIndex(), index))
                .findFirst()
                .orElse(null);
    }

    public List<DirectoryLine> search(int key) {
        String bucketIndex = Hasher.hash(key, globalDepth);
        List<DirectoryLine> lines = directoryLines.stream()
                .filter(directoryLine -> Objects.equals(directoryLine.getIndex(), bucketIndex))
                .toList();

        lines.forEach(line -> {
            Bucket bucket = line.getBucket();
            if (bucket != null) {
                if (bucket.getInData().contains(key)) {
                    logger.info("key found in bucket " + bucket.getName());
                } else {
                    logger.info("key not found in bucket " + bucket.getName());
                }
            } else {
                logger.info("Bucket does not exist");
            }
        });

        return lines;
    }

    public List<Integer> insert(int key) {
        String bucketName = Hasher.hash(key, globalDepth);

        List<Shopping> shoppingData = CsvReader.readCsv();

        Shopping shoppingToBeAdded = shoppingData
                .stream()
                .filter(shopping -> shopping.getYear() == key)
                .findFirst()
                .orElse(null);

        List<DirectoryLine> lines = directoryLines.stream()
                .filter(directoryLine -> Objects.equals(directoryLine.getIndex(), bucketName))
                .toList();

        List<Integer> depths = new ArrayList<>();

        Bucket bucket = lines.get(0).getBucket();

        if (bucket.getInData().size() <= 2) { // bucket is not full
            bucket.getInData().add(shoppingToBeAdded);
            logger.info("Key inserted in bucket " + bucket.getName());
        } else { // bucket is full
            Bucket newBucket = new Bucket();

            if (lines.size() > 1) { // localDepth < globalDepth
                if (bucket.getInData().size() == 2) { // bucket is full
                    lines.get(1).setBucket(newBucket);
                    distributeBucket(lines.get(0), lines.get(1), globalDepth, shoppingToBeAdded);
                } else { // bucket is not full
                    bucket.getInData().add(shoppingToBeAdded);
                }
            } else { // localDepth == globalDepth
                if (bucket.getInData().size() < 2) { // bucket is not full
                    bucket.getInData().add(shoppingToBeAdded);
                } else { // bucket is full
                    String newIndex = "1" + lines.get(0).getIndex();
                    duplicateDirectory();
                    DirectoryLine line = searchByIndex(newIndex);
                    distributeBucket(lines.get(0), line, globalDepth, shoppingToBeAdded);
                }
            }
        }
        depths.add(globalDepth);
        depths.add(lines.get(0).getLocalDepth());

        return depths;
    }

    private void distributeBucket(DirectoryLine oldLine, DirectoryLine newLine, int depth, Shopping newValue){
        oldLine.getBucket().getInData().add(newValue);
        List<Shopping> auxData = new ArrayList<>(oldLine.getBucket().getInData());

        Bucket newBucket = new Bucket(oldLine.getBucket().getName() + "k");
        newLine.setBucket(newBucket);

        auxData.forEach(key -> {
            String newBucketName = Hasher.hash(key.getYear(), depth);
            if (newBucketName.equals(newLine.getIndex())) {
                newLine.getBucket().getInData().add(key);
                oldLine.getBucket().getInData().remove(key);
            }
        });

        oldLine.setLocalDepth(depth);
        newLine.setLocalDepth(depth);
    }

    private void duplicateDirectory() {
        globalDepth++;
        int size = directoryLines.size();
        for (int i = 0; i < size; i++) {
            DirectoryLine line = directoryLines.get(i);
            DirectoryLine newLine = new DirectoryLine();
            newLine.setIndex("1" + line.getIndex());
            line.setIndex("0" + line.getIndex());
            newLine.setBucket(line.getBucket());

            Bucket bucket = newLine.getBucket();
            bucket.setName(bucket.getName() + i);

            newLine.setLocalDepth(line.getLocalDepth());
            directoryLines.add(newLine);
        }

        System.out.println("Directory duplicated");
    }

    public void remove(int key) {
        String bucketIndex = Hasher.hash(key, globalDepth);
        DirectoryLine directoryLine = directoryLines.stream()
                .filter(line -> Objects.equals(line.getIndex(), bucketIndex))
                .findFirst()
                .orElse(null);

        if (directoryLine != null) {
            Bucket bucket = directoryLine.getBucket();
            if (bucket.getInData().stream().anyMatch(shopping -> shopping.getYear() == key)){
                Shopping shoppingToBeDeleted = bucket.getInData().stream().filter(s -> s.getYear() == key).findFirst().orElse(null);
                bucket.getInData().remove(shoppingToBeDeleted);
                logger.info("Key removed from bucket " + bucket.getName());
            } else {
                logger.info("Key not found in bucket " + bucket.getName());
            }
        } else {
            logger.info("Key not found in directory");
        }
    }

}