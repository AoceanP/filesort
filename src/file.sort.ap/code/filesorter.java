import java.io.File;
import java.util.Scanner;

/**
 * My files are unorganized, I want a better way to manage them. So I created a file sorter
 * to assist a solution. The class will scan a folder and list all the files in it, then sort
 * the files into the respective folders.
 *
 * @author Aleksandar Panich
 * @version 1.0
 * */
public class filesorter
{
    public static void main(final String[] args)
    {
        final Scanner scanner = new Scanner();

        final String userfiles = System.getProperty("user.files");

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

        switch (choice)
        {
            case 1:
                directorypath = userfiles + "//Desktop";
                break;
            case 2:
        }






    }
}