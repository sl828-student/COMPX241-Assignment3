/**
 * this is a simple hash table class that stores key-value pairs.
 * it handles collisions and resizes itself when it gets too full.
 * it's not super fancy but it works for basic stuff.
 */
public class StrHashTable {
    private int size;
    private Node[] hashTable;
    private int numFullEntries;

    /**
     * makes a new hash table with a given size.
     * @param size the initial size of the hash table.
     */
    public StrHashTable(int size) {
        this.size = size;
        this.hashTable = new Node[size];
        this.numFullEntries = 0;
    }

    /**
     * inserts a key-value pair into the hash table.
     * if the table is too full, it will resize itself.
     * @param key the key to insert.
     * @param value the value to insert.
     */
    public void insert(String key, String value) {
        if ((double) numFullEntries / size >= 0.8) {
            rehash();
        }

        int index = hashFunction(key);
        Node existing = hashTable[index];

        // only insert if the slot is empty
        if (existing == null) {
            hashTable[index] = new Node(key, value);
            numFullEntries++;
        }
    }

    /**
     * deletes a key-value pair from the hash table.
     * @param key the key to delete.
     */
    public void delete(String key) {
        int index = hashFunction(key);
        Node node = hashTable[index];
        if (node != null && node.getKey().equals(key)) {
            hashTable[index] = null;
            numFullEntries--;
        }
    }

    /**
     * calculates the hash for a given key.
     * @param k the key to hash.
     * @return the hash index.
     */
    public int hashFunction(String k) {
        int sum = 0;
        int group = 0;
        for (int i = 0; i < k.length(); i++) {
            group = (group << 8) + k.charAt(i);
            if ((i + 1) % 4 == 0) {
                sum += group;
                group = 0;
            }
        }
        sum += group;
        return Math.abs(sum) % size;
    }

    /**
     * resizes the hash table when it gets too full.
     */
    private void rehash() {
        Node[] oldTable = hashTable;
        size = size * 2;
        hashTable = new Node[size];
        numFullEntries = 0;

        for (Node node : oldTable) {
            if (node != null) {
                insert(node.getKey(), node.getValue());
            }
        }
    }

    /**
     * checks if the hash table contains a key.
     * @param key the key to check.
     * @return true if the key is in the table, false otherwise.
     */
    public boolean contains(String key) {
        int index = hashFunction(key);
        Node node = hashTable[index];
        return node != null && node.getKey().equals(key);
    }

    /**
     * gets the value for a given key.
     * @param key the key to get the value for.
     * @return the value for the key, or null if not found.
     */
    public String get(String key) {
        int index = hashFunction(key);
        Node node = hashTable[index];
        return (node != null && node.getKey().equals(key)) ? node.getValue() : null;
    }

    /**
     * checks if the hash table is empty.
     * @return true if the table is empty, false otherwise.
     */
    public boolean isEmpty() {
        return numFullEntries == 0;
    }

    /**
     * counts the number of entries in the hash table.
     * @return the number of entries.
     */
    public int count() {
        return numFullEntries;
    }

    /**
     * prints the contents of the hash table.
     */
    public void dump() {
        for (int i = 0; i < size; i++) {
            Node node = hashTable[i];
            if (node != null) {
                System.out.println(i + ": " + node.getKey() + ", " + node.getValue());
            } else {
                System.out.println(i + ": ");
            }
        }
    }

    /**
     * this is the node class used in the hash table.
     * it stores a key and a value.
     */
    private static class Node {
        private final String key;
        private final String value;

        /**
         * makes a new node with a key and value.
         * @param key the key for the node.
         * @param value the value for the node.
         */
        public Node(String key, String value) {
            this.key = key;
            this.value = value;
        }

        /**
         * gets the key from the node.
         * @return the key in the node.
         */
        public String getKey() {
            return this.key;
        }

        /**
         * gets the value from the node.
         * @return the value in the node.
         */
        public String getValue() {
            return this.value;
        }
    }
}
