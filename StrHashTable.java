public class StrHashTable {
    private int size;
    private Node[] hashTable;
    private int numFullEntries;

    public StrHashTable(int size) {
        this.size = size;
        this.hashTable = new Node[size];
        this.numFullEntries = 0;
    }

    public void insert(String key, String value) {
        if ((double) numFullEntries / size >= 0.8) {
            rehash();
        }

        int index = hashFunction(key);
        Node existing = hashTable[index];

        // Only insert if the slot is empty
        if (existing == null) {
            hashTable[index] = new Node(key, value);
            numFullEntries++;
        }
    }

    public void delete(String key) {
        int index = hashFunction(key);
        Node node = hashTable[index];
        if (node != null && node.getKey().equals(key)) {
            hashTable[index] = null;
            numFullEntries--;
        }
    }

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

    public boolean contains(String key) {
        int index = hashFunction(key);
        Node node = hashTable[index];
        return node != null && node.getKey().equals(key);
    }

    public String get(String key) {
        int index = hashFunction(key);
        Node node = hashTable[index];
        return (node != null && node.getKey().equals(key)) ? node.getValue() : null;
    }

    public boolean isEmpty() {
        return numFullEntries == 0;
    }

    public int count() {
        return numFullEntries;
    }

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

    // Optional: Inner Node class can be used instead of separate file
    private static class Node {
        private final String key;
        private final String value;

        public Node(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return this.key;
        }

        public String getValue() {
            return this.value;
        }
    }
}
