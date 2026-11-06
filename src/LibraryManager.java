import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Scanner;

public class LibraryManager {
    private Connection con;

    public LibraryManager(Connection con) {
        this.con = con;
    }

    private String readNonEmptyString(Scanner s, String message) {

        System.out.print(message);
        String input = s.nextLine();
        input = input.trim();

        while (input.isEmpty()) {

            System.out.println("Invalid text!");
            System.out.print("Please type again: ");

            input = s.nextLine();
            input = input.trim();
        }

        return input;
    }

    private int readPositiveInt(Scanner s, String message) {

        int number;

        while (true) {

            System.out.print(message);

            if (s.hasNextInt()) {

                number = s.nextInt();

                if (number > 0) {
                    return number;
                } else {
                    System.out.println("Please enter a positive number!");
                }

            } else {

                System.out.println("Invalid input! Please enter a number.");
                s.next();
            }
        }
    }

    public int readMenuOption(Scanner s) {

        int number;

        while (true) {

            System.out.print("Select an option from 1-7: ");

            if (s.hasNextInt()) {

                number = s.nextInt();

                if (number >= 1 && number <= 7) {
                    s.nextLine();
                    return number;
                } else {
                    System.out.println("Please enter a number from 1-7.");
                }

            } else {
                System.out.println("Invalid input! Please enter a valid number.");
                s.next();
            }
        }
    }

    public void addBook(Scanner s) throws SQLException {
        String title = readNonEmptyString(s, "Enter the Book name: ");

        String author = readNonEmptyString(s, "Enter the Author name: ");

        String query = "INSERT INTO books (title, author) VALUES (?, ?)";

        try (PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, title);
            ps.setString(2, author);

            ps.executeUpdate();
        }

        System.out.println("Book added successfully!");
        System.out.println("");
    }

    public ArrayList<Book> getAllBooks() throws SQLException {

        ArrayList<Book> books = new ArrayList<>();

        String query = "SELECT * FROM Books";

        try (PreparedStatement ps = con.prepareStatement(query);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String author = rs.getString("author");
                boolean status = rs.getBoolean("status");

                Book b = new Book(id, title, author, status);

                books.add(b);
            }
        }

        return books;
    }

    public void displayBooks() throws SQLException {

        ArrayList<Book> books = getAllBooks();

        System.out.println("Books in the Library");
        System.out.println("");

        if (books.isEmpty()) {
            System.out.println("No Books found in the Library!");
        } else {

            for (Book b : books) {

                System.out.println(b);

                if (b.isStatus()) {
                    System.out.println("Available");
                } else {
                    System.out.println("Borrowed");
                }

                System.out.println("");
            }
        }
    }

    public void borrowBook(Scanner s) throws SQLException {

        int bookID = readPositiveInt(
                s, "Enter the Book ID that you want to Borrow: ");

        String query = "UPDATE books SET status = FALSE WHERE id = ? AND status = TRUE";

        try (PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, bookID);

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Book borrowed successfully!");
            } else {
                System.out.println("Book doesn't exist or is already borrowed!");
            }

            System.out.println("");
        }
    }

    public void returnBook(Scanner s) throws SQLException {

        int bookID = readPositiveInt(
                s, "Enter the Book ID that you want to Return: ");

        String query = "UPDATE books SET status = TRUE WHERE id = ? AND status = FALSE";

        try (PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, bookID);

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Book returned successfully!");
            } else {
                System.out.println("Book doesn't exist or is already available!");
            }

            System.out.println("");
        }
    }

    public void updateBook(Scanner s) throws SQLException {

        int bookID = readPositiveInt(
                s, "Enter the Book ID that you want to Update: ");

        s.nextLine();

        String title = readNonEmptyString(s, "Enter the new Book Title: ");

        String author = readNonEmptyString(s, "Enter the new Author Name: ");

        String query = "UPDATE books SET title = ?, author = ? WHERE id = ?";

        try (PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, title);
            ps.setString(2, author);
            ps.setInt(3, bookID);

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Book Title and Author updated successfully!");
            } else {
                System.out.println("Book Title and Author updation unsuccessful!");
            }

            System.out.println("");

        }
    }

    public void deleteBook(Scanner s) throws SQLException {

        int bookID = readPositiveInt(
                s, "Enter the Book ID that you want to Delete: ");

        s.nextLine();

        ArrayList<Book> books = getAllBooks();
        boolean found = false;

        for (Book b : books) {
            if (b.getId() == bookID) {
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Book doesn't exist!");
        } else {
            System.out.print("Are you sure you want to delete this book? (Y/N): ");
            String choice = s.nextLine();

            while (!choice.equalsIgnoreCase("Y") && !choice.equalsIgnoreCase("N")) {

                System.out.println("Invalid choice!");

                System.out.print("Enter either Y or N: ");
                choice = s.nextLine();
            }

            if (choice.equalsIgnoreCase("Y")) {

                String query = "DELETE FROM books WHERE id = ?";

                try (PreparedStatement ps = con.prepareStatement(query)) {
                    ps.setInt(1, bookID);

                    int rowsAffected = ps.executeUpdate();

                    if (rowsAffected > 0) {
                        System.out.println("Book deleted successfully!");
                    } else {
                        System.out.println("Book deletion unsuccessful!");
                    }

                    System.out.println("");
                }

            } else {

                System.out.println("Book deletion cancelled!");

            }
        }
    }
}
