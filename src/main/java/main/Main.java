package main;

import libraryInterface.LibraryActionsService;
import libraryInterface.LibraryInterface;
import libraryInterface.admin.AdminActionsService;
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
import util.DummyClientGenerator;

import java.sql.SQLException;
import java.util.List;
import java.util.Locale;

public class Main {

    static {
        Locale locale = new Locale.Builder().setLanguage("pl").setRegion("PL").build();
        Locale.setDefault(locale);
    }

    public static void main(String[] args) throws SQLException {

        var library = new Library();
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
        var dummyClientGenerator = new DummyClientGenerator();
        List<Client> bazaKlientow = dummyClientGenerator.generate(40);
        bazaKlientow.add(new Person(99, DepartmentType.A, "Micha³", "Wêsierski"));
        bazaKlientow.add(new Person(90, DepartmentType.A, "Adrian", "Bronk"));
        bazaKlientow.add(new Person(91, DepartmentType.A, "Pawe³", "Ma³aszyñski"));
        bazaKlientow.add(new Person(92, DepartmentType.A, "Micha³", "Wiœniewski"));
        var clientActionsService = new ClientActionsService(bazaKlientow);
        var adminActionsService = new AdminActionsService();
        var rentalService = new RentalService();
        var departmentReportA = new DepartmentReport(DepartmentType.A);
        var departmentReportB = new DepartmentReport(DepartmentType.B);
        var departmentReportC = new DepartmentReport(DepartmentType.C);
        var reports = new Reports();
        reports.getLibraryReport().put(DepartmentType.A, departmentReportA);
        reports.getLibraryReport().put(DepartmentType.B, departmentReportB);
        reports.getLibraryReport().put(DepartmentType.C, departmentReportC);
        var filter = new Filter(clientActionsService);
        LibraryActionsService libraryActionsService = new LibraryActionsService(rentalService, filter, reports,
                clientActionsService);
        LibraryInterface libraryInterface = new LibraryInterface(library, libraryActionsService, clientActionsService, adminActionsService);
        libraryInterface.workWithLibrary();

    }

}


