package libraryInterface.filter;

import java.util.Scanner;

import model.library.DepartmentType;
import model.library.Library;
import model.library.book.Book;
import model.library.book.BookType;
import model.library.book.Genre;

public class Filter {
	
	
	public void showFiltered(Library library, Scanner in) {

		FilterType action;
		DepartmentType dept = libraryInterface.LibraryInterface.setDepartment(in);;

		while (true) {
			System.out.println("Podaj filtr, którego chcia³byœ u¿yæ: ");

			System.out.println("Do wyboru: " + FilterType.AUTHOR + " (" + FilterType.AUTHOR.getAction() + ") | "
					+ FilterType.CATEGORY + " (" + FilterType.CATEGORY.getAction() + ") | " + FilterType.DESCRIPTION
					+ " (" + FilterType.DESCRIPTION.getAction() + ") | " + FilterType.GENRE + " ("
					+ FilterType.GENRE.getAction() + ")");

			String filterStr = in.next().toUpperCase();
			try {
				action = FilterType.valueOf(filterStr);
				break;

			} catch (Exception e) {
				action = FilterType.ILLEGAL;
			}
		}

		switch (action) {
		case AUTHOR:
			Scanner author = new Scanner(System.in);
			System.out.println("Podaj autora");
			String authorScr = author.nextLine();
			int count = 0;

			for (Book book : library.getDepartments().get(dept)) {
				if (book.getAuthor().equals(authorScr)) {
					System.out.println(book);
					count++;
				}
			}
			if(count == 0) {
				System.out.println("Nie znaleziono ksi¹¿ek tego autora");
			}

			break;

		case CATEGORY:
			
			System.out.println("Podaj kategoriê:");
			System.out.println("Do wyboru: " + BookType.BESTSELLER + " | " + BookType.NORMAL + " | " + BookType.OLD);
			String categoryScr = in.next().toUpperCase();
			BookType bookType = BookType.valueOf(categoryScr); 

			for (Book book : library.getDepartments().get(dept)) {
				if (book.getBookType().equals(bookType)) {
					System.out.println(book);
				}
			}

			break;

		case DESCRIPTION:

			Scanner description = new Scanner(System.in);
			System.out.println("Podaj opis: ");
			String descriptionScr = description.nextLine();

			for (Book book : library.getDepartments().get(dept)) {
				if (book.getDescription().equals(descriptionScr)) {
					System.out.println(book);
				}
			}

			break;
		
		case GENRE:
			
			while(true) {
				try {
					int countGenre = 0;
					System.out.println("Podaj gatunek/dzia³: ");
					System.out.println("Do wyboru: " + Genre.CHILDREN + " | " + Genre.COOKING + " | " + Genre.FANTASY + " | " + Genre.GUIDE + " | "
							+ Genre.HISTORY + " | " + Genre.HORROR + " | "+ Genre.MEMOIR + " | " + Genre.ROMANCE + " | " + Genre.SCIENCE_FICTION + " | "
							+ Genre.TRAVEL + " | ");
					String genreScr = in.next().toUpperCase();
					Genre genreType = Genre.valueOf(genreScr);

					for (Book book : library.getDepartments().get(dept)) {
						if (book.getGenre() == genreType) {
							System.out.println(book);
							countGenre++;
						}
					}
					
					if(countGenre == 0) {
						System.out.println("W tym departamencie nie ma takiego dzia³u");
					}

					
					break;

				} catch (Exception e) {
					action = FilterType.ILLEGAL;
				}
				
			}

			break;

		case ILLEGAL:
			System.out.println(FilterType.ILLEGAL.getAction());
			break;

		}
	}
	
	

}
