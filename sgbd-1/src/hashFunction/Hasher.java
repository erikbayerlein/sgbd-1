package hashFunction;

public interface Hasher {
    static String hash(int key, int depth) {
        String binaryString = Integer.toBinaryString(key);
        if (depth >= binaryString.length()) {
            return binaryString;
        } else {
            return binaryString.substring(binaryString.length()-depth);
        }
    }
}