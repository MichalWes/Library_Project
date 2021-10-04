package model.library;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import model.library.book.Book;

public class Library {
	private HashMap<DepartmentType, List<Book>> departments;

	public Library(List<Book> ksiazki) {
		this.departments = new HashMap<>();
		List<Book> departmentA = new ArrayList<>();
		List<Book> departmentB = new ArrayList<>();
		List<Book> departmentC = new ArrayList<>();
		
		for (Book book : ksiazki) {
			if(book.getBookDepartment().equals(DepartmentType.A)) {
				departmentA.add(book);
			}else if (book.getBookDepartment().equals(DepartmentType.B)) {
				departmentB.add(book);
			}else if (book.getBookDepartment().equals(DepartmentType.C)){
				departmentC.add(book);
			}
		departments.put(DepartmentType.A, departmentA);
		departments.put(DepartmentType.B, departmentB);
		departments.put(DepartmentType.C, departmentC);
		}
		ksiazki.removeAll(ksiazki);
	}

	public HashMap<DepartmentType, List<Book>> getDepartments() {
		return departments;
	}

	
	
	
	
	
	
}
