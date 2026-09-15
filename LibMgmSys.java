import java.util.Scanner;

class Book {
    public int id;
    public String title;
    public String author;
    public boolean status;

    Book(int ID, String Title, String Author, boolean sts) {
        this.id = ID;
        this.title = Title;
        this.author = Author;
        this.status = sts;
    }
}

class LibraryManager {
    Book[] books = new Book[1000];
    int position = 0;
    int id = 1;

    public void addBook(Scanner s) {
        System.out.print("Enter the Book name: ");
        String title = s.nextLine();

        System.out.print("Enter the Author name: ");
        String author = s.nextLine();

        System.out.println("");

        books[position] = new Book(id, title, author, true);
        position++;
        id++;
    }

    public void displayBooks() {
        System.out.println("Books in the Library");

        System.out.println("");

        for (int i = 0; i < position; i++) {
            System.out.println("Book ID: " + books[i].id);
            System.out.println("Book Title: " + books[i].title);
            System.out.println("Book Author: " + books[i].author);
            if (books[i].status == true) {
                System.out.println("Available");
            }

            if (books[i].status == false) {
                System.out.println("Borrowed");
            }

            System.out.println("");
        }
    }

    public void borrowBook(Scanner s) {
        boolean found = false;

        System.out.print("Enter the Book ID that you want to Borrow: ");
        int bookID = s.nextInt();

        for (int i = 0; i < position; i++) {
            if (books[i].id == bookID) {
                found = true;

                if (books[i].status == true) {
                    System.out.println("Book borrowed successfully!");
                    books[i].status = false;
                    System.out.println("");
                }

                else if (books[i].status == false) {
                    System.out.println("This Book is already borrowed!");
                    System.out.println("");
                }

            }
        }

        if (found == false) {
            System.out.println("The Book with ID: " + bookID + " doesn't exist!");
            System.out.println("");
        }
    }

    public void returnBook(Scanner s) {
        boolean found = false;

        System.out.print("Enter the Book ID that you want to Return: ");
        int bookID = s.nextInt();

        for (int i = 0; i < position; i++) {
            if (books[i].id == bookID) {
                found = true;

                if (books[i].status == false) {
                    System.out.println("Book returned successfully!");
                    books[i].status = true;
                    System.out.println("");
                }

                else if (books[i].status == true) {
                    System.out.println("This Book is already available in the Library!");
                    System.out.println("");
                }
            }
        }

        if (found == false) {
            System.out.println("The Book with ID: " + bookID + " doesn't exist!");
            System.out.println("");
        }
    }
}

public class LibMgmSys {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        LibraryManager l = new LibraryManager();

        System.out.println("*** Welcome to The Library ***");
        System.out.println("");

        int option = 1;

        while (option != 5) {
            System.out.print("Select the  option from 1-5: ");
            option = s.nextInt();

            s.nextLine();

            System.out.println("");

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
                    break;

                default:
                    System.out.println("Invalid Option selected!!");
                    break;
            }
        }
    }
}
