/**
 * this is the node class. it stores a key and a value.
 * used in the hash table to keep stuff together.
 */
public class Node {
    public String key;
    public String value;

    /**
     * makes a new node with a key and value
     * @param key the key for the node
     * @param value the value for the node
     */
    public Node(String key, String value) {
        this.key = key;
        this.value = value;
    }

    /**
     * gets the key from the node
     * @return the key in the node
     */
    public String getKey() {
        return this.key;
    }

    /**
     * gets the value from the node
     * @return the value in the node
     */
    public String getValue() {
        return this.value;
    }
}