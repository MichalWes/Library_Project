package libraryInterface;

import libraryInterface.admin.AdminActionsService;
import libraryInterface.client.ClientActions;
import libraryInterface.client.ClientActionsService;
import libraryInterface.rental.RentalActions;
import model.library.Library;

import java.util.Scanner;

public class LibraryInterface {

    private Library library;
    private LibraryActionsService libraryActionsService;
    private ClientActionsService clientActionsService;
    private AdminActionsService adminActionsService;

    public LibraryInterface(Library library, LibraryActionsService libraryActionsService,
                            ClientActionsService clientActionsService, AdminActionsService adminActionsService) {
        this.library = library;
        this.libraryActionsService = libraryActionsService;
        this.clientActionsService = clientActionsService;
        this.adminActionsService = adminActionsService;
    }

    public void workWithLibrary() {
        Scanner in = new Scanner(System.in);
        System.out.println("Witamy serdecznie w bibliotece!!!!!");
        System.out.println("Prosimy o identyfikacjê:");
        clientActions(library, in);
        libraryActions(library, in);
    }

    private void clientActions(Library library, Scanner in) {

        ClientActions action;
        powitalny:
        while (true) {
            action = clientActionsService.availableClientActions(in);

            switch (action) {
                case LOGON_NAME:

                    if (clientActionsService.logonName(in)) {
                        break powitalny;
                    } else
                        break;

                case LOGON_ORGANIZATION:

                    if (clientActionsService.logonOrganization(in)) {
                        break powitalny;
                    } else
                        break;

                case LOGON_ID:

                    if (clientActionsService.logonId(in)) {
                        break powitalny;
                    } else
                        break;

                case CHANGE_DEPARTMENT:
                    clientActionsService.setDepartmentActive(clientActionsService.setDepartment(in));
                    break powitalny;

                case SHOW_ALL_CLIENTS:
                    clientActionsService.showAllClients();
                    break;

                case LIBRARY_ADMIN:
                    adminActionsService.adminActions(library, in);
                    break;

                case ILLEGAL:
                    System.out.println(ClientActions.ILLEGAL.getAction());
                    break;

                default:
                    break;
            }
        }

    }

    private void libraryActions(Library library, Scanner in) {

        RentalActions action;
        biblioteka:
        while (true) {
            action = libraryActionsService.availableLibraryActions(in);

            switch (action) {
                case BORROW:
                    libraryActionsService.borrow(library);
                    break;

                case BORROW_DEPT:
                    libraryActionsService.borrowDept(library);
                    break;

                case RETURN:
                    libraryActionsService.returnBook(library);
                    break;

                case SHOW_ALL:
                    libraryActionsService.showLibrary(library);
                    break;

                case SHOW_FILTERED:
                    libraryActionsService.getFilter().showFiltered(library, in);
                    break;

                case SHOW_RENTED_BOOKS:
                    libraryActionsService.showRented(clientActionsService.getLoggedUser());
                    break;

                case GENERATE_REPORT:
                    System.out.println(libraryActionsService.getReports().getLibraryReport()
                            .get(clientActionsService.getDepartmentActive()));
                    break;

                case USER_MANAGEMENT:
                    clientActions(library, in);
                    break;

                case EXIT_LIBRARY:
                    System.out.println("Dziêkujemy za skorzystanie z naszych us³ug. Do widzenia!");
                    break biblioteka;

                case ILLEGAL:
                    System.out.println(RentalActions.ILLEGAL.getAction());

                default:
                    break;
            }

        }

    }



}
