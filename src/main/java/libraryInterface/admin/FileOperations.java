package libraryInterface.admin;

import model.library.DepartmentType;
import model.library.Library;
import model.library.book.Book;

import java.io.*;
import java.util.HashSet;
import java.util.regex.Pattern;

public class FileOperations {

    String projectPath = "C:\\Users\\micha\\Desktop\\Coding\\Languages\\Java\\Kurs Sii\\LibraryProject\\src\\main\\resources\\";
    String fileName = "books.dat";

    public void writeLibrarytoFile(Library library) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(projectPath + fileName))) {

            for (DepartmentType departmentType : DepartmentType.values()) {
                for (Book book : library.getDepartments().get(departmentType)) {
                    if (book.getTitle().length() < 9
                            || !Pattern.matches("[a-zA-Z\\d]{8}\\-{1}", book.getTitle().substring(0, 9))) {
                        out.writeObject(book);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found exception");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Input/Output related exception");
            e.printStackTrace();
        }
    }

    public HashSet<Book> readLibraryFromFile() {
        HashSet<Book> zPliku = new HashSet<>();
        try (var in = new ObjectInputStream(new FileInputStream(projectPath + fileName))) {
            while (true) {
                var object = in.readObject();
                if (object instanceof Book) {
                    zPliku.add((Book) object);
                }
            }
        } catch (ClassNotFoundException e) {
            System.err.println("Class of serialized object cannot be found");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Input/Output related exception");
            e.printStackTrace();
        }
        return zPliku;
    }
}
