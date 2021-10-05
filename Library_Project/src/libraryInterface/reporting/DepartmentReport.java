package libraryInterface.reporting;

import java.util.HashMap;
import java.util.Objects;

import model.client.Client;
import model.library.DepartmentType;
import util.Util;

public class DepartmentReport {
	private DepartmentType department;
	private int numberOfBooksLent;
	private int numberOfBooksLentOtherDept;
	private int numberOfBooksBorrowedOtherDept;
	private HashMap<Client, Integer> numberOfBooksBorrowedClient;
	private int lateBooksReturned;
	private double totalFines;
	
	public DepartmentReport(DepartmentType department) {
		this.department = department;
		this.numberOfBooksBorrowedClient = new HashMap<>(); 
	}

	public DepartmentType getDepartment() {
		return department;
	}
	
	public int getNumberOfBooksLent() {
		return numberOfBooksLent;
	}
	public void setNumberOfBooksLent(int numberOfBooksLent) {
		this.numberOfBooksLent = numberOfBooksLent;
	}
	public int getNumberOfBooksLentOtherDept() {
		return numberOfBooksLentOtherDept;
	}
	public void setNumberOfBooksLentOtherDept(int numberOfBooksLentOtherDept) {
		this.numberOfBooksLentOtherDept = numberOfBooksLentOtherDept;
	}
	public int getNumberOfBooksBorrowedOtherDept() {
		return numberOfBooksBorrowedOtherDept;
	}
	public void setNumberOfBooksBorrowedOtherDept(int numberOfBooksBorrowedOtherDept) {
		this.numberOfBooksBorrowedOtherDept = numberOfBooksBorrowedOtherDept;
	}
	public HashMap<Client, Integer> getNumberOfBooksBorrowedClient() {
		return numberOfBooksBorrowedClient;
	}
	public void setNumberOfBooksBorrowedClient(HashMap<Client, Integer> numberOfBooksBorrowedClient) {
		this.numberOfBooksBorrowedClient = numberOfBooksBorrowedClient;
	}
	public int getLateBooksReturned() {
		return lateBooksReturned;
	}
	public void setLateBooksReturned(int lateBooksReturned) {
		this.lateBooksReturned = lateBooksReturned;
	}
	public double getTotalFines() {
		return totalFines;
	}
	public void setTotalFines(double totalFines) {
		this.totalFines = totalFines;
	}

	@Override
	public int hashCode() {
		return Objects.hash(department);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DepartmentReport other = (DepartmentReport) obj;
		return department == other.department;
	}

	@Override
	public String toString() {
		return "DepartmentReport [" + (department != null ? "department=" + department + ", " : "")
				+ "numberOfBooksLent=" + numberOfBooksLent + ", numberOfBooksLentOtherDept="
				+ numberOfBooksLentOtherDept + ", numberOfBooksBorrowedOtherDept=" + numberOfBooksBorrowedOtherDept
				+ ", "
				+ (numberOfBooksBorrowedClient != null
						? "numberOfBooksBorrowedClient=" + numberOfBooksBorrowedClient + ", "
						: "")
				+ "lateBooksReturned=" + lateBooksReturned + ", totalFines=" + Util.plNumberFormat.format(totalFines) + "]";
	}
	

}
