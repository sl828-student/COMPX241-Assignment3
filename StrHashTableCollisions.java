import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class StrHashTableCollisions {
    private LinkedList<Node>[] bucketArray;

    public StrHashTableCollisions() {
        this(10);
    }

    public StrHashTableCollisions(int size) {
        bucketArray = new LinkedList[size];
    }

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
        System.out.println("Key does not exist in table");
    }

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

    public String get(String k) {
        int index = hashFunction(k);
        if (bucketArray[index] != null) {
            for (Node node : bucketArray[index]) {
                if (node.getKey().equals(k)) {
                    return node.getValue();
                }
            }
        }
        System.out.println("Key does not exist in table");
        return null;
    }

    public boolean isEmpty() {
        for (LinkedList<Node> list : bucketArray) {
            if (list != null && !list.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public int count() {
        int counter = 0;
        for (LinkedList<Node> list : bucketArray) {
            if (list != null) {
                counter += list.size();
            }
        }
        return counter;
    }

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
