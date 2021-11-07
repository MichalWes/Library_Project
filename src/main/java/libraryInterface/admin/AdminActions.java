package libraryInterface.admin;

public enum AdminActions {
    GENERATE_BOOKS("Wygeneruj ksi��ki z generatora"),
    READ_FILE("Wczytaj ksi��ki z pliku"),
    WRITE_FILE("Zapisz ksi��ki do pliku"),
    WRITE_POSTGRE("Zapisz ksi��ki do bazy danych PostgreSQL"),
    WRITE_MYSQL("Zapisz ksi��ki do bazy danych MySQL"),
    ILLEGAL("Niepoprawne polecenie - prosz� spr�bowa� ponownie");

    private String action;

    private AdminActions(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }
}
