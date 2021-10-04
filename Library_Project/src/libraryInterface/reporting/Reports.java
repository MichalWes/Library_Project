package libraryInterface.reporting;

import java.util.HashMap;

import model.client.Client;
import model.library.DepartmentType;

public class Reports {
	private HashMap<DepartmentType, DepartmentReport> libraryReport = new HashMap<>();

	public HashMap<DepartmentType, DepartmentReport> getLibraryReport() {
		return libraryReport;
	}

	public void penaltyTotalDept(Double penalty, DepartmentType departmentActive) {
		if (penalty > 0) {
			libraryReport.get(departmentActive)
					.setTotalFines(libraryReport.get(departmentActive).getTotalFines() + penalty);
		}
	}

	public void booksReturnedLate(Double penalty, DepartmentType departmentActive) {
		if (penalty > 0) {
			libraryReport.get(departmentActive)
					.setLateBooksReturned(libraryReport.get(departmentActive).getLateBooksReturned() + 1);
		}
	}

	public void addBookBorrowedOtherDept(DepartmentType departmentActive) {
		libraryReport.get(departmentActive).setNumberOfBooksBorrowedOtherDept(
				libraryReport.get(departmentActive).getNumberOfBooksBorrowedOtherDept() + 1);
	}

	public void addBookLentOtherDept(DepartmentType departmentLent) {
		libraryReport.get(departmentLent)
				.setNumberOfBooksLentOtherDept(libraryReport.get(departmentLent).getNumberOfBooksLentOtherDept() + 1);
	}

	public void addBookBorrowedClient(Client user, DepartmentType departmentActive) {
		if (!libraryReport.get(departmentActive)
				.getNumberOfBooksBorrowedClient().containsKey(user)) {
			libraryReport.get(departmentActive).getNumberOfBooksBorrowedClient()
					.put(user, 1);
		} else {
			libraryReport.get(departmentActive).getNumberOfBooksBorrowedClient()
					.put(user, libraryReport.get(departmentActive)
							.getNumberOfBooksBorrowedClient().get(user) + 1);
		}

	}

	public void addBookLent(DepartmentType departmentActive) {
		libraryReport.get(departmentActive).setNumberOfBooksLent(
				libraryReport.get(departmentActive).getNumberOfBooksLent() + 1);
	}

}
