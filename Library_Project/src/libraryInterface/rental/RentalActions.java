package libraryInterface.rental;

public enum RentalActions {
	BORROW("Wypo¿ycz ksi¹¿kê"),
	BORROW_DEPT("Wypo¿ycz ksi¹¿kê z innego oddzia³u"),
	RETURN("Zwróæ ksi¹¿kê"),
	SHOW_ALL("Poka¿ wszystkie ksi¹¿ki"),
	SHOW_FILTERED("Poka¿ ksi¹¿ki z uwzglêdnieniem filtra"),
	SHOW_RENTED_BOOKS("Poka¿ wypo¿yczone ksi¹¿ki"),
	USER_MANAGEMENT("Wróc do ekranu zarz¹dzania u¿ytkownikiem"),
	GENERATE_REPORT("Wygeneruj raport dla aktywnego departamentu"),
	EXIT_LIBRARY("WyjdŸ z biblioteki"),
	ILLEGAL("Niepoprawne polecenie - proszê spróbowaæ ponownie");
	
	
	private String action;

	private RentalActions(String action) {
		this.action = action;
	}

	public String getAction() {
		return action;
	}
	
	
	
	
}
