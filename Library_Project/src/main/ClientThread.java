package main;

import java.util.Deque;
import java.util.List;
import java.util.Optional;

import libraryInterface.LibraryActionsService;
import libraryInterface.client.ClientActionsService;
import model.client.Client;
import model.library.DepartmentType;
import model.library.Library;

public class ClientThread implements Runnable {

	private Library library;
	private ClientActionsService clientActionsService;
	private LibraryActionsService libraryActionsService;
	private List<Client> bazaKlientow;
	private DepartmentType departmentType;
	private Deque<String> booksToBorrow;
	private Deque<String> booksToReturn;
	private int clientId;
	private int daysBorrowed;

	public ClientThread(Library library, ClientActionsService clientActionsService,
			LibraryActionsService libraryActionsService, List<Client> bazaKlientow, DepartmentType departmentType,
			Deque<String> booksToBorrow, Deque<String> booksToReturn, int clientId, int daysBorrowed) {
		super();
		this.library = library;
		this.clientActionsService = clientActionsService;
		this.libraryActionsService = libraryActionsService;
		this.bazaKlientow = bazaKlientow;
		this.departmentType = departmentType;
		this.booksToBorrow = booksToBorrow;
		this.booksToReturn = booksToReturn;
		this.clientId = clientId;
		this.daysBorrowed = daysBorrowed;
	}

	@Override
	public void run() {
		clientActionsService.setDepartmentActive(departmentType);
		Optional<Client> clientFound = bazaKlientow.stream().filter(client -> client.getId() == clientId).findFirst();
		clientActionsService.setLoggedUser(clientFound.get());
		System.out.println(clientActionsService.getLoggedUser());
		while (!booksToBorrow.isEmpty()) {
			libraryActionsService.borrow(library, booksToBorrow.pollFirst());
		}
		while (!booksToReturn.isEmpty()) {
			libraryActionsService.returnBook(library, booksToReturn.pollFirst(), daysBorrowed);
		}
	}
}
