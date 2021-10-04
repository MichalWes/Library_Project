package libraryInterface.rental;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import model.client.Client;
import model.client.Organization;
import model.client.Person;
import model.library.book.Book;
import model.library.book.BookType;
import model.library.book.Genre;

public class RentalService {
	private HashMap<Client, HashMap<Client, List<Book>>> rentedBooks = new HashMap<>();

	public double penaltyAmountCalc(Book book) {
		int daysBorrowedAboveLimit = book.getDaysBorrowed() - 14;
		double penalty = daysBorrowedAboveLimit * book.getValue() * 0.01;
		if (penalty < 0.5)
			penalty = 0.5;
		if (book.getBookType() == BookType.BESTSELLER)
			penalty *= 2;
		System.out.println(String.format("Naliczono karê z tytu³u opóŸnienia równ¹ %sPLN", penalty));
		return penalty;
	}

	public boolean clientBorrowConditionsChecker(Client client, RentalService rentalService, Book book) {
		HashMap<Genre, List<Book>> listaDoSprawdzenia = new HashMap<>();
		boolean conditionCheck = true;
		boolean[] warunki = new boolean[3];

		listaDoSprawdzenia.put(book.getGenre(), new ArrayList<>());
		listaDoSprawdzenia.get(book.getGenre()).add(book);

		warunki[0] = numberOfBooksCheck(client, rentalService);
		warunki[1] = sameGenreCheck(client, listaDoSprawdzenia);
		warunki[2] = betsellerCheck(client, rentalService, book);

		if (rentalService.getRentedBooks().containsKey(client)) {
			for (boolean b : warunki) {
				if (!b) {
					conditionCheck = false;
					break;
				}
			}
		}

		return conditionCheck;
	}

	public boolean clientBorrowConditionsChecker(Person person, Organization organization, RentalService rentalService,
			Book book) {
		HashMap<Genre, List<Book>> listaDoSprawdzenia = new HashMap<>();
		boolean conditionCheck = true;
		boolean[] warunki = new boolean[2];

		listaDoSprawdzenia.put(book.getGenre(), new ArrayList<>());
		listaDoSprawdzenia.get(book.getGenre()).add(book);

		warunki[0] = numberOfBooksCheck(person, organization, rentalService);
		warunki[1] = sameGenreCheck(person, organization, listaDoSprawdzenia);

		if (!(rentalService.getRentedBooks().get(organization) == null)) {
			if (rentalService.getRentedBooks().get(organization).containsKey(person)) {
				for (boolean b : warunki) {
					if (!b) {
						conditionCheck = false;
						break;
					}
				}
			}
		}
		

		return conditionCheck;
	}

	private boolean sameGenreCheck(Client client, HashMap<Genre, List<Book>> listaDoSprawdzenia) {
		boolean conditionCheck = true;
		int checkNumber = 0;

		if (client instanceof Person) {
			checkNumber = 2;
		} else
			checkNumber = 6;
		if (!(getRentedBooks().get(client) == null) && !(getRentedBooks().get(client).get(client) == null)) {

			for (Book book : getRentedBooks().get(client).get(client)) {
				if (!listaDoSprawdzenia.containsKey(book.getGenre())) {
					listaDoSprawdzenia.put(book.getGenre(), new ArrayList<>());
					listaDoSprawdzenia.get(book.getGenre()).add(book);
				} else
					listaDoSprawdzenia.get(book.getGenre()).add(book);
			}
			conditionCheck = genreCheck(listaDoSprawdzenia, conditionCheck, checkNumber);
		}

		return conditionCheck;
	}

	private boolean sameGenreCheck(Person person, Organization organization,
			HashMap<Genre, List<Book>> listaDoSprawdzenia) {
		boolean conditionCheck = true;
		int checkNumber = 3;

		if (!(getRentedBooks().get(person) == null)) {

			for (Book book : getRentedBooks().get(person).get(person)) {
				if (!listaDoSprawdzenia.containsKey(book.getGenre())) {
					listaDoSprawdzenia.put(book.getGenre(), new ArrayList<>());
					listaDoSprawdzenia.get(book.getGenre()).add(book);
				} else
					listaDoSprawdzenia.get(book.getGenre()).add(book);
			}
			
			

			if (!(getRentedBooks().get(organization) == null) && !(getRentedBooks().get(organization).get(person) == null)) {
				for (Book book : getRentedBooks().get(organization).get(person)) {
					if (!listaDoSprawdzenia.containsKey(book.getGenre())) {
						listaDoSprawdzenia.put(book.getGenre(), new ArrayList<>());
						listaDoSprawdzenia.get(book.getGenre()).add(book);
					} else
						listaDoSprawdzenia.get(book.getGenre()).add(book);
				}
			}
			
			conditionCheck = genreCheck(listaDoSprawdzenia, conditionCheck, checkNumber);
		}

		return conditionCheck;
	}

	private boolean genreCheck(HashMap<Genre, List<Book>> listaDoSprawdzenia, boolean conditionCheck, int checkNumber) {
		for (Genre genre : listaDoSprawdzenia.keySet()) {
			if (!(listaDoSprawdzenia.get(genre).size() <= checkNumber)) {
				conditionCheck = false;
				System.out.println(String.format(
						"Nie mo¿a wypo¿yczyæ wiêcej ksi¹¿ek ze wzglêdu na max %s z jednego dzia³u przys³uguj¹ce na klienta",
						checkNumber));
			}
		}
		return conditionCheck;
	}

	private boolean numberOfBooksCheck(Client client, RentalService rentalService) {
		boolean conditionCheck = true;
		int checkNumber = 0;

		if (client instanceof Person) {
			checkNumber = 4;
			if (!(rentalService.getRentedBooks().get(client) == null)) {
				if (!(rentalService.getRentedBooks().get(client).get(client).size() < checkNumber)) {
					conditionCheck = false;
					System.out.println(String.format(
							"Nie mo¿a wypo¿yczyæ wiêcej ksi¹¿ek ze wzglêdu na max %s przys³uguj¹ce na klienta",
							checkNumber));
				}

			}
		} else {
			checkNumber = 10;
			if (!(rentalService.getRentedBooks().get(client) == null)) {
				int organizationMembersBookNumber = 0;
				
				
				for (List<Book> books : rentalService.getRentedBooks().get(client).values()) {
					for (int i = 0; i < books.size(); i++) {
						organizationMembersBookNumber += 1;
					}
				}
				
				
				if (!(organizationMembersBookNumber < checkNumber)) {
					conditionCheck = false;
					System.out.println(String.format(
							"Nie mo¿a wypo¿yczyæ wiêcej ksi¹¿ek ze wzglêdu na max %s przys³uguj¹ce na organizacjê",
							checkNumber));
				}

			}
			
		}

		return conditionCheck;

	}

	private boolean numberOfBooksCheck(Client client, Organization organization, RentalService rentalService) {
		boolean conditionCheck = true;
		int checkNumber = 2;

		if (!(rentalService.getRentedBooks().get(organization) == null)
				&& !(rentalService.getRentedBooks().get(organization).get(client) == null)) {
			if (!(rentalService.getRentedBooks().get(organization).get(client).size() < checkNumber)) {
				conditionCheck = false;
				System.out.println(String.format(
						"Nie mo¿a wypo¿yczyæ wiêcej ksi¹¿ek ze wzglêdu na max %s przys³uguj¹ce na klienta w organizacji",
						checkNumber));
			}

		}
		return conditionCheck;

	}

	public boolean betsellerCheck(Client client, RentalService rentalService, Book book) {
		boolean conditionCheck = true;
		int checkNumber = 0;

		if (client instanceof Person) {
			checkNumber = 1;
		} else
			return true;

		if (!(rentalService.getRentedBooks().get(client) == null)) {
			int bestSellerNum = 0;
			for (Book bookIter : rentalService.getRentedBooks().get(client).get(client)) {
				if (bookIter.getBookType() == BookType.BESTSELLER) {
					bestSellerNum += 1;
					if (bestSellerNum >= 1 && book.getBookType() == BookType.BESTSELLER) {
						System.out.println(String.format(
								"Nie mo¿a wypo¿yczyæ wiêcej ksi¹¿ek ze wzglêdu na max %s bestseller przys³uguj¹cy na klienta",
								checkNumber));
						return false;
					}
				}
			}

		}

		return conditionCheck;

	}

	public HashMap<Client, HashMap<Client, List<Book>>> getRentedBooks() {
		return rentedBooks;
	}

}
