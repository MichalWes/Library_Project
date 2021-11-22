package libraryInterface;

import libraryInterface.client.ClientActionsService;
import libraryInterface.filter.Filter;
import libraryInterface.rental.RentalActions;
import libraryInterface.rental.RentalService;
import libraryInterface.reporting.Reports;
import model.client.Client;
import model.client.Organization;
import model.client.Person;
import model.library.DepartmentType;
import model.library.Library;
import model.library.book.Book;
import util.Util;

import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

public class LibraryActionsService {

    private RentalService rentalService;
    private Filter filter;
    private Reports reports;
    private ClientActionsService clientActionsService;

    public LibraryActionsService(RentalService rentalService, Filter filter, Reports reports,
                                 ClientActionsService clientActionsService) {
        this.rentalService = rentalService;
        this.filter = filter;
        this.reports = reports;
        this.clientActionsService = clientActionsService;
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
                + RentalActions.SHOW_RENTED_BOOKS.getAction() + ") | " + RentalActions.USER_MANAGEMENT + " ("
                + RentalActions.USER_MANAGEMENT.getAction() + ") | " + "\n" + RentalActions.GENERATE_REPORT + " ("
                + RentalActions.GENERATE_REPORT.getAction() + ") | " + RentalActions.EXIT_LIBRARY + " ("
                + RentalActions.EXIT_LIBRARY.getAction() + ")");
        actionStr = in.nextLine().toUpperCase();
        try {
            action = RentalActions.valueOf(actionStr);

        } catch (Exception e) {
            action = RentalActions.ILLEGAL;
        }
        return action;
    }

    public boolean borrow(Library library) {
        if (clientActionsService.getLoggedUser().getMotherDepartment() != clientActionsService.getDepartmentActive()) {
            System.out.println(
                    "W tym oddziale nie mo¿na wypo¿yczaæ ksi¹¿ek, proszê o skorzystanie z dzia³u macierzystego");

        } else {

            Scanner bookScanner = new Scanner(System.in);
            System.out.println("Proszê podaæ tytu³ ksi¹¿ki do wypo¿yczenia: ");
            String bookTitle = "";

            bookTitle = bookScanner.nextLine();
            bookScanner = null;

            for (Book book : library.getDepartments().get(clientActionsService.getDepartmentActive())) {
                if (bookTitle.equals(book.getTitle())) {
                    if (clientActionsService.getLoggedUserOrganization().isEmpty()
                            && clientActionsService.getLoggedUser() instanceof Person) {
                        if (rentalService.clientBorrowConditionsChecker((Person) clientActionsService.getLoggedUser(),
                                rentalService, book)) {
                            borrowBook(library, book, clientActionsService.getLoggedUser());
                            System.out.println("Wypo¿yczono ksi¹¿kê " + book + "u¿ytkownikowi "
                                    + clientActionsService.getLoggedUser());
                            reports.addBookLent(clientActionsService.getDepartmentActive());
                            reports.addBookBorrowedClient(clientActionsService.getLoggedUser(),
                                    clientActionsService.getDepartmentActive());
                            return true;
                        } else
                            return false;
                    } else if (clientActionsService.getLoggedUserOrganization().isEmpty()
                            && clientActionsService.getLoggedUser() instanceof Organization) {
                        if (rentalService.clientBorrowConditionsChecker(
                                (Organization) clientActionsService.getLoggedUser(), rentalService, book)) {
                            borrowBook(library, book, clientActionsService.getLoggedUser());
                            System.out.println("Wypo¿yczono ksi¹¿kê " + book + "u¿ytkownikowi "
                                    + clientActionsService.getLoggedUser());
                            reports.addBookLent(clientActionsService.getDepartmentActive());
                            reports.addBookBorrowedClient(clientActionsService.getLoggedUser(),
                                    clientActionsService.getDepartmentActive());
                            return true;
                        } else
                            return false;
                    } else {
                        if (rentalService.clientBorrowConditionsChecker((Person) clientActionsService.getLoggedUser(),
                                rentalService, book)) {
                            borrowBook(library, book, clientActionsService.getLoggedUser());
                            System.out.println("Wypo¿yczono ksi¹¿kê " + book + "u¿ytkownikowi "
                                    + clientActionsService.getLoggedUser());
                            reports.addBookLent(clientActionsService.getDepartmentActive());
                            reports.addBookBorrowedClient(clientActionsService.getLoggedUser(),
                                    clientActionsService.getDepartmentActive());
                            return true;
                        } else {
                            System.out.println(
                                    "Poniewa¿ jesteœ cz³onkiem organizacji, jest mo¿liwoœæ wypo¿yczenia ksi¹¿ki na organizacjê");
                            System.out.println("Trwa sprawdzanie czy takie wypo¿ycznie jest mo¿liwe");
                            if (rentalService.clientBorrowConditionsChecker(
                                    (Organization) clientActionsService.getLoggedUserOrganization().get(),
                                    rentalService, book)
                                    && rentalService.clientBorrowConditionsChecker(
                                    (Person) clientActionsService.getLoggedUser(),
                                    clientActionsService.getLoggedUserOrganization().get(), rentalService,
                                    book)) {
                                if (!rentalService.getRentedBooks()
                                        .containsKey(clientActionsService.getLoggedUserOrganization().get())) {
                                    rentalService.getRentedBooks().put(
                                            clientActionsService.getLoggedUserOrganization().get(), new HashMap<>());
                                    if (!rentalService.getRentedBooks()
                                            .get(clientActionsService.getLoggedUserOrganization().get())
                                            .containsKey(clientActionsService.getLoggedUser()))
                                        rentalService.getRentedBooks()
                                                .get(clientActionsService.getLoggedUserOrganization().get())
                                                .put(clientActionsService.getLoggedUser(), new ArrayList<>());
                                    rentalService.getRentedBooks()
                                            .get(clientActionsService.getLoggedUserOrganization().get())
                                            .get(clientActionsService.getLoggedUser()).add(book);
                                } else
                                    rentalService.getRentedBooks()
                                            .get(clientActionsService.getLoggedUserOrganization().get())
                                            .get(clientActionsService.getLoggedUser()).add(book);

                                library.getDepartments().get(
                                                clientActionsService.getLoggedUserOrganization().get().getMotherDepartment())
                                        .remove(book);
                                System.out.println("Wypo¿yczono ksi¹¿kê " + book + "u¿ytkownikowi "
                                        + clientActionsService.getLoggedUserOrganization().get());
                                reports.addBookLent(clientActionsService.getDepartmentActive());
                                reports.addBookBorrowedClient(
                                        (Client) clientActionsService.getLoggedUserOrganization().get(),
                                        clientActionsService.getDepartmentActive());
                                return true;
                            }
                        }
                        return false;
                    }
                }
            }
            System.out.println("Nie ma takiej ksi¹¿ki");

        }
        return false;

    }

    public boolean borrowDept(Library library) {
        Scanner bookScannerDept = new Scanner(System.in);

        String bookTitleDept = "";
        System.out.println("Proszê podaæ tytu³ ksi¹¿ki do wypo¿yczenia miêdzy oddzia³ami: ");
        bookTitleDept = bookScannerDept.nextLine();

        DepartmentType departmentTypeDept = clientActionsService.setDepartment(bookScannerDept);

        for (Book book : library.getDepartments().get(departmentTypeDept)) {
            if (bookTitleDept.equals(book.getTitle())) {
                book.setBorrowedFrom(clientActionsService.getLoggedUser().getMotherDepartment());
                library.getDepartments().get(clientActionsService.getLoggedUser().getMotherDepartment()).add(book);
                library.getDepartments().get(departmentTypeDept).remove(book);

                System.out.println("Wypo¿yczono ksi¹¿kê z oddzia³u " + departmentTypeDept + " do oddzia³u "
                        + clientActionsService.getLoggedUser().getMotherDepartment());
                reports.addBookBorrowedOtherDept(clientActionsService.getLoggedUser().getMotherDepartment());
                reports.addBookLentOtherDept(departmentTypeDept);
                return true;

            }
        }
        System.out.println("Nie ma takiej ksi¹¿ki");
        return false;

    }

    public boolean returnBook(Library library) {
        Scanner bookScannerReturn = new Scanner(System.in);
        double penaltySum = 0.0;
        HashMap<Client, HashMap<Client, List<Book>>> borrowedBooks = rentalService.getRentedBooks();

        if (clientActionsService.getLoggedUser() != null) {
            if (borrowedBooks.containsKey(clientActionsService.getLoggedUser())) {
                if (clientActionsService.getLoggedUser() instanceof Person
                        && borrowedBooks.get(clientActionsService.getLoggedUser())
                        .get(clientActionsService.getLoggedUser()).isEmpty()) {
                    System.out.println("Brak wypo¿yczonych ksi¹¿ek");
                    return false;
                } else if (clientActionsService.getLoggedUser() instanceof Organization && !borrowedBooks
                        .get(clientActionsService.getLoggedUser()).containsKey(clientActionsService.getLoggedUser())) {
                    int num = 0;
                    for (List<Book> books : borrowedBooks.get(clientActionsService.getLoggedUser()).values()) {
                        for (Book book : books) {
                            num++;
                        }
                    }
                    if (num == 0) {
                        System.out.println("Brak wypo¿yczonych ksi¹¿ek");
                        return false;
                    }
                } else if (clientActionsService.getLoggedUser() instanceof Organization
                        && borrowedBooks.get(clientActionsService.getLoggedUser())
                        .containsKey(clientActionsService.getLoggedUser())
                        && borrowedBooks.get(clientActionsService.getLoggedUser()).isEmpty()) {
                    int num = 0;
                    for (List<Book> books : borrowedBooks.get(clientActionsService.getLoggedUser()).values()) {
                        for (Book book : books) {
                            num++;
                        }
                    }
                    if (num == 0) {
                        System.out.println("Brak wypo¿yczonych ksi¹¿ek");
                        return false;
                    }
                } else {

                    System.out.println("Proszê podaæ tytu³ ksi¹¿ki do zwrócenia: ");
                    String bookTitle = bookScannerReturn.nextLine();

                    if (clientActionsService.getLoggedUser() instanceof Person) {
                        for (Book book : rentalService.getRentedBooks().get(clientActionsService.getLoggedUser())
                                .get(clientActionsService.getLoggedUser())) {
                            if (book.getTitle().equals(bookTitle)) {

                                penaltySum = penaltyCalc(bookScannerReturn, penaltySum, book);
                                rentalService.getRentedBooks().get(clientActionsService.getLoggedUser())
                                        .get(clientActionsService.getLoggedUser()).remove(book);
                                library.getDepartments().get(book.getBookDepartment()).add(book);
                                reports.booksReturnedLate(penaltySum, clientActionsService.getDepartmentActive());
                                reports.penaltyTotalDept(penaltySum, clientActionsService.getDepartmentActive());
                                System.out.println("Pomyœlnie zwrócono ksi¹¿kê");
                                return true;
                            }
                        }

                    } else {
                        for (List<Book> rentedBooks : rentalService.getRentedBooks()
                                .get(clientActionsService.getLoggedUser()).values()) {
                            for (Book bookM : rentedBooks) {
                                if (bookM.getTitle().equals(bookTitle)) {

                                    penaltySum = penaltyCalc(bookScannerReturn, penaltySum, bookM);
                                    rentedBooks.remove(bookM);
                                    library.getDepartments().get(bookM.getBookDepartment()).add(bookM);
                                    reports.booksReturnedLate(penaltySum, clientActionsService.getDepartmentActive());
                                    reports.penaltyTotalDept(penaltySum, clientActionsService.getDepartmentActive());
                                    System.out.println("Pomyœlnie zwrócono ksi¹¿kê wypo¿yczon¹ przez organizacjê");
                                    return true;
                                }
                            }

                        }
                    }

                }
                System.out.println("Nie ma takiej wypo¿yczonej ksi¹¿ki");
                return false;

            } else
                System.out.println("Nigdy nie wypo¿yczano ¿adnych ksi¹¿ek");
            return false;
        } else
            System.out.println("Brak logowania");
        return false;
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

    private double penaltyCalc(Scanner bookScannerReturn, double penaltySum, Book book) {
        int daysBorrowed;
        if (book.getBorrowedFrom() != clientActionsService.getDepartmentActive()
                && book.getBookDepartment() != clientActionsService.getDepartmentActive()) {
            penaltySum += 2.0;
            System.out.println(String.format("Naliczono op³atê za zwrot do innego oddzia³u w wysokoœci %s",
                    Util.plNumberFormat.format(2)));
        }

        System.out.println("Proszê podaæ przez ile dni by³a wypo¿yczona ksi¹¿ka: ");
        daysBorrowed = bookScannerReturn.nextInt();
        book.setDaysBorrowed(daysBorrowed);
        if (daysBorrowed > 14 && daysBorrowed < 22) {
            penaltySum += rentalService.penaltyAmountCalc(book);
        } else if (daysBorrowed > 21) {
            penaltySum += rentalService.penaltyAmountCalc(book);
            penaltySum += 5;
            System.out.println(
                    String.format("Naliczono karê za wys³anie monitu w wysokoœci %s", Util.plNumberFormat.format(5)));

        }
        System.out.println(String.format("£¹czna kara to %s", Util.plNumberFormat.format(penaltySum)));
        return penaltySum;
    }

    private double penaltyCalc(int daysBorrowed, double penaltySum, Book book) {
        if (book.getBorrowedFrom() != clientActionsService.getDepartmentActive()
                && book.getBookDepartment() != clientActionsService.getDepartmentActive()) {
            penaltySum += 2.0;
            System.out.println(String.format("Naliczono op³atê za zwrot do innego oddzia³u w wysokoœci %s",
                    Util.plNumberFormat.format(2)));
        }

        book.setDaysBorrowed(daysBorrowed);
        if (daysBorrowed > 14 && daysBorrowed < 22) {
            penaltySum += rentalService.penaltyAmountCalc(book);
        } else if (daysBorrowed > 21) {
            penaltySum += rentalService.penaltyAmountCalc(book);
            penaltySum += 5;
            System.out.println(
                    String.format("Naliczono karê za wys³anie monitu w wysokoœci %s", Util.plNumberFormat.format(5)));

        }
        System.out.println(String.format("£¹czna kara to %s", Util.plNumberFormat.format(penaltySum)));
        return penaltySum;
    }

}
