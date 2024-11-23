package models;


public class BorrowedBook {
    private Book book;
    private User user;

    // Constructor
    public BorrowedBook(Book book, User user) {
        this.book = book;
        this.user = user;
    }

    // Getters and Setters
    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Copy of " +book.getTitle() + "Is taken by " + user.getName();
    }
}
