package model.library.book;

import model.library.DepartmentType;

import java.io.Serializable;
import java.util.UUID;

public class Book implements Serializable {
    private static final long serialVersionUID = 4961785586454307645L;
    private final String title;
    private final String author;
    private final BookType bookType;
    private final DepartmentType bookDepartment;
    private final Genre genre;
    private final String description;
    private final int value;
    private DepartmentType borrowedFrom;
    private int daysBorrowed;
    private int ID;


    public Book(String title, String author, BookType bookType, DepartmentType bookDepartment, Genre genre, int value) {
        this.title = title;
        this.author = author;
        this.bookType = bookType;
        this.bookDepartment = bookDepartment;
        this.genre = genre;
        this.value = value;
        this.description = UUID.randomUUID().toString();
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public BookType getBookType() {
        return bookType;
    }

    public DepartmentType getBookDepartment() {
        return bookDepartment;
    }

    public Genre getGenre() {
        return genre;
    }

    public int getValue() {
        return value;
    }

    public DepartmentType getBorrowedFrom() {
        return borrowedFrom;
    }

    public void setBorrowedFrom(DepartmentType borrowedFrom) {
        this.borrowedFrom = borrowedFrom;
    }

    public int getDaysBorrowed() {
        return daysBorrowed;
    }

    public void setDaysBorrowed(int daysBorrowed) {
        this.daysBorrowed = daysBorrowed;
    }

    public String getDescription() {
        return description;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    @Override
    public String toString() {
        return "Book [" + (title != null ? "title=" + title + ", " : "")
                + (author != null ? "author=" + author + ", " : "")
                + (bookType != null ? "bookType=" + bookType + ", " : "")
                + (bookDepartment != null ? "bookDepartment=" + bookDepartment + ", " : "")
                + (genre != null ? "genre=" + genre + ", " : "") + "value=" + value + "]";
    }

}
