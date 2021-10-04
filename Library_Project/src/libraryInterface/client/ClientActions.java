package libraryInterface.client;

public enum ClientActions {
	
	LOGON_NAME("Zaloguj si� poprzez imi� i nazwisko"),
	LOGON_ORGANIZATION("Zaloguj si� poprzez nazw� organizacji"),
	LOGON_ID("Zaloguj si� poprzez podanie nr klienta"),
	CHANGE_DEPARTMENT("Skorzystaj z innego departamentu"),
	SHOW_ALL_CLIENTS("Poka� u�ytkownik�w zapisanych do biblioteki"),
	ILLEGAL("Niepoprawne polecenie - prosz� spr�bowa� ponownie");

	private String action;

	private ClientActions(String action) {
		this.action = action;
	}

	public String getAction() {
		return action;
	}
	
	

}
