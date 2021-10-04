package libraryInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import libraryInterface.filter.Filter;
import libraryInterface.rental.RentalActions;
import libraryInterface.rental.RentalService;
import libraryInterface.reporting.Reports;
import model.client.Client;
import model.client.Person;
import model.library.DepartmentType;
import model.library.Library;
import model.library.book.Book;

public class LibraryActionsService {

	private RentalService rentalService;
	private Filter filter;
	private Reports reports;

	public LibraryActionsService(RentalService rentalService, Filter filter, Reports reports) {
		this.rentalService = rentalService;
		this.filter = filter;
		this.reports = reports;
	}

	public RentalService getRentalService() {
		return rentalService;
	}

	public Filter getFilter() {
		return filter;
	}

	public Reports getReports() {
		return reports;
	}

	RentalActions availableLibraryActions(Scanner in) {
		String actionStr;
		RentalActions action;
		System.out.println("W czym mo¿emy Panu/Pani pomóc?");
		System.out.println("Do wyboru: " + RentalActions.BORROW + " (" + RentalActions.BORROW.getAction() + ") | "
				+ RentalActions.BORROW_DEPT + " (" + RentalActions.BORROW_DEPT.getAction() + ") | "
				+ RentalActions.RETURN + " (" + RentalActions.RETURN.getAction() + ") | " + RentalActions.SHOW_ALL
				+ " (" + RentalActions.SHOW_ALL.getAction() + ") | " + "\n" + RentalActions.SHOW_FILTERED + " ("
				+ RentalActions.SHOW_FILTERED.getAction() + ") | " + RentalActions.SHOW_RENTED_BOOKS + " ("
				+ RentalActions.SHOW_RENTED_BOOKS.getAction() + ") | " 
				+ RentalActions.USER_MANAGEMENT + " (" + RentalActions.USER_MANAGEMENT.getAction() + ") | " + "\n" 
				+ RentalActions.GENERATE_REPORT + " (" + RentalActions.GENERATE_REPORT.getAction() + ") | "
				+ RentalActions.EXIT_LIBRARY + " (" + RentalActions.EXIT_LIBRARY.getAction() + ")");
		actionStr = in.next().toUpperCase();
		try {
			action = RentalActions.valueOf(actionStr);

		} catch (Exception e) {
			action = RentalActions.ILLEGAL;
		}
		return action;
	}

	void borrowBook(Library library, Book book, Client loggedUser) {
		if (loggedUser instanceof Person) {
			if (!rentalService.getRentedBooks().containsKey(loggedUser)) {
				rentalService.getRentedBooks().put(loggedUser, new HashMap<>());
				if (!rentalService.getRentedBooks().get(loggedUser).containsKey(loggedUser))
					rentalService.getRentedBooks().get(loggedUser).put(loggedUser, new ArrayList<>());
				rentalService.getRentedBooks().get(loggedUser).get(loggedUser).add(book);

			} else
				rentalService.getRentedBooks().get(loggedUser).get(loggedUser).add(book);
		} else {
			if (!rentalService.getRentedBooks().containsKey(loggedUser)) {
				rentalService.getRentedBooks().put(loggedUser, new HashMap<>());
				if (!rentalService.getRentedBooks().get(loggedUser).containsKey(loggedUser))
					rentalService.getRentedBooks().get(loggedUser).put(loggedUser, new ArrayList<>());
				rentalService.getRentedBooks().get(loggedUser).get(loggedUser).add(book);
			} else if (rentalService.getRentedBooks().containsKey(loggedUser)) {
				if (!rentalService.getRentedBooks().get(loggedUser).containsKey(loggedUser))
					rentalService.getRentedBooks().get(loggedUser).put(loggedUser, new ArrayList<>());
				rentalService.getRentedBooks().get(loggedUser).get(loggedUser).add(book);
			} else
				rentalService.getRentedBooks().get(loggedUser).get(loggedUser).add(book);

		}

		library.getDepartments().get(loggedUser.getMotherDepartment()).remove(book);
	}

	void showRented(Client loggedUser) {
		if (loggedUser != null) {
			if (rentalService.getRentedBooks().containsKey(loggedUser)) {

				if (loggedUser instanceof Person) {
					int num = 0;
					for (Book book : rentalService.getRentedBooks().get(loggedUser).get(loggedUser)) {
						System.out.println(book);
						num++;
					}

					if (num == 0) {
						System.out.println("Brak wypo¿yczonych ksi¹¿ek");
					}

				} else {

					int num = 0;
					for (List<Book> client : rentalService.getRentedBooks().get(loggedUser).values()) {
						for (Book book : client) {
							System.out.println(book);
							num++;
						}
					}
					if (num == 0) {
						System.out.println("Brak wypo¿yczonych ksi¹¿ek");
					}

				}

			} else
				System.out.println("Brak wypo¿yczonych ksi¹¿ek");

		} else
			System.out.println("Brak logowania");
	}

	void showLibrary(Library library) {
		System.out.println(library.getDepartments().get(DepartmentType.A).size());
		System.out.println(library.getDepartments().get(DepartmentType.B).size());
		System.out.println(library.getDepartments().get(DepartmentType.C).size());

		for (DepartmentType departmentType : library.getDepartments().keySet()) {
			for (Book book : library.getDepartments().get(departmentType)) {
				System.out.println(book);
			}
		}

	}

}
