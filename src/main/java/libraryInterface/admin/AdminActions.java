package libraryInterface.admin;

public enum AdminActions {
    GENERATE_BOOKS("Wygeneruj ksi¹¿ki z generatora"),
    READ_FILE("Wczytaj ksi¹¿ki z pliku"),
    WRITE_FILE("Zapisz ksi¹¿ki do pliku"),
    WRITE_POSTGRE("Zapisz ksi¹¿ki do bazy danych PostgreSQL"),
    WRITE_MYSQL("Zapisz ksi¹¿ki do bazy danych MySQL"),
    ILLEGAL("Niepoprawne polecenie - proszê spróbowaæ ponownie");

    private String action;

    private AdminActions(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }
}
