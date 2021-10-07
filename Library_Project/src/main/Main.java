package main;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Locale;

import libraryInterface.LibraryActionsService;
import libraryInterface.LibraryInterface;
import libraryInterface.client.ClientActionsService;
import libraryInterface.filter.Filter;
import libraryInterface.rental.RentalService;
import libraryInterface.reporting.DepartmentReport;
import libraryInterface.reporting.Reports;
import model.client.Client;
import model.client.Person;
import model.library.DepartmentType;
import model.library.Library;
import model.library.book.Book;
import model.library.book.BookType;
import model.library.book.Genre;
import util.DummyBooksGenerator;
import util.DummyClientGenerator;

public class Main {

	static {
		Locale locale = new Locale.Builder().setLanguage("pl").setRegion("PL").build();
		Locale.setDefault(locale);
	}

	public static void main(String[] args) {

		DummyBooksGenerator dummyBooksGenerator = new DummyBooksGenerator();
		List<Book> ksiazki = dummyBooksGenerator.generate(80);

		Library library = new Library(ksiazki);

		DummyClientGenerator dummyClientGenerator = new DummyClientGenerator();
		List<Client> bazaKlientow = dummyClientGenerator.generate(40);
		bazaKlientow.add(new Person(99, DepartmentType.A, "Micha³", "Wêsierski"));
		bazaKlientow.add(new Person(90, DepartmentType.A, "Adrian", "Bronk"));
		bazaKlientow.add(new Person(91, DepartmentType.A, "Pawe³", "Ma³aszyñski"));
		bazaKlientow.add(new Person(92, DepartmentType.A, "Micha³", "Wiœniewski"));
		ClientActionsService clientActionsService = new ClientActionsService(bazaKlientow);
		RentalService rentalService = new RentalService();
		DepartmentReport departmentReportA = new DepartmentReport(DepartmentType.A);
		DepartmentReport departmentReportB = new DepartmentReport(DepartmentType.B);
		DepartmentReport departmentReportC = new DepartmentReport(DepartmentType.C);
		Reports reports = new Reports();
		reports.getLibraryReport().put(DepartmentType.A, departmentReportA);
		reports.getLibraryReport().put(DepartmentType.B, departmentReportB);
		reports.getLibraryReport().put(DepartmentType.C, departmentReportC);
		Filter filter = new Filter(clientActionsService);
		LibraryActionsService libraryActionsService = new LibraryActionsService(rentalService, filter, reports,
				clientActionsService);
		LibraryInterface libraryInterface = new LibraryInterface(library, libraryActionsService, clientActionsService);
		// libraryInterface.workWithLibrary();

		library.getDepartments().get(DepartmentType.A).add(
				new Book("Wa³kowanie Ameryki", "Marek Wa³kuski", BookType.NORMAL, DepartmentType.A, Genre.GUIDE, 48));
		library.getDepartments().get(DepartmentType.A).add(
				new Book("What if?", "Randall Munroe", BookType.NORMAL, DepartmentType.A, Genre.FANTASY, 58));
		library.getDepartments().get(DepartmentType.A).add(
				new Book("Factfullness", "Hans Rosling", BookType.NORMAL, DepartmentType.A, Genre.HISTORY, 43));
		library.getDepartments().get(DepartmentType.A).add(
				new Book("Kwantechizm", "Andrzej Dragan", BookType.NORMAL, DepartmentType.A, Genre.SCIENCE_FICTION, 35));
		library.getDepartments().get(DepartmentType.A).add(
				new Book("Zaginiêcie", "Remigiusz Mróz", BookType.NORMAL, DepartmentType.A, Genre.FANTASY, 27));
		

		Deque<String> booksToBorrowClient1 = new ArrayDeque<>();
		booksToBorrowClient1.add("Zaginiêcie");
		booksToBorrowClient1.add("Factfullness");
		booksToBorrowClient1.add("What if?");
		booksToBorrowClient1.add("Wa³kowanie Ameryki");
		booksToBorrowClient1.add("Kwantechizm");
		Deque<String> booksToReturnClient1 = new ArrayDeque<>();
		ClientThread clientThread1 = new ClientThread(library, clientActionsService, libraryActionsService,
				bazaKlientow, DepartmentType.A, booksToBorrowClient1, booksToReturnClient1, 99, 15);
		
		
		Deque<String> booksToBorrowClient2 = new ArrayDeque<>();
		booksToBorrowClient2.add("Wa³kowanie Ameryki");
		booksToBorrowClient2.add("Kwantechizm");
		Deque<String> booksToReturnClient2 = new ArrayDeque<>();
		booksToReturnClient2.add("Wa³kowanie Ameryki");
		booksToReturnClient2.add("Kwantechizm");
		ClientThread clientThread2 = new ClientThread(library, clientActionsService, libraryActionsService,
				bazaKlientow, DepartmentType.A, booksToBorrowClient2, booksToReturnClient2, 90, 15);
		
		new Thread(clientThread1).start();
		new Thread(clientThread2).start();
		clientThread1.run();
		clientThread2.run();
	

	}

}
