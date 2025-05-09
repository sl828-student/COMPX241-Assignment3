/**
 * This is the Main class where we test the StrHashTableCollisions class.
 * It shows how to use the hash table by adding, deleting, and getting stuff.
 * Also checks how collisions are handled in the table.
 */
public class Main {

    /**
     * The main method is where the program starts running.
     * We make a hash table, put some values in it, and then do some operations
     * like checking if stuff is in the table or printing the table.
     *
     * @param args Command-line arguments (we dont use them here).
     */
    public static void main(String[] args) {
        StrHashTableCollisions hashTable = new StrHashTableCollisions(4);

        hashTable.insert("one", "1");
        hashTable.insert("two", "2");
        hashTable.insert("three", "3");
        hashTable.insert("four", "4");

        hashTable.insert("five", "5");
        hashTable.insert("six", "6");
        hashTable.insert("seven", "7");

        hashTable.insert("eight", "8");
        hashTable.insert("nine", "9");
        hashTable.insert("ten", "10");
        hashTable.insert("eleven", "11");
        hashTable.insert("twelve", "12");

        hashTable.dump();

        System.out.println();
        System.out.println();

        System.out.println("no of elements in table: " + hashTable.count());

        System.out.println();
        System.out.println();

        System.out.println("contains eleven? " + hashTable.contains("eleven"));
        System.out.println();

        System.out.println("value for 'eight': " + hashTable.get("eight"));

        System.out.println("is empty? " + hashTable.isEmpty());
    }
}
