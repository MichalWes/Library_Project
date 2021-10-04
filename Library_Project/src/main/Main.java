package main;

import java.util.List;

import libraryInterface.LibraryActionsService;
import libraryInterface.LibraryInterface;
import libraryInterface.filter.Filter;
import libraryInterface.rental.RentalService;
import libraryInterface.reporting.DepartmentReport;
import libraryInterface.reporting.Reports;
import model.client.Client;
import model.client.Person;
import model.library.DepartmentType;
import model.library.Library;
import model.library.book.Book;
import util.DummyBooksGenerator;
import util.DummyClientGenerator;

public class Main {

	public static void main(String[] args) {
		
		DummyBooksGenerator dummyBooksGenerator = new DummyBooksGenerator();
		List<Book> ksiazki = dummyBooksGenerator.generate(80);
	
		Library library = new Library(ksiazki);
		
		DummyClientGenerator dummyClientGenerator = new DummyClientGenerator();
		List<Client> bazaKlientow = dummyClientGenerator.generate(40);
		bazaKlientow.add(new Person(99, DepartmentType.A, "Micha�", "W�sierski"));
		RentalService rentalService = new RentalService();
		DepartmentReport departmentReportA = new DepartmentReport(DepartmentType.A);
		DepartmentReport departmentReportB = new DepartmentReport(DepartmentType.B);
		DepartmentReport departmentReportC = new DepartmentReport(DepartmentType.C);
		Filter filter = new Filter();
		Reports reports = new Reports();
		reports.getLibraryReport().put(DepartmentType.A, departmentReportA);
		reports.getLibraryReport().put(DepartmentType.B, departmentReportB);
		reports.getLibraryReport().put(DepartmentType.C, departmentReportC);
		LibraryActionsService libraryActionsService = new LibraryActionsService(rentalService, filter, reports);
		LibraryInterface libraryInterface = new LibraryInterface(library ,bazaKlientow, libraryActionsService);
		libraryInterface.workWithLibrary();
		
	
	}

}
