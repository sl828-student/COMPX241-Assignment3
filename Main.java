public class Main {
    public static void main(String[] args) {
        // Create a small table to test collision handling
        StrHashTableCollisions hashTable = new StrHashTableCollisions(4);

        // Insert some values (some may collide)
        hashTable.insert("one", "1");
        hashTable.insert("two", "2");
        hashTable.insert("three", "3");
        hashTable.insert("four", "4");

        // This would have deleted key "2" if uncommented
        // hashTable.delete("2");

        hashTable.insert("five", "5");
        hashTable.insert("six", "6");
        hashTable.insert("seven", "7");

        hashTable.insert("eight", "8");
        hashTable.insert("nine", "9");
        hashTable.insert("ten", "10");
        hashTable.insert("eleven", "11");
        hashTable.insert("twelve", "12");

        // Show what's inside the table (with collisions shown)
        hashTable.dump();

        System.out.println();
        System.out.println();

        // Show count
        System.out.println("no of elements in table: " + hashTable.count());

        System.out.println();
        System.out.println();

        // Check contains
        System.out.println("contains eleven? " + hashTable.contains("eleven"));
        System.out.println();

        // Try get
        System.out.println("value for 'eight': " + hashTable.get("eight"));

        // Check empty
        System.out.println("is empty? " + hashTable.isEmpty());
    }
}
