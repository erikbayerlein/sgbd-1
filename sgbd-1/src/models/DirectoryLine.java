package models;

public class DirectoryLine {
    private String index;
    private int localDepth;
    private Bucket bucket;

    public DirectoryLine(String index, Bucket bucket, int localDepth) {
        setIndex(index);
        setBucket(bucket);
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
        return bucket;
    }

    public void setBucket(Bucket bucket) {
        this.bucket = bucket;
    }
}
