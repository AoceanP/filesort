import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * FileSorterTest validates the core logic of FileSorter:
 * - Moves files with "bcit", "comp", or exactly 4-digit numbers
 * - Handles edge cases like empty or non-existent directories
 * - Triggers and catches IOExceptions safely
 *
 * @author Aleksandar
 * @version 1.0
 */
public class FileSorterTest {

    public static void main(String[] args) {
        System.out.println("=== Running FileSorter Tests ===");

        // ✅ Test 1: Valid source folder with a matching file name
        File source1 = new File("test_source1");
        File destination1 = new File("test_destination1");
        setupFolder(source1);
        setupFolder(destination1);
        createTestFile(source1, "bcit_schedule.txt");

        FileSorter.sortFiles(source1, destination1);
        assertFileMoved("bcit_schedule.txt", source1, destination1);

        // ✅ Test 2: Empty folder (Edge case)
        File source2 = new File("test_source2_empty");
        setupFolder(source2);
        FileSorter.sortFiles(source2, destination1);

        // ✅ Test 3: Non-existent folder (Edge case)
        File source3 = new File("folder_does_not_exist_123");
        FileSorter.sortFiles(source3, destination1);

        // ✅ Test 4: Simulated I/O exception (read-only file)
        File source4 = new File("test_source4_io");
        setupFolder(source4);
        File errorFile = createTestFile(source4, "comp_locked.txt");
        if (errorFile.exists()) {
            errorFile.setWritable(false); // simulate permission error
        }
        FileSorter.sortFiles(source4, destination1);
        if (errorFile.exists()) {
            errorFile.setWritable(true); // restore so it can be cleaned up
        }

        System.out.println("=== Tests Complete ===");
    }

    /**
     * Helper to create folders if they don’t exist.
     */
    private static void setupFolder(File folder) {
        if (!folder.exists()) {
            boolean created = folder.mkdirs();
            if (!created) {
                System.out.println("Could not create folder: " + folder.getAbsolutePath());
            }
        }
    }

    /**
     * Helper to create a file with test content in a folder.
     */
    private static File createTestFile(File folder, String filename) {
        File file = new File(folder, filename);
        try (FileWriter writer = new FileWriter(file)) {
            writer.write("Sample content for " + filename);
            System.out.println("Created test file: " + file.getName());
        } catch (IOException e) {
            System.out.println("❌ Failed to create test file: " + filename);
            e.printStackTrace();
        }
        return file;
    }

    /**
     * Verifies whether a file was moved from one directory to another.
     */
    private static void assertFileMoved(String filename, File from, File to) {
        File oldFile = new File(from, filename);
        File newFile = new File(to, filename);

        if (!oldFile.exists() && newFile.exists()) {
            System.out.println("✅ Passed: " + filename + " was moved successfully.");
        } else {
            System.out.println("❌ Failed: " + filename + " was NOT moved.");
        }
    }
}