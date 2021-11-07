package model.library;

import model.library.book.Book;

import java.util.ArrayList;
import java.util.List;

public class Department {
	private String name;
	private List<Book> departmentBookList;
	
	public Department(DepartmentType departmentType, List<Book> library) {
		this.name =  "Department " + departmentType;
		this.departmentBookList = new ArrayList<>();
		for (Book book : library) {
			if (book.getBookDepartment().equals(departmentType)) {
				departmentBookList.add(book);
			}
		}
	}

	public String getName() {
		return name;
	}

	public List<Book> getDepartmentBookList() {
		return departmentBookList;
	}
	
	
	
}
