package util;

import java.util.function.Predicate;

import model.library.book.Book;
import model.library.book.Genre;

public class BookGenrePredicate implements Predicate<Book>{
	private final Genre genre;

	public BookGenrePredicate(Genre genre) {
		this.genre = genre;
	}

	@Override
	public boolean test(Book book) {
		return genre == book.getGenre();
	}

}
