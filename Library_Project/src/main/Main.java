package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

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
		libraryInterface.workWithLibrary();

		library.getDepartments().get(DepartmentType.A).add(
				new Book("Wa³kowanie Ameryki", "Marek Wa³kuski", BookType.NORMAL, DepartmentType.A, Genre.GUIDE, 48));
		library.getDepartments().get(DepartmentType.A)
				.add(new Book("What if?", "Randall Munroe", BookType.NORMAL, DepartmentType.A, Genre.FANTASY, 58));
		library.getDepartments().get(DepartmentType.A)
				.add(new Book("Factfullness", "Hans Rosling", BookType.NORMAL, DepartmentType.A, Genre.HISTORY, 43));
		library.getDepartments().get(DepartmentType.A).add(new Book("Kwantechizm", "Andrzej Dragan", BookType.NORMAL,
				DepartmentType.A, Genre.SCIENCE_FICTION, 35));
		library.getDepartments().get(DepartmentType.A)
				.add(new Book("Zaginiêcie", "Remigiusz Mróz", BookType.NORMAL, DepartmentType.A, Genre.FANTASY, 27));

//		Optional<Client> clientFound1 = bazaKlientow.stream().filter(client1 -> client1.getId() == 99).findFirst();
//		Client loggedClient1 = clientFound1.get();
//		System.out.println(loggedClient1);
//		List<String> booksToBorrowClient1 = new ArrayList<>();
//		booksToBorrowClient1.add("Zaginiêcie");
//		booksToBorrowClient1.add("Factfullness");
//		booksToBorrowClient1.add("What if?");
//		booksToBorrowClient1.add("Wa³kowanie Ameryki");
//		booksToBorrowClient1.add("Kwantechizm");
//		Collections.shuffle(booksToBorrowClient1);
//		List<String> booksToReturnClient1 = new ArrayList<>();
//		ClientThread clientThread1 = new ClientThread(loggedClient1, DepartmentType.A, library, libraryActionsService,
//				booksToBorrowClient1, booksToReturnClient1, 15);
//
//		Optional<Client> clientFound2 = bazaKlientow.stream().filter(client2 -> client2.getId() == 90).findFirst();
//		Client loggedClient2 = clientFound2.get();
//		System.out.println(loggedClient2);
//		List<String> booksToBorrowClient2 = new ArrayList<>();
//		booksToBorrowClient2.add("Zaginiêcie");
//		booksToBorrowClient2.add("Factfullness");
//		booksToBorrowClient2.add("What if?");
//		booksToBorrowClient2.add("Wa³kowanie Ameryki");
//		booksToBorrowClient2.add("Kwantechizm");
//		Collections.shuffle(booksToBorrowClient2);
//		List<String> booksToReturnClient2 = new ArrayList<>();
//		// booksToReturnClient2.add("Wa³kowanie Ameryki");
//		// booksToReturnClient2.add("Kwantechizm");
//		ClientThread clientThread2 = new ClientThread(loggedClient2, DepartmentType.A, library, libraryActionsService,
//				booksToBorrowClient2, booksToReturnClient2, 15);
//
//		Optional<Client> clientFound3 = bazaKlientow.stream().filter(client3 -> client3.getId() == 91).findFirst();
//		Client loggedClient3 = clientFound3.get();
//		System.out.println(loggedClient3);
//		List<String> booksToBorrowClient3 = new ArrayList<>();
//		booksToBorrowClient3.add("Zaginiêcie");
//		booksToBorrowClient3.add("Factfullness");
//		booksToBorrowClient3.add("What if?");
//		booksToBorrowClient3.add("Wa³kowanie Ameryki");
//		booksToBorrowClient3.add("Kwantechizm");
//		Collections.shuffle(booksToBorrowClient3);
//		List<String> booksToReturnClient3 = new ArrayList<>();
//		ClientThread clientThread3 = new ClientThread(loggedClient3, DepartmentType.A, library, libraryActionsService,
//				booksToBorrowClient3, booksToReturnClient3, 15);
//
//		ClientThread[] clientThreads = { clientThread1, clientThread2, clientThread3 };
//
//		System.out.println("--------------------------------------------------------------------------");
//
//		ExecutorService executor = Executors.newFixedThreadPool(3); //dodanie do metody borrow synchronized rozwi¹zuje problemy nieprawid³owego zachowania aplikacji.
//		executor.submit(clientThread1);
//		executor.submit(clientThread2);
//		executor.submit(clientThread3);
//
////		new Thread(clientThread1).start();
////		new Thread(clientThread2).start();
////		new Thread(clientThread3).start();
//		executor.shutdown();

	}

}
