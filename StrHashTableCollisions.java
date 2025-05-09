import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * this is a hash table class that handles collisions using linked lists.
 * it stores key-value pairs and resizes itself when it gets too full.
 * it's pretty basic but works for most stuff.
 */
public class StrHashTableCollisions {
    private LinkedList<Node>[] bucketArray;

    /**
     * makes a new hash table with default size of 10.
     */
    public StrHashTableCollisions() {
        this(10);
    }

    /**
     * makes a new hash table with a given size.
     * @param size the size of the hash table.
     */
    public StrHashTableCollisions(int size) {
        bucketArray = new LinkedList[size];
    }

    /**
     * calculates the hash index for a given key.
     * @param k the key to hash.
     * @return the hash index.
     */
    private int hashFunction(String k) {
        int[] asciiValues = new int[k.length()];
        for (int i = 0; i < k.length(); i++) {
            asciiValues[i] = (int) k.charAt(i);
        }

        int chunkSize = 3;
        ArrayList<String> chunks = new ArrayList<>();
        StringBuilder currentChunk = new StringBuilder();

        for (int i = 0; i < asciiValues.length; i++) {
            currentChunk.append(asciiValues[i]);
            if ((i + 1) % chunkSize == 0 || i == asciiValues.length - 1) {
                chunks.add(currentChunk.toString());
                currentChunk = new StringBuilder();
            }
        }

        int totalSum = 0;
        for (String chunk : chunks) {
            totalSum += Integer.parseInt(chunk);
        }

        return totalSum % bucketArray.length;
    }

    /**
     * inserts a key-value pair into the hash table.
     * if the table is too full, it will resize itself.
     * @param k the key to insert.
     * @param v the value to insert.
     */
    public void insert(String k, String v) {
        if ((double) count() / bucketArray.length >= 0.8) {
            rehash();
        }

        int index = hashFunction(k);
        if (bucketArray[index] == null) {
            bucketArray[index] = new LinkedList<>();
        }

        for (Node node : bucketArray[index]) {
            if (node.getKey().equals(k)) {
                return; // key already exists, do not insert
            }
        }

        bucketArray[index].add(new Node(k, v));
    }

    /**
     * deletes a key-value pair from the hash table.
     * if the key doesnt exist, it prints a message.
     * @param k the key to delete.
     */
    public void delete(String k) {
        int index = hashFunction(k);
        if (bucketArray[index] != null) {
            Iterator<Node> iterator = bucketArray[index].iterator();
            while (iterator.hasNext()) {
                if (iterator.next().getKey().equals(k)) {
                    iterator.remove();
                    return;
                }
            }
        }
        System.out.println("key does not exist in table");
    }

    /**
     * checks if the hash table contains a key.
     * @param k the key to check.
     * @return true if the key exists, false otherwise.
     */
    public boolean contains(String k) {
        int index = hashFunction(k);
        if (bucketArray[index] != null) {
            for (Node node : bucketArray[index]) {
                if (node.getKey().equals(k)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * gets the value for a given key.
     * if the key doesnt exist, it prints a message.
     * @param k the key to get the value for.
     * @return the value for the key, or null if not found.
     */
    public String get(String k) {
        int index = hashFunction(k);
        if (bucketArray[index] != null) {
            for (Node node : bucketArray[index]) {
                if (node.getKey().equals(k)) {
                    return node.getValue();
                }
            }
        }
        System.out.println("key does not exist in table");
        return null;
    }

    /**
     * checks if the hash table is empty.
     * @return true if the table is empty, false otherwise.
     */
    public boolean isEmpty() {
        for (LinkedList<Node> list : bucketArray) {
            if (list != null && !list.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    /**
     * counts the number of entries in the hash table.
     * @return the number of entries.
     */
    public int count() {
        int counter = 0;
        for (LinkedList<Node> list : bucketArray) {
            if (list != null) {
                counter += list.size();
            }
        }
        return counter;
    }

    /**
     * prints the contents of the hash table.
     */
    public void dump() {
        for (int i = 0; i < bucketArray.length; i++) {
            System.out.print(i + ": ");
            if (bucketArray[i] == null || bucketArray[i].isEmpty()) {
                System.out.println("null");
            } else {
                for (Node node : bucketArray[i]) {
                    System.out.print("[" + node.getKey() + ": " + node.getValue() + "] ");
                }
                System.out.println();
            }
        }
    }

    /**
     * resizes the hash table when it gets too full.
     */
    public void rehash() {
        LinkedList<Node>[] oldArray = bucketArray;
        bucketArray = new LinkedList[oldArray.length * 2];

        for (LinkedList<Node> bucket : oldArray) {
            if (bucket != null) {
                for (Node node : bucket) {
                    int newIndex = hashFunction(node.getKey());
                    if (bucketArray[newIndex] == null) {
                        bucketArray[newIndex] = new LinkedList<>();
                    }
                    bucketArray[newIndex].add(new Node(node.getKey(), node.getValue()));
                }
            }
        }
    }
}
