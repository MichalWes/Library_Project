package util;

import model.library.DepartmentType;
import model.library.book.Book;
import model.library.book.BookType;
import model.library.book.Genre;

import java.sql.*;

public class BookPostgresDatabaseCrudPersister {
    private Connection c = null;

    public BookPostgresDatabaseCrudPersister() {

        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/library",
                            "postgres", "dupapupa");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        } finally {
            System.out.println("Opened database successfully");


        }
    }

    public void createBook(Book book) throws SQLException {
        PreparedStatement stmt = c.prepareStatement(
                "insert into books (title, author, bookType, bookDepartment, genre, description, price) values (?,?,?,?,?,?,?)",
                Statement.RETURN_GENERATED_KEYS);

        stmt.setString(1, book.getTitle());
        stmt.setString(2, book.getAuthor());
        stmt.setString(3, String.valueOf(book.getBookType()));
        stmt.setString(4, String.valueOf(book.getBookDepartment()));
        stmt.setString(5, String.valueOf(book.getGenre()));
        stmt.setString(6, book.getDescription());
        stmt.setInt(7, book.getValue());


        stmt.executeUpdate();

        ResultSet rs = stmt.getGeneratedKeys();

        if (rs.next()) {
            book.setID(rs.getInt(1));
        }

    }

}

