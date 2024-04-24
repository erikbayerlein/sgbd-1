package models;

import hashFunction.Hasher;

import java.beans.Beans;
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

        directoryLines.add(new DirectoryLine("00", new Bucket("A"), 2));
        directoryLines.add(new DirectoryLine("01", new Bucket("B"), 2));
        directoryLines.add(new DirectoryLine("10", new Bucket("C"),2));
        directoryLines.add(new DirectoryLine("11", new Bucket("D"),2));
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

    public DirectoryLine searchByIndex(String index) {
        DirectoryLine line = directoryLines.stream()
                .filter(directoryLine -> Objects.equals(directoryLine.getIndex(), index))
                .findFirst()
                .orElse(null);

        return line;
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

    public void insert(int key) {
        String bucketName = Hasher.hash(key, globalDepth);
        List<DirectoryLine> lines = directoryLines.stream()
                .filter(directoryLine -> Objects.equals(directoryLine.getIndex(), bucketName))
                .toList();

        Bucket bucket = lines.get(0).getBucket();

        if (bucket.getInData().size() <= 2) { // bucket is not full
            bucket.getInData().add(key);
            logger.info("Key inserted in bucket " + bucket.getName());
        } else { // bucket is full
            Bucket newBucket = new Bucket();

            if (lines.size() > 1) { // localDepth < globalDepth
                if (bucket.getInData().size() == 2) { // bucket is full
                    lines.get(1).setBucket(newBucket);
                    distributeBucket(lines.get(0), lines.get(1), globalDepth, key);
                } else { // bucket is not full
                    bucket.getInData().add(key);
                }
            } else { // localDepth == globalDepth
                if (bucket.getInData().size() < 2) { // bucket is not full
                    bucket.getInData().add(key);
                } else { // bucket is full
                    String newIndex = "1" + lines.get(0).getIndex();
                    duplicateDirectory();
                    DirectoryLine line = searchByIndex(newIndex);
                    distributeBucket(lines.get(0), line, globalDepth, key);
                }
            }
        }
    }

    private void distributeBucket(DirectoryLine oldLine, DirectoryLine newLine, int depth, int newValue){
        oldLine.getBucket().getInData().add(newValue);
        List<Integer> auxData = new ArrayList<>(oldLine.getBucket().getInData());

        Bucket newBucket = new Bucket(oldLine.getBucket().getName() + "k");
        newLine.setBucket(newBucket);

        auxData.forEach(key -> {
            String newBucketName = Hasher.hash(key, depth);
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
    }

    public void remove(int key) {
        String bucketIndex = Hasher.hash(key, globalDepth);
        DirectoryLine directoryLine = directoryLines.stream()
                .filter(line -> Objects.equals(line.getIndex(), bucketIndex))
                .findFirst()
                .orElse(null);

        if (directoryLine != null) {
            Bucket bucket = directoryLine.getBucket();
            if (bucket.getInData().contains(key)) {
                bucket.getInData().remove((Integer) key);
                logger.info("Key removed from bucket " + bucket.getName());
            } else {
                logger.info("Key not found in bucket " + bucket.getName());
            }
        } else {
            logger.info("Key not found in directory");
        }
    }

}