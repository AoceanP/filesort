import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;

/**
 * FileSorter is a utility class that organizes files from a selected folder.
 * It moves files containing specific keywords (e.g., "bcit", "comp") or 4-digit numbers
 * to a predefined destination directory.
 *
 * This class is intended to help reduce clutter and enhance file management.
 *
 * @author Aleksandar Panich
 * @version 1.0
 */
public class FileSorter
{

    /**
     * Launches the file organizer via a simple console interface.
     * Allows the user to select a common folder (Desktop, Downloads, Documents),
     * or enter a custom path. Invokes the sortFiles method based on the input.
     *
     * @param args not used.
     */
    public static void main(final String[] args)
    {
        Scanner scanner = new Scanner(System.in);

        final String userHome = System.getProperty("user.home");
        final String destinationPath = userHome + "\\Desktop\\BCIT_AP";

        System.out.println("=== File Organizer ===");
        System.out.println("Choose a folder to organize:");
        System.out.println("1. Desktop");
        System.out.println("2. Downloads");
        System.out.println("3. Documents");
        System.out.println("4. Enter custom path");

        String selectedPath = null;
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice)
        {
            case 1:
                selectedPath = userHome + "\\Desktop";
                break;
            case 2:
                selectedPath = userHome + "\\Downloads";
                break;
            case 3:
                selectedPath = userHome + "\\Documents";
                break;
            case 4:
                System.out.print("Enter full path: ");
                selectedPath = scanner.nextLine();
                break;
            default:
                System.out.println("Invalid choice. Exiting...");
                return;
        }

        File sourceFolder = new File(selectedPath);
        File destinationFolder = new File(destinationPath);
        sortFiles(sourceFolder, destinationFolder);
    }

    /**
     * Sorts files from the source folder to the destination folder.
     * Only moves files that contain "bcit", "comp", or a 4-digit year (e.g., "2023").
     *
     * If the destination folder does not exist, it is created.
     * Files are copied with overwrite enabled and errors are printed if they occur.
     *
     * @param sourceFolder the folder to scan for matching files
     * @param destinationFolder the folder where matching files will be copied to
     */
    public static void sortFiles(File sourceFolder, File destinationFolder)
    {
        if (!destinationFolder.exists())
        {
            destinationFolder.mkdirs();
        }

        File[] files = sourceFolder.listFiles();
        if (files == null || files.length == 0)
        {
            System.out.println("No files to organize.");
            return;
        }

        for (File file : files)
        {
            String fileName = file.getName().toLowerCase();

            boolean containsBCIT;
            boolean containsCOMP;
            boolean isFourDigit;

            try
            {
                containsBCIT = fileName.contains("bcit");
                containsCOMP = fileName.contains("comp");

                isFourDigit = false;
                String[] parts = fileName.split("[^0-9]+");
                for (String part : parts)
                {
                    if (part.length() == 4)
                    {
                        try
                        {
                            Integer.parseInt(part);
                            isFourDigit = true;
                            break;
                        }
                        catch (NumberFormatException e)
                        {
                            // Skip invalid number
                        }
                    }
                }
            }
            catch (IllegalArgumentException e)
            {
                System.out.println("Invalid filename encountered: " + fileName);
                containsBCIT = false;
                containsCOMP = false;
                isFourDigit = false;
            }

            if (containsBCIT || containsCOMP || isFourDigit)
            {
                File destFile = new File(destinationFolder, file.getName());
                try
                {
                    Files.copy(file.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    System.out.println("Moved: " + file.getName());
                }
                catch (IOException e)
                {
                    System.out.println("Failed to move file: " + file.getName());
                    e.printStackTrace();
                }
            }
        }
    }
}