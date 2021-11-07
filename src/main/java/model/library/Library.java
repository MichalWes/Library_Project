package model.library;

import model.library.book.Book;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Library {
	private HashMap<DepartmentType, List<Book>> departments;

	public Library() {
		this.departments = new HashMap<>();
		List<Book> departmentA = new ArrayList<>();
		List<Book> departmentB = new ArrayList<>();
		List<Book> departmentC = new ArrayList<>();
		departments.put(DepartmentType.A, departmentA);
		departments.put(DepartmentType.B, departmentB);
		departments.put(DepartmentType.C, departmentC);
	}

	public void addBooksToLibrary(List<Book> ksiazki) {
		for (Book book : ksiazki) {
			if(book.getBookDepartment().equals(DepartmentType.A)) {
				departments.get(DepartmentType.A).add(book);
			}else if (book.getBookDepartment().equals(DepartmentType.B)) {
				departments.get(DepartmentType.B).add(book);
			}else if (book.getBookDepartment().equals(DepartmentType.C)){
				departments.get(DepartmentType.C).add(book);
			}

		}
		ksiazki.removeAll(ksiazki);
	}

	public HashMap<DepartmentType, List<Book>> getDepartments() {
		return departments;
	}

	
	
	
	
	
	
}
