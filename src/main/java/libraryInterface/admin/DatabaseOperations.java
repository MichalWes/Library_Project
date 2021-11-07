package libraryInterface.admin;

import model.library.DepartmentType;
import model.library.Library;
import model.library.book.Book;
import util.BookMySqlDatabaseCrudPersister;
import util.BookPostgresDatabaseCrudPersister;

import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;

public class DatabaseOperations {

    public void writePostgreSql(Library library) {
        var postgresql = new BookPostgresDatabaseCrudPersister();
        for (DepartmentType departmentType : DepartmentType.values()) {
            for (Book book : library.getDepartments().get(departmentType)) {
                if (book.getTitle().length() < 9
                        || !Pattern.matches("[a-zA-Z\\d]{8}\\-{1}", book.getTitle().substring(0, 9))) {
                    try {
                        postgresql.createBook(book);
                    } catch (SQLException e) {
                        System.err.println("SQL operation failed");
                        e.printStackTrace();
                    }
                }
            }
        }
        System.out.println("Pomyœlnie zapisano ksi¹¿ki w bazie danych PostgreSql");
    }

    public void writeMySql(Library library) {
        var mySql = new BookMySqlDatabaseCrudPersister();
        for (DepartmentType departmentType : DepartmentType.values()) {
            for (Book book : library.getDepartments().get(departmentType)) {
                if (book.getTitle().length() < 9
                        || !Pattern.matches("[a-zA-Z\\d]{8}\\-{1}", book.getTitle().substring(0, 9))) {
                    try {
                        mySql.createBook(book);
                    } catch (SQLException e) {
                        System.err.println("SQL operation failed");
                        e.printStackTrace();
                    }
                }
            }
        }
        System.out.println("Pomyœlnie zapisano ksi¹¿ki w bazie danych MySql");
    }
}
