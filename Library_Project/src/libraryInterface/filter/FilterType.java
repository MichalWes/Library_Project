package libraryInterface.filter;

public enum FilterType {
	AUTHOR("Filtruj poprzez wybór autora"),
	GENRE("Filtruj poprzez wybór gatunku/dzia³u"),
	CATEGORY("Filtruj wg kategorii"),
	DESCRIPTION("Filtruj wg opisu"),
	ILLEGAL("Niepoprawne polecenie - proszê spróbowaæ ponownie");
	
	private String action;

	private FilterType(String action) {
		this.action = action;
	}

	public String getAction() {
		return action;
	}
}
