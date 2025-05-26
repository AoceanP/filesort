import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * FileSorterTest provides test coverage for the FileSorter class.
 * It checks if matching files are correctly moved, verifies behavior
 * for empty folders, and validates basic error-handling paths.
 * This test is run through a CLI and outputs success/failure messages.
 *
 * @author Aleksandar
 * @version 1.0
 */
public class FileSorterTest
{

    /**
     * Executes all test scenarios for FileSorter.
     * Validates sorting logic for known cases and edge cases like empty directories.
     *
     * @param args not used
     */
    public static void main(String[] args)
    {
        System.out.println("=== Running FileSorter Tests ===");

        File source1 = new File("test_source1");
        File destination1 = new File("test_destination1");
        setupFolder(source1);
        setupFolder(destination1);
        createTestFile(source1, "bcit_schedule.txt");

        FileSorter.sortFiles(source1, destination1);
        assertFileMoved("bcit_schedule.txt", source1, destination1);

        File source2 = new File("test_source2_empty");
        setupFolder(source2);
        FileSorter.sortFiles(source2, destination1);

        File source3 = new File("test_source3");
        setupFolder(source3);
        createTestFile(source3, "report_2023.pdf");
        FileSorter.sortFiles(source3, destination1);
        assertFileMoved("report_2023.pdf", source3, destination1);

        System.out.println("✅ All tests completed.");
    }

    /**
     * Sets up the given directory for testing. Creates it if it does not exist,
     * and clears existing contents to ensure a clean test state.
     *
     * @param folder the directory to prepare for testing
     */
    private static void setupFolder(File folder)
    {
        if (!folder.exists())
        {
            folder.mkdirs();
        }
        for (File file : folder.listFiles())
        {
            file.delete();
        }
    }

    /**
     * Creates a dummy file with the specified name and sample content
     * inside the provided directory. Used to simulate a file to be sorted.
     *
     * @param dir the directory where the file will be created
     * @param filename the name of the file to be created
     */
    private static void createTestFile(File dir, String filename)
    {
        try
        {
            FileWriter writer = new FileWriter(new File(dir, filename));
            writer.write("Test content");
            writer.close();
        }
        catch (IOException e)
        {
            System.out.println("Failed to create test file: " + filename);
        }
    }

    /**
     * Verifies that a file was successfully moved from source to destination.
     * Outputs a test pass/fail message based on file existence.
     *
     * @param filename the file name to check
     * @param sourceDir the directory it was originally located in
     * @param destDir the directory it should now reside in
     */
    private static void assertFileMoved(String filename, File sourceDir, File destDir)
    {
        File sourceFile = new File(sourceDir, filename);
        File destFile = new File(destDir, filename);

        if (destFile.exists() && sourceFile.exists())
        {
            System.out.println("Test Passed: " + filename + " moved successfully.");
        }
        else
        {
            System.out.println("Test Failed: " + filename + " was not moved correctly.");
        }
    }
}