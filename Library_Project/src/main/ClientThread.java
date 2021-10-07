package main;

import java.util.List;

import libraryInterface.LibraryActionsService;
import model.client.Client;
import model.library.DepartmentType;
import model.library.Library;

public class ClientThread implements Runnable {

	private Client loggedClient;
	private DepartmentType departmentActive;
	private Library library;
	private LibraryActionsService libraryActionsService;
	private List<String> booksToBorrow;
	private List<String> booksToReturn;
	private int daysBorrowed;

	

	public ClientThread(Client loggedClient, DepartmentType departmentActive, Library library,
			LibraryActionsService libraryActionsService, List<String> booksToBorrow, List<String> booksToReturn,
			int daysBorrowed) {
		super();
		this.loggedClient = loggedClient;
		this.departmentActive = departmentActive;
		this.library = library;
		this.libraryActionsService = libraryActionsService;
		this.booksToBorrow = booksToBorrow;
		this.booksToReturn = booksToReturn;
		this.daysBorrowed = daysBorrowed;
	}

	@Override
	public void run() {

		while (!booksToBorrow.isEmpty()) {
			libraryActionsService.borrow(library, booksToBorrow.remove(booksToBorrow.size()-1),loggedClient, departmentActive);
		}
		while (!booksToReturn.isEmpty()) {
			libraryActionsService.returnBook(library, booksToReturn.remove(booksToBorrow.size()-1), daysBorrowed);
		}
	}
}
