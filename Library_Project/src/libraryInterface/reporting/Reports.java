package libraryInterface.reporting;

import java.util.HashMap;

import model.library.DepartmentType;

public class Reports {
	private HashMap<DepartmentType, DepartmentReport> libraryReport = new HashMap<>();

	public HashMap<DepartmentType, DepartmentReport> getLibraryReport() {
		return libraryReport;
	}
	
}
