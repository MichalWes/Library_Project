package libraryInterface.filter;

public enum FilterType {
	AUTHOR("Filtruj poprzez wyb�r autora"),
	GENRE("Filtruj poprzez wyb�r gatunku/dzia�u"),
	CATEGORY("Filtruj wg kategorii"),
	DESCRIPTION("Filtruj wg opisu"),
	ILLEGAL("Niepoprawne polecenie - prosz� spr�bowa� ponownie");
	
	private String action;

	private FilterType(String action) {
		this.action = action;
	}

	public String getAction() {
		return action;
	}
}
