package libraryInterface.admin;

import model.library.book.Book;
import util.BookMySqlDatabaseCrudPersister;
import util.BookPostgresDatabaseCrudPersister;

import java.sql.SQLException;
import java.util.List;

public class DatabaseOperations {

    public void writePostgreSql(List<Book> books){
        var postgresql = new BookPostgresDatabaseCrudPersister();
        for (Book book : books) {
            try {
                postgresql.createBook(book);
            } catch (SQLException e) {
                System.err.println("SQL operation failed");
                e.printStackTrace();
            }
        }
    }

    public void writeMySql(List<Book> books){
        var mysql = new BookMySqlDatabaseCrudPersister();
        for (Book book : books) {
            try {
                mysql.createBook(book);
            } catch (SQLException e) {
                System.err.println("SQL operation failed");
                e.printStackTrace();
            }
        }
    }
}
