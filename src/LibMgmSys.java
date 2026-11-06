import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class LibMgmSys {
    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);

        Properties properties = new Properties();

        try (FileInputStream fis = new FileInputStream("db.properties")) {
            properties.load(fis);
        } catch (IOException e) {
            System.out.println("Error loading database configuration: " + e.getMessage());
            return;
        }

        String url = properties.getProperty("db.url");
        String username = properties.getProperty("db.username");
        String password = properties.getProperty("db.password");

        try (Connection con = DriverManager.getConnection(url, username, password)) {

            LibraryManager l = new LibraryManager(con);
            System.out.println("*** Welcome to The Library ***");
            System.out.println("");

            int option = 1;

            while (option != 7) {
                System.out.println("1: Add Books in the Library");
                System.out.println("2: Books available in the Library");
                System.out.println("3: Borrow a Book from the Library");
                System.out.println("4: Return Books to the Library");
                System.out.println("5: Update Book Title and Author");
                System.out.println("6: Delete a Book from the Library");
                System.out.println("7: Close");
                System.out.println("");

                option = l.readMenuOption(s);

                System.out.println("");

                try {
                    switch (option) {
                        case 1:
                            l.addBook(s);
                            break;

                        case 2:
                            l.displayBooks();
                            break;

                        case 3:
                            l.borrowBook(s);
                            break;

                        case 4:
                            l.returnBook(s);
                            break;

                        case 5:
                            l.updateBook(s);
                            break;

                        case 6:
                            l.deleteBook(s);
                            break;

                        case 7:
                            System.out.println("Thank you for visiting the Library!");
                            break;

                        default:
                            System.out.println("Invalid Option selected!!");
                            break;
                    }
                } catch (SQLException e) {
                    System.out.println("Database error: " + e.getMessage());
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        s.close();
    }
}
