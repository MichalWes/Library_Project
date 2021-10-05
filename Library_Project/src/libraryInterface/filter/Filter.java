package libraryInterface.filter;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import libraryInterface.client.ClientActionsService;
import model.library.DepartmentType;
import model.library.Library;
import model.library.book.Book;
import model.library.book.BookType;
import model.library.book.Genre;

public class Filter {

	List<Book> filteredBooks = new ArrayList<>();
	private ClientActionsService clientActionsService;

	public Filter(ClientActionsService clientActionsService) {
		this.clientActionsService = clientActionsService;
	}

	public void showFiltered(Library library, Scanner in) {

		FilterType action;
		DepartmentType dept = clientActionsService.setDepartment(in);

		while (true) {
			System.out.println("Podaj filtr, kt�rego chcia�by� u�y�: ");

			System.out.println("Do wyboru: " + FilterType.AUTHOR + " (" + FilterType.AUTHOR.getAction() + ") | "
					+ FilterType.CATEGORY + " (" + FilterType.CATEGORY.getAction() + ") | " + FilterType.DESCRIPTION
					+ " (" + FilterType.DESCRIPTION.getAction() + ") | " + FilterType.GENRE + " ("
					+ FilterType.GENRE.getAction() + ") | " + "\n" + FilterType.LAST_FILTER + " ("
					+ FilterType.LAST_FILTER.getAction() + ")");

			String filterStr = in.next().toUpperCase();
			try {
				action = FilterType.valueOf(filterStr);
				break;

			} catch (Exception e) {
				System.out.println(FilterType.ILLEGAL.getAction());
			}
		}

		switch (action) {
		case AUTHOR:
			Scanner author = new Scanner(System.in);
			System.out.println("Podaj autora");
			String authorScr = author.nextLine();

			filteredBooks = library.getDepartments().get(dept).stream()
					.filter(book -> book.getAuthor().equals(authorScr)).collect(Collectors.toList());
			filteredBooks.stream().forEach(book -> System.out.println(book));
			if (filteredBooks.isEmpty())
				System.out.println("Nie znaleziono ksi��ek tego autora");
			break;

		case CATEGORY:

			System.out.println("Podaj kategori�:");
			System.out.println("Do wyboru: " + BookType.BESTSELLER + " | " + BookType.NORMAL + " | " + BookType.OLD);
			String categoryScr = in.next().toUpperCase();
			BookType bookType = BookType.valueOf(categoryScr);

			filteredBooks = library.getDepartments().get(dept).stream()
					.filter(book -> book.getBookType().equals(bookType)).collect(Collectors.toList());
			filteredBooks.stream().forEach(book -> System.out.println(book));
			if (filteredBooks.isEmpty())
				System.out.println("Nie znaleziono ksi��ek z tej kategorii");
			break;

		case DESCRIPTION:

			Scanner description = new Scanner(System.in);
			System.out.println("Podaj opis: ");
			String descriptionScr = description.nextLine();

			filteredBooks = library.getDepartments().get(dept).stream()
					.filter(book -> book.getDescription().equals(descriptionScr)).collect(Collectors.toList());
			filteredBooks.stream().forEach(book -> System.out.println(book));
			if (filteredBooks.isEmpty())
				System.out.println("Nie znaleziono ksi��ek z tym opisem");
			break;

		case GENRE:

			while (true) {
				try {
					System.out.println("Podaj gatunek/dzia�: ");
					System.out.println("Do wyboru: " + Genre.CHILDREN + " | " + Genre.COOKING + " | " + Genre.FANTASY
							+ " | " + Genre.GUIDE + " | " + Genre.HISTORY + " | " + Genre.HORROR + " | " + Genre.MEMOIR
							+ " | " + Genre.ROMANCE + " | " + Genre.SCIENCE_FICTION + " | " + Genre.TRAVEL + " | ");
					String genreScr = in.next().toUpperCase();
					Genre genreType = Genre.valueOf(genreScr);

					filteredBooks = library.getDepartments().get(dept).stream()
							.filter(book -> book.getGenre() == genreType).collect(Collectors.toList());
					filteredBooks.stream().forEach(book -> System.out.println(book));
					if (filteredBooks.isEmpty())
						System.out.println("Nie znaleziono ksi��ek z tego gatunku/dzia�u");
					break;

				} catch (Exception e) {
					action = FilterType.ILLEGAL;
				}
			}

			break;
		case LAST_FILTER:
			if (filteredBooks.isEmpty()) {
				System.out.println(
						"Nie przeprowadzono jeszcze filtrowania ksi��ek lub ostatnie wyszukiwanie nie przynios�o wynik�w");
			} else
				filteredBooks.stream().forEach(book -> System.out.println(book));
			break;

		case ILLEGAL:
			System.out.println(FilterType.ILLEGAL.getAction());
			break;

		}
	}

}
