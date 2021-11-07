package model.library.book;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import model.library.DepartmentType;

public class BookFactory {
	public List<String> titles = new ArrayList<>();
	public List<String> authors = new ArrayList<>();

	public Book create() {
		int randomTitleIndex = (int) (Math.random() * titles.size());
		int randomAuthorIndex = (int) (Math.random() * authors.size());
		int randomBookTypeIndex = (int) (Math.random() * BookType.values().length);
		int randomDepartmentTypeIndex = (int) (Math.random() * DepartmentType.values().length);
		int randomGenreIndex = (int) (Math.random() * Genre.values().length);

		String title = titles.get(randomTitleIndex);
		if (titles.size() != 1) {
			titles.remove(randomTitleIndex);
		} else title = (UUID.randomUUID()).toString();

		String author = authors.get(randomAuthorIndex);
		BookType bookType = BookType.values()[randomBookTypeIndex];
		DepartmentType bookDepartment = DepartmentType.values()[randomDepartmentTypeIndex];
		Genre genre = Genre.values()[randomGenreIndex];
		
	    
		int value = (int)(30 * (randomBookTypeIndex + 1) * ((Math.random()*0.5)+0.3));

		return new Book(title, author, bookType, bookDepartment, genre, value);
	}

}
