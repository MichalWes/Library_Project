package libraryInterface.admin;

import model.library.book.Book;
import util.DummyBooksGenerator;

import java.util.List;

public class BookGenerator {
    public List<Book> generateBooks(){
        DummyBooksGenerator dummyBooksGenerator = new DummyBooksGenerator();
        List<Book> ksiazki = dummyBooksGenerator.generate(80);
        return ksiazki;
    }
}
