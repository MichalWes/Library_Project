package libraryInterface.rental;

public enum RentalActions {
	BORROW("Wypo�ycz ksi��k�"),
	BORROW_DEPT("Wypo�ycz ksi��k� z innego oddzia�u"),
	RETURN("Zwr�� ksi��k�"),
	SHOW_ALL("Poka� wszystkie ksi��ki"),
	SHOW_FILTERED("Poka� ksi��ki z uwzgl�dnieniem filtra"),
	SHOW_RENTED_BOOKS("Poka� wypo�yczone ksi��ki"),
	USER_MANAGEMENT("Wr�c do ekranu zarz�dzania u�ytkownikiem"),
	GENERATE_REPORT("Wygeneruj raport dla aktywnego departamentu"),
	EXIT_LIBRARY("Wyjd� z biblioteki"),
	ILLEGAL("Niepoprawne polecenie - prosz� spr�bowa� ponownie");
	
	
	private String action;

	private RentalActions(String action) {
		this.action = action;
	}

	public String getAction() {
		return action;
	}
	
	
	
	
}
