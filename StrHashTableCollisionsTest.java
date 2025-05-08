import org.junit.jupiter.api.*;
import java.io.*;

public class StrHashTableCollisionsTest {
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }

    @Test
    @DisplayName("Insert multiple values with collisions and retrieve all")
    public void insertCollisionAndGetTest() {
        StrHashTableCollisions hashTable = new StrHashTableCollisions();

        hashTable.insert("A", "Apple");   // Assume A and K hash to the same bucket
        hashTable.insert("K", "Kiwi");
        hashTable.insert("U", "Ugli");    // Another colliding key

        Assertions.assertEquals("Apple", hashTable.get("A"));
        Assertions.assertEquals("Kiwi", hashTable.get("K"));
        Assertions.assertEquals("Ugli", hashTable.get("U"));
    }

    @Test
    @DisplayName("Overwriting a value with the same key")
    public void overwriteValueTest() {
        StrHashTableCollisions hashTable = new StrHashTableCollisions();

        hashTable.insert("dog", "bark");
        hashTable.insert("dog", "woof");

        Assertions.assertEquals("bark", hashTable.get("dog"), "Should not overwrite existing value");
    }

    @Test
    @DisplayName("Check delete works inside a collision chain")
    public void deleteMiddleOfChainTest() {
        StrHashTableCollisions hashTable = new StrHashTableCollisions();

        hashTable.insert("cat", "meow");
        hashTable.insert("bat", "squeak");
        hashTable.insert("hat", "top");

        hashTable.delete("bat");

        hashTable.dump();
        String output = outputStreamCaptor.toString().trim();

        Assertions.assertFalse(output.contains("bat"), "Deleted value should be removed from dump output");
        Assertions.assertTrue(output.contains("cat") && output.contains("hat"), "Other values in chain should remain");
    }

    @Test
    @DisplayName("Inserting different keys with same hash and verifying dump order")
    public void collisionInsertionOrderTest() {
        StrHashTableCollisions hashTable = new StrHashTableCollisions();

        hashTable.insert("11", "eleven"); // Hash: bucket 1
        hashTable.insert("21", "twentyone"); // Same bucket

        hashTable.dump();
        String output = outputStreamCaptor.toString().trim();

        Assertions.assertTrue(output.contains("11, eleven"), "Should contain first inserted value");
        Assertions.assertTrue(output.contains("21, twentyone"), "Should contain second inserted value");
    }

    @Test
    @DisplayName("Get non-existent key")
    public void getMissingKeyTest() {
        StrHashTableCollisions hashTable = new StrHashTableCollisions();
        Assertions.assertNull(hashTable.get("ghost"));
    }

    @Test
    @DisplayName("Delete non-existent key and capture message")
    public void deleteMissingKeyTest() {
        StrHashTableCollisions hashTable = new StrHashTableCollisions();
        hashTable.delete("missing");

        String actual = outputStreamCaptor.toString().trim();
        String expected = "key does not exist in table";

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Check table is not empty after multiple inserts")
    public void isEmptyAfterInsertTest() {
        StrHashTableCollisions hashTable = new StrHashTableCollisions();
        hashTable.insert("x", "value");

        Assertions.assertFalse(hashTable.isEmpty());
    }

    @Test
    @DisplayName("Clear table by deleting all keys and check empty")
    public void clearTableTest() {
        StrHashTableCollisions hashTable = new StrHashTableCollisions();
        hashTable.insert("a", "alpha");
        hashTable.insert("b", "beta");

        hashTable.delete("a");
        hashTable.delete("b");

        Assertions.assertTrue(hashTable.isEmpty());
    }

    @Test
    @DisplayName("Insert null key should not crash or insert")
    public void nullKeyInsertTest() {
        StrHashTableCollisions hashTable = new StrHashTableCollisions();
        hashTable.insert(null, "void");

        hashTable.dump();
        String output = outputStreamCaptor.toString().trim();
        Assertions.assertFalse(output.contains("void"), "Null key should not be inserted into table");
    }

    @Test
    @DisplayName("Delete same key twice should not break")
    public void doubleDeleteTest() {
        StrHashTableCollisions hashTable = new StrHashTableCollisions();

        hashTable.insert("bruh", "moment");
        hashTable.delete("bruh");

        // First delete
        String firstOutput = outputStreamCaptor.toString().trim();
        Assertions.assertTrue(firstOutput.contains("bruh"), "First delete should affect output");

        outputStreamCaptor.reset();
        hashTable.delete("bruh"); // Second delete, key already gone

        String secondOutput = outputStreamCaptor.toString().trim();
        Assertions.assertTrue(secondOutput.contains("key does not exist in table"), "Second delete should return error message");
    }

    @Test
    @DisplayName("Re-insert after delete")
    public void reinsertionTest() {
        StrHashTableCollisions hashTable = new StrHashTableCollisions();

        hashTable.insert("sus", "impostor");
        hashTable.delete("sus");

        hashTable.insert("sus", "vented");
        Assertions.assertEquals("vented", hashTable.get("sus"), "Should return newly inserted value after deletion");
    }

    @Test
    @DisplayName("Dump on empty table should return no data")
    public void dumpEmptyTableTest() {
        StrHashTableCollisions hashTable = new StrHashTableCollisions();
        hashTable.dump();

        String output = outputStreamCaptor.toString().trim();
        Assertions.assertTrue(output.isEmpty() || output.equals(""), "Empty table should not produce any dump output");
    }

    @Test
    @DisplayName("Insert same key multiple times should not duplicate in dump")
    public void duplicateInsertTest() {
        StrHashTableCollisions hashTable = new StrHashTableCollisions();

        hashTable.insert("yeet", "one");
        hashTable.insert("yeet", "two"); // Should not overwrite

        hashTable.dump();
        String output = outputStreamCaptor.toString().trim();
        int count = output.split("yeet").length - 1;

        Assertions.assertEquals(1, count, "Duplicate inserts with same key should not appear more than once in dump");
    }
}
