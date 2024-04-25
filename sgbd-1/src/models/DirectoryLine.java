package models;

import csv.CsvReader;

import java.util.UUID;

public class DirectoryLine {
    private String index;
    private int localDepth;
    private String bucketName;

    public DirectoryLine(String index, String bucketName, int localDepth) {
        setIndex(index);
        setBucket(bucketName);
        setLocalDepth(localDepth);
    }

    public DirectoryLine(String index, int localDepth) {
        setIndex(index);
        setLocalDepth(localDepth);
    }

    public DirectoryLine(DirectoryLine directoryLineClone) {
        setIndex(directoryLineClone.getIndex());
        setLocalDepth(directoryLineClone.getLocalDepth());
    }

    public DirectoryLine() {}

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public int getLocalDepth() {
        return localDepth;
    }

    public void setLocalDepth(int localDepth) {
        this.localDepth = localDepth;
    }

    public Bucket getBucket() {
        UUID bucketId = UUID.fromString(bucketName.substring(bucketName.indexOf("_") + 1));
        return new Bucket(bucketId, bucketName, CsvReader.readBucketCsv(bucketName) );
    }

    public void setBucket(String bucket) {
        this.bucketName = bucket;
    }
}
