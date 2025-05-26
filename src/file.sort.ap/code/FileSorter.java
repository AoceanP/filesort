import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;

/**
 * My files are unorganized, I want a better way to manage them. So I created a file sorter
 * to assist a solution. The class will scan a folder and list all the files in it, then sort
 * the files into the respective folders.
 *
 * @author Aleksandar Panich
 * @version 1.0
 * */
public class FileSorter {
    public static void main(final String[] args) {

        {
            Scanner scanner = new Scanner(System.in);

            final String userHome = System.getProperty("user.home");
            final String destinationPath = userHome + "\\Desktop\\BCIT_AP";

            System.out.println("=== File Organizer ===");
            System.out.println("Choose a folder to organize:");
            System.out.println("1. Desktop");
            System.out.println("2. Downloads");
            System.out.println("3. Documents");
            System.out.println("4. Enter a custom path");
            System.out.print("Enter your choice (1-4): ");

            final int choice = scanner.nextInt();
            scanner.nextLine();

            final String directorypath;

            switch (choice) {
                case 1:
                    directorypath = userHome + "\\Desktop";
                    break;
                case 2:
                    directorypath = userHome + "\\Downloads";
                    break;
                case 3:
                    directorypath = userHome + "\\Documents";
                    break;
                case 4:
                    System.out.print("Enter full source path: ");
                    directorypath = scanner.nextLine();
                    break;
                default:
                    System.out.println("Invalid choice. Exiting.");
                    scanner.close();
                    return;
            }

            File sourcefolder = new File(directorypath);
            File[] files = sourcefolder.listFiles();

            if (files == null || files.length == 0) {
                System.out.println("No files found in: " + directorypath);
                return;
            }
            for (File file : files) {
                if (file.isFile()) {
                    String filename = file.getName();
                    String lowerName = filename.toLowerCase();

                    boolean containsBCIT = lowerName.contains("bcit");
                    boolean containsCOMP = lowerName.contains("comp");
                    boolean contains4Digit = false;

                    for (int i = 0; i < filename.length() - 3; i++) {
                        String segment = filename.substring(i, i + 4);

                        if (segment.chars().allMatch(Character::isDigit)) {
                            boolean leftValid = (i == 0 || !Character.isDigit(filename.charAt(i - 1)));
                            boolean rightValid = (i + 4 == filename.length() || !Character.isDigit(filename.charAt(i + 4)));

                            if (leftValid && rightValid) {
                                contains4Digit = true;
                                break;
                            }
                        }
                    }
                    if (containsBCIT || containsCOMP || contains4Digit) {
                        File destDir = new File(destinationPath);
                        if (!destDir.exists()) {
                            destDir.mkdirs();
                        }

                        File destFile = new File(destDir, filename);
                        try {
                            Files.move(file.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                            System.out.println("Moved: " + filename);
                        } catch (IOException e) {
                            System.out.println("Failed to move: " + filename);
                            e.printStackTrace();
                        }
                    }
                }
            }
            scanner.close();
        }
    }
    public static void sortFiles(File sourceFolder, File destinationFolder){};
}