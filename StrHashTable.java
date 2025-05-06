import java.util.Arrays;

public class StrHashTable{
    int size;
    Node[] hashTable;

    int numFullEntries, numTotalCollisions, numRehashes;

    public StrHashTable(int size){
        hashTable = new Node[size];
        this.size = size;
        this.numFullEntries = 0;
        this.numTotalCollisions = 0;
        this.numRehashes = 0;
    }

    public void insert(String k, String v){

        Node newNode = new Node(key, value);

        if((double)numFullEntries / size >= 0.8){
            rehash();
            numRehashes++;
        }
        int i = 0;
        while(hashTable[(getHash(key) + i) % size] != null){
            i++;
            numTotalCollisions++;
            newNode.collisions++;
        }
        hashTable[(getHash(key) + i) % size] = newNode;
        numFullEntries++;
    }

    public void delete(String k){
        int index = hashFunction(k);
        if (hashTable[index] != null && hashTable[index].key.equals(k)){
            hashTable[index] = null;
            numFullEntries--;
        }

    }

    private int hashFunction(String k){
        int sum = 0;
        int group =0;
        for(int i =0; i < k.length(); i++){
            group = (group << 8) + k.charAt(i);
            if ((i + 1) % 4 == 0){
                sum += group;
                group = 0;
            }
        }
        sum += group;
        return Math.abs(sum) % size;
    }

    private void rehash(){
        Node[] temp = hashTable;
        size = size * 2;
        hashTable = new Node[size];

        for (int i = 0; i < temp.length; i++){
            Node t = temp[i];
            insert(t.key, t.value);
            numFullEntries--;
        }
    }

    private bool contains(String k){
        int index = hashFunction(k);
        return hashTable[index] != null && hashTable[index].key.equals(k);
    }

    private String get(String k){
        int index = hashFunction(k);
        if(hasTable[index] != null && hashTable[index].key.equals(k)){
            return hashTable[index].value;
        }
        return null;

    }

    private bool isEmpty(){
        return numFullEntries == 0;
    }

    public int count(){
        return numFullEntries; 
    }

    public void dump(){
        for (int i = 0; i < size; i++) {
            if (hashTable[i] != null) {
                System.out.println(i + ": " + hashTable[i].key + ", " + hashTable[i].value);
            } else {
                System.out.println(i + ": ");
            }
        }
    }

    class Node{
        public String key;
        public String Value;
        public int collisions;

        public Node(String key, String value){
            this.key = key;
            this.value = value;
        }
    }
}