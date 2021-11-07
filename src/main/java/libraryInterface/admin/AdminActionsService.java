package libraryInterface.admin;

import libraryInterface.rental.RentalActions;
import model.library.Library;

import java.util.Scanner;

public class AdminActionsService {

    public AdminActions availableAdminActions(Scanner in) {
        String actionStr = "";
        AdminActions action;
        System.out.println("Do wyboru: " + AdminActions.GENERATE_BOOKS + " (" + AdminActions.GENERATE_BOOKS.getAction()
                + ") | " + AdminActions.READ_FILE + " (" + AdminActions.READ_FILE.getAction()
                + ") | " + AdminActions.WRITE_FILE + " (" + AdminActions.WRITE_FILE.getAction() + ") | " + "\n"
                + AdminActions.WRITE_POSTGRE + " (" + AdminActions.WRITE_POSTGRE.getAction()
                + ") | " + AdminActions.WRITE_MYSQL + " (" + AdminActions.WRITE_MYSQL.getAction() + ") | "
                + AdminActions.BACK_PRV + " (" + AdminActions.BACK_PRV.getAction() + ")");

        try {
            actionStr = in.next().toUpperCase();
        } catch (Exception e) {
            System.out.println("B³¹d odczytu");
            e.printStackTrace();
        }

        try {
            action = AdminActions.valueOf(actionStr);

        } catch (Exception e1) {
            action = AdminActions.ILLEGAL;
        }
        return action;
    }

    public void adminActions(Library library, Scanner in) {

        AdminActions action;
        administrator:
        while (true) {
            action = availableAdminActions(in);

            switch (action) {
                case GENERATE_BOOKS:
                    var bookGenerator = new BookGenerator();
                    library.addBooksToLibrary(bookGenerator.generateBooks());
                    System.out.println("Pomyœlnie dodano wygenerowane ksi¹¿ki do biblioteki");
                    break;

                case READ_FILE:
                    var fileOperationsRead = new FileOperations();
                    library.addBooksToLibrary(fileOperationsRead.readLibraryFromFile());
                    System.out.println("Pomyœlnie dodano odczytane z pliku ksi¹¿ki do biblioteki");
                    break;

                case WRITE_FILE:
                    var fileOperationsWrite = new FileOperations();
                    fileOperationsWrite.writeLibrarytoFile(library);

                    break;

                case WRITE_POSTGRE:
                    var databaseOperationsPostgre = new DatabaseOperations();
                    databaseOperationsPostgre.writePostgreSql(library);
                    break;

                case WRITE_MYSQL:
                    var databaseOperationsMySql = new DatabaseOperations();
                    databaseOperationsMySql.writeMySql(library);
                    break;

                case BACK_PRV:
                    break administrator;

                case ILLEGAL:
                    System.out.println(RentalActions.ILLEGAL.getAction());

                default:
                    break;
            }

        }

    }


}
