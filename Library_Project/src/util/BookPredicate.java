package util;

import model.library.book.Book;

public interface BookPredicate {
	boolean test (Book book);
}
