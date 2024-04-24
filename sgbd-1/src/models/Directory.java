package models;

import hashFunction.Hasher;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

public class Directory {
    private int globalDepth;
    private List<DirectoryLine> directoryLines;

    Logger logger = Logger.getLogger(getClass().getName());

    public Directory(int globalDepth, List<DirectoryLine> directoryLines) {
        setGlobalDepth(globalDepth);
        setDirectoryLines(directoryLines);
    }

    public Directory(int globalDepth) {
        setGlobalDepth(globalDepth);
    }

    public Directory() {
        this.directoryLines = new ArrayList<DirectoryLine>();

        directoryLines.add(new DirectoryLine("000", 2));
        directoryLines.add(new DirectoryLine("001", 2));
        directoryLines.add(new DirectoryLine("010", 3));
        directoryLines.add(new DirectoryLine("011", 2));
        directoryLines.add(new DirectoryLine("100", 3));
        directoryLines.add(new DirectoryLine("101", 2));
        directoryLines.add(new DirectoryLine("110", 3));
        directoryLines.add(new DirectoryLine("111", 3));
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
        List<DirectoryLine> lines = search(key);
        Bucket bucket;

        bucket = lines.get(0).getBucket();

        if (bucket.getInData().size() < 2) { // bucket is not full
            bucket.getInData().add(key);
            logger.info("Key inserted in bucket " + bucket.getName());
        } else { // bucket is full
            Bucket newBucket = new Bucket();

            if (lines.size() > 1) { // localDepth < globalDepth
                if (bucket.getInData().size() == 2) { // bucket is full
                    lines.get(1).setBucket(newBucket);
                    distributeBucket(lines.get(0), lines.get(1), globalDepth);
                } else { // bucket is not full
                    bucket.getInData().add(key);
                }
            } else { // localDepth == globalDepth
                if (bucket.getInData().size() < 2) { // bucket is not full
                    bucket.getInData().add(key);
                } else { // bucket is full
                    duplicateDirectory();
                    DirectoryLine directoryLine = search(key).get(0);
                    distributeBucket(lines.get(0), directoryLine, globalDepth);
                }
            }
        }
    }

    private void distributeBucket(DirectoryLine oldLine, DirectoryLine newLine, int depth){
        oldLine.getBucket().getInData().forEach((key) -> {
            String newBucketName = Hasher.hash(key, depth);
            if (newBucketName.equals(newLine.getIndex())) {
                newLine.getBucket().getInData().add(key);
                oldLine.getBucket().getInData().remove((Integer) key);
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
            line.setIndex(line.getIndex() + "0");
            newLine.setIndex(line.getIndex() + "1");
            newLine.setBucket(line.getBucket());
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