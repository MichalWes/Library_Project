package libraryInterface.client;

public enum ClientActions {
	
	LOGON_NAME("Zaloguj siê poprzez imiê i nazwisko"),
	LOGON_ORGANIZATION("Zaloguj siê poprzez nazwê organizacji"),
	LOGON_ID("Zaloguj siê poprzez podanie nr klienta"),
	CHANGE_DEPARTMENT("Skorzystaj z innego departamentu"),
	SHOW_ALL_CLIENTS("Poka¿ u¿ytkowników zapisanych do biblioteki"),
	ILLEGAL("Niepoprawne polecenie - proszê spróbowaæ ponownie");

	private String action;

	private ClientActions(String action) {
		this.action = action;
	}

	public String getAction() {
		return action;
	}
	
	

}
