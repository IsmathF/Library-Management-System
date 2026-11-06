public class Book {
    private int id;
    private String title;
    private String author;
    private boolean status;

    public Book(int id, String title, String author, boolean status) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.status = status;
    }

    @Override
    public String toString() {
        return "Book ID: " + id +
                "\nBook Title: " + title +
                "\nBook Author: " + author;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isStatus() {
        return status;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}