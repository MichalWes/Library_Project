package libraryInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import libraryInterface.rental.RentalActions;
import model.client.Client;
import model.client.Organization;
import model.client.Person;
import model.library.DepartmentType;
import model.library.Library;
import model.library.book.Book;

public class LibraryInterface {

	private final Library library;
	private final List<Client> bazaKlientow;
	private LibraryActionsService libraryActionsService;
	private Client loggedUser;
	private Organization loggedUserOrganization;
	private DepartmentType departmentActive;
	private boolean breakPowitalny;

	public LibraryInterface(Library library, List<Client> bazaKlientow, LibraryActionsService libraryActionsService) {
		this.library = library;
		this.bazaKlientow = bazaKlientow;
		this.libraryActionsService = libraryActionsService;
	}

	public void workWithLibrary() {
		Scanner in = new Scanner(System.in);
		System.out.println("Witamy serdecznie w bibliotece!!!!!");

		System.out.println("Prosimy o identyfikacjê:");
		clientActions(library, in);
		libraryActions(library, in);
	}

	private void clientActions(Library library, Scanner in) {
		ClientActions action;
		while (true) {
			action = availableClientActions(in);

			switch (action) {
			case LOGON_NAME:
				logonName(in);
				break;

			case LOGON_ORGANIZATION:
				logonOrganization(in);
				break;

			case LOGON_ID:
				logonId(in);

				break;
			case CHANGE_DEPARTMENT:
				departmentActive = setDepartment(in);
				libraryActions(library, in);
				break;
			case SHOW_ALL_CLIENTS:
				for (Client client : bazaKlientow) {
					System.out.println(client);
				}
				System.out.println();
				break;

			case ILLEGAL:
				System.out.println(ClientActions.ILLEGAL.getAction());
				break;

			default:
				break;
			}
			if (breakPowitalny)
				break;

		}
	}

	private void logonId(Scanner in) {
		System.out.println("Proszê podaæ ID: ");
		int clientId = in.nextInt();
		for (Client client : bazaKlientow) {
			if (client.getId() == clientId) {
				if (client.equals(loggedUser)) {
					System.out.println("Jest ju¿ Pani/Pan/Organizacja zalogowana/y");
					breakPowitalny = true;
				}
				departmentActive = setDepartment(in);
				loggedUser = client;
				if (client instanceof Organization) {
					Organization organization = (Organization) client;
					System.out.println("Witaj " + organization.getOrganizationName() + "!!!");
					loggedUserOrganization = null;
				} else {
					Person person = (Person) client;
					System.out.println("Witaj " + person.getName() + " " + person.getSurName() + "!!!");
					organizationCheck(person);
				}

				System.out.println("Poprawnie zalogowano do biblioteki");
				breakPowitalny = true;
			}
		}
		if (!breakPowitalny) {
			System.out.println("Nie znaleziono u¿ytkownika, proszê spróbowaæ ponownie.");
		}

	}

	private void logonOrganization(Scanner in) {
		System.out.println("Proszê podaæ nazwê organizacji: ");
		String organizationName = in.next();
		for (Client client : bazaKlientow) {
			if (client instanceof Organization) {
				Organization organization = (Organization) client;
				if (organization.getOrganizationName().equals(organizationName)) {
					if (client.equals(loggedUser)) {
						System.out.println("Organizacja jest ju¿ zalogowana");
						breakPowitalny = true;
						break;
					}
					departmentActive = setDepartment(in);
					loggedUser = organization;
					System.out.println("Witaj " + organization.getOrganizationName() + "!!!");
					loggedUserOrganization = null;
					System.out.println("Poprawnie zalogowano do biblioteki");
					breakPowitalny = true;
					break;
				}
			}
		}
		if (!breakPowitalny) {
			System.out.println("Nie znaleziono u¿ytkownika, proszê spróbowaæ ponownie.");
		}

	}

	private void logonName(Scanner in) {
		System.out.println("Proszê podaæ imiê: ");
		String clientName = in.next();
		System.out.println("Proszê podaæ nazwisko: ");
		String clientSurname = in.next();
		for (Client client : bazaKlientow) {
			if (client instanceof Person) {
				Person person = (Person) client;
				if (person.getName().equals(clientName) && person.getSurName().equals(clientSurname)) {
					if (client.equals(loggedUser)) {
						System.out.println("Jest ju¿ Pani/Pan zalogowana/y");
						breakPowitalny = true;
					}
					departmentActive = setDepartment(in);
					loggedUser = person;
					System.out.println("Witaj " + person.getName() + " " + person.getSurName() + "!!!");
					organizationCheck(person);
					System.out.println("Poprawnie zalogowano do biblioteki");
					breakPowitalny = true;
				}
			}
		}
		if (!breakPowitalny) {
			System.out.println("Nie znaleziono u¿ytkownika, proszê spróbowaæ ponownie.");
		}

	}

	private void organizationCheck(Person person) {
		if (person.isInOrganization()) {
			for (Client clientOrg : bazaKlientow) {
				if (clientOrg instanceof Organization) {
					if (((Organization) clientOrg).getMembers().contains(person)) {
						loggedUserOrganization = (Organization) clientOrg;
					}
				}
			}
		} else
			loggedUserOrganization = null;

		if (person.isInOrganization()) {
			System.out.println("Poniewa¿ nale¿ysz do organizacji " + loggedUserOrganization.getOrganizationName()
					+ " jesteœ zalogowana/y te¿ w jej imieniu");
		}
	}

	private ClientActions availableClientActions(Scanner in) {
		String actionStr;
		ClientActions action;
		System.out.println("Do wyboru: " + ClientActions.LOGON_NAME + " (" + ClientActions.LOGON_NAME.getAction()
				+ ") | " + ClientActions.LOGON_ORGANIZATION + " (" + ClientActions.LOGON_ORGANIZATION.getAction()
				+ ") | " + ClientActions.LOGON_ID + " (" + ClientActions.LOGON_ID.getAction() + ") | " + "\n"
				+ ClientActions.CHANGE_DEPARTMENT + " (" + ClientActions.CHANGE_DEPARTMENT.getAction() + ") | "
				+ ClientActions.SHOW_ALL_CLIENTS + " (" + ClientActions.SHOW_ALL_CLIENTS.getAction() + ")");

		actionStr = in.next().toUpperCase();
		try {
			action = ClientActions.valueOf(actionStr);

		} catch (Exception e) {
			action = ClientActions.ILLEGAL;
		}
		return action;
	}

	private void libraryActions(Library library, Scanner in) {
		RentalActions action;
		biblioteka: while (true) {
			action = libraryActionsService.availableLibraryActions(in);

			pozyczanie: switch (action) {
			case BORROW:
				if (loggedUser.getMotherDepartment() != departmentActive) {
					System.out.println(
							"W tym oddziale nie mo¿na wypo¿yczaæ ksi¹¿ek, proszê o skorzystanie z dzia³u macierzystego");
					break;
				} else {

					Scanner bookScanner = new Scanner(System.in);
					System.out.println("Proszê podaæ tytu³ ksi¹¿ki do wypo¿yczenia: ");
					String bookTitle = "";

					bookTitle = bookScanner.nextLine();
					bookScanner = null;

					for (Book book : library.getDepartments().get(loggedUser.getMotherDepartment())) {
						if (bookTitle.equals(book.getTitle())) {
							if (loggedUserOrganization == null && loggedUser instanceof Person) {
								if (libraryActionsService.getRentalService().clientBorrowConditionsChecker(
										(Person) loggedUser, libraryActionsService.getRentalService(), book)) {
									libraryActionsService.borrowBook(library, book, loggedUser);
									System.out.println("Wypo¿yczono ksi¹¿kê " + book + "u¿ytkownikowi " + loggedUser);
									addBookLent();
									addBookBorrowedClient(loggedUser);
									break pozyczanie;
								} else
									break pozyczanie;
							} else if (loggedUserOrganization == null && loggedUser instanceof Organization) {
								if (libraryActionsService.getRentalService().clientBorrowConditionsChecker(
										(Organization) loggedUser, libraryActionsService.getRentalService(), book)) {
									libraryActionsService.borrowBook(library, book, loggedUser);
									System.out.println("Wypo¿yczono ksi¹¿kê " + book + "u¿ytkownikowi " + loggedUser);
									addBookLent();
									addBookBorrowedClient(loggedUser);
									break pozyczanie;
								} else
									break pozyczanie;
							} else {
								if (libraryActionsService.getRentalService().clientBorrowConditionsChecker(
										(Person) loggedUser, libraryActionsService.getRentalService(), book)) {
									libraryActionsService.borrowBook(library, book, loggedUser);
									System.out.println("Wypo¿yczono ksi¹¿kê " + book + "u¿ytkownikowi " + loggedUser);
									addBookLent();
									addBookBorrowedClient(loggedUser);
									break pozyczanie;
								} else {
									System.out.println(
											"Poniewa¿ jesteœ cz³onkiem organizacji, jest mo¿liwoœæ wypo¿yczenia ksi¹¿ki na organizacjê");
									System.out.println("Trwa sprawdzanie czy takie wypo¿ycznie jest mo¿liwe");
									if (libraryActionsService.getRentalService().clientBorrowConditionsChecker(
											(Organization) loggedUserOrganization,
											libraryActionsService.getRentalService(), book)
											&& libraryActionsService.getRentalService().clientBorrowConditionsChecker(
													(Person) loggedUser, loggedUserOrganization,
													libraryActionsService.getRentalService(), book)) {
										if (!libraryActionsService.getRentalService().getRentedBooks()
												.containsKey(loggedUserOrganization)) {
											libraryActionsService.getRentalService().getRentedBooks()
													.put(loggedUserOrganization, new HashMap<>());
											if (!libraryActionsService.getRentalService().getRentedBooks()
													.get(loggedUserOrganization).containsKey(loggedUser))
												libraryActionsService.getRentalService().getRentedBooks()
														.get(loggedUserOrganization).put(loggedUser, new ArrayList<>());
											libraryActionsService.getRentalService().getRentedBooks()
													.get(loggedUserOrganization).get(loggedUser).add(book);
										} else
											libraryActionsService.getRentalService().getRentedBooks()
													.get(loggedUserOrganization).get(loggedUser).add(book);

										library.getDepartments().get(loggedUserOrganization.getMotherDepartment())
												.remove(book);
										System.out.println("Wypo¿yczono ksi¹¿kê " + book + "u¿ytkownikowi "
												+ loggedUserOrganization);
										addBookLent();
										addBookBorrowedClient(loggedUserOrganization);
										break pozyczanie;
									}
								}
								break pozyczanie;
							}
						}
					}
					System.out.println("Nie ma takiej ksi¹¿ki");
					break;
				}

			case BORROW_DEPT:
				Scanner bookScannerDept = new Scanner(System.in);

				String bookTitleDept = "";
				System.out.println("Proszê podaæ tytu³ ksi¹¿ki do wypo¿yczenia miêdzy oddzia³ami: ");
				bookTitleDept = bookScannerDept.nextLine();

				DepartmentType departmentTypeDept = setDepartment(bookScannerDept);

				for (Book book : library.getDepartments().get(departmentTypeDept)) {
					if (bookTitleDept.equals(book.getTitle())) {
						book.setBorrowedFrom(loggedUser.getMotherDepartment());
						library.getDepartments().get(loggedUser.getMotherDepartment()).add(book);
						library.getDepartments().get(departmentTypeDept).remove(book);

						System.out.println("Wypo¿yczono ksi¹¿kê z oddzia³u " + departmentTypeDept + " do oddzia³u "
								+ loggedUser.getMotherDepartment());
						addBookBorrowedOtherDept();
						addBookLentOtherDept(departmentTypeDept);
						break pozyczanie;
					}
				}
				System.out.println("Nie ma takiej ksi¹¿ki");
				break;

			case RETURN:
				Scanner bookScannerReturn = new Scanner(System.in);
				double penaltySum = 0.0;
				HashMap<Client, HashMap<Client, List<Book>>> borrowedBooks = libraryActionsService.getRentalService()
						.getRentedBooks();

				if (loggedUser != null) {
					if (borrowedBooks.containsKey(loggedUser)) {
						if (loggedUser instanceof Person && borrowedBooks.get(loggedUser).get(loggedUser).isEmpty()) {
							System.out.println("Brak wypo¿yczonych ksi¹¿ek");
							break;
						} else if (loggedUser instanceof Organization
								&& !borrowedBooks.get(loggedUser).containsKey(loggedUser)) {
							int num = 0;
							for (List<Book> books : borrowedBooks.get(loggedUser).values()) {
								for (Book book : books) {
									num++;
								}
							}
							if (num == 0) {
								System.out.println("Brak wypo¿yczonych ksi¹¿ek");
								break;
							}
						} else if (loggedUser instanceof Organization
								&& borrowedBooks.get(loggedUser).containsKey(loggedUser)
								&& borrowedBooks.get(loggedUser).isEmpty()) {
							int num = 0;
							for (List<Book> books : borrowedBooks.get(loggedUser).values()) {
								for (Book book : books) {
									num++;
								}
							}
							if (num == 0) {
								System.out.println("Brak wypo¿yczonych ksi¹¿ek");
								break;
							}
						} else {

							System.out.println("Proszê podaæ tytu³ ksi¹¿ki do zwrócenia: ");
							String bookTitle = bookScannerReturn.nextLine();

							if (loggedUser instanceof Person) {
								for (Book book : libraryActionsService.getRentalService().getRentedBooks()
										.get(loggedUser).get(loggedUser)) {
									if (book.getTitle().equals(bookTitle)) {

										penaltySum = penaltyCalc(bookScannerReturn, penaltySum, book);
										libraryActionsService.getRentalService().getRentedBooks().get(loggedUser)
												.get(loggedUser).remove(book);
										library.getDepartments().get(book.getBookDepartment()).add(book);
										booksReturnedLate(penaltySum);
										penaltyTotalDept(penaltySum);
										System.out.println("Pomyœlnie zwrócono ksi¹¿kê");
										break pozyczanie;
									}
								}

							} else {
								for (List<Book> rentedBooks : libraryActionsService.getRentalService().getRentedBooks()
										.get(loggedUser).values()) {
									for (Book bookM : rentedBooks) {
										if (bookM.getTitle().equals(bookTitle)) {

											penaltySum = penaltyCalc(bookScannerReturn, penaltySum, bookM);
											rentedBooks.remove(bookM);
											library.getDepartments().get(bookM.getBookDepartment()).add(bookM);
											booksReturnedLate(penaltySum);
											penaltyTotalDept(penaltySum);
											System.out.println(
													"Pomyœlnie zwrócono ksi¹¿kê wypo¿yczon¹ przez organizacjê");

											break pozyczanie;
										}
									}

								}
							}

						}
						System.out.println("Nie ma takiej wypo¿yczonej ksi¹¿ki");

					} else
						System.out.println("Nigdy nie wypo¿yczano ¿adnych ksi¹¿ek");
				} else
					System.out.println("Brak logowania");
				break;

			case SHOW_ALL:
				libraryActionsService.showLibrary(library);
				break;

			case SHOW_FILTERED:
				libraryActionsService.getFilter().showFiltered(library, in);
				break;

			case SHOW_RENTED_BOOKS:
				libraryActionsService.showRented(loggedUser);

				break;

			case GENERATE_REPORT:
				System.out.println(libraryActionsService.getReports().getLibraryReport().get(departmentActive));

				break;

			case USER_MANAGEMENT:
				clientActions(library, in);
				break;

			case EXIT_LIBRARY:
				System.out.println("Dziêkujemy za skorzystanie z naszych us³ug. Do widzenia!");
				break biblioteka;

			case ILLEGAL:
				System.out.println(RentalActions.ILLEGAL.getAction());

			default:
				break;
			}

		}

	}

	private void penaltyTotalDept(Double penalty) {
		if (penalty > 0) {
			libraryActionsService.getReports().getLibraryReport().get(departmentActive).setTotalFines(
					libraryActionsService.getReports().getLibraryReport().get(departmentActive).getTotalFines()
							+ penalty);
		}
	}

	private void booksReturnedLate(Double penalty) {
		if (penalty > 0) {
			libraryActionsService.getReports().getLibraryReport().get(departmentActive).setLateBooksReturned(
					libraryActionsService.getReports().getLibraryReport().get(departmentActive).getLateBooksReturned()
							+ 1);
		}
	}

	private void addBookBorrowedOtherDept() {
		libraryActionsService.getReports().getLibraryReport().get(departmentActive)
				.setNumberOfBooksBorrowedOtherDept(libraryActionsService.getReports().getLibraryReport()
						.get(departmentActive).getNumberOfBooksBorrowedOtherDept() + 1);
	}

	private void addBookLentOtherDept(DepartmentType departmentLent) {
		libraryActionsService.getReports().getLibraryReport().get(departmentLent)
				.setNumberOfBooksLentOtherDept(libraryActionsService.getReports().getLibraryReport().get(departmentLent)
						.getNumberOfBooksLentOtherDept() + 1);
	}

	private void addBookBorrowedClient(Client user) {
		if (!libraryActionsService.getReports().getLibraryReport().get(departmentActive)
				.getNumberOfBooksBorrowedClient().containsKey(user)) {
			libraryActionsService.getReports().getLibraryReport().get(departmentActive).getNumberOfBooksBorrowedClient()
					.put(user, 1);
		} else {
			libraryActionsService.getReports().getLibraryReport().get(departmentActive).getNumberOfBooksBorrowedClient()
					.put(user, libraryActionsService.getReports().getLibraryReport().get(departmentActive)
							.getNumberOfBooksBorrowedClient().get(user) + 1);
		}

	}

	private void addBookLent() {
		libraryActionsService.getReports().getLibraryReport().get(departmentActive).setNumberOfBooksLent(
				libraryActionsService.getReports().getLibraryReport().get(departmentActive).getNumberOfBooksLent() + 1);
	}

	private double penaltyCalc(Scanner bookScannerReturn, double penaltySum, Book book) {
		int daysBorrowed;
		if (book.getBorrowedFrom() != departmentActive && book.getBookDepartment() != departmentActive) {
			penaltySum += 2.0;
			System.out.println("Naliczono op³atê za zwrot do innego oddzia³u w wysokoœci 2PLN");
		}

		System.out.println("Proszê podaæ przez ile dni by³a wypo¿yczona ksi¹¿ka: ");
		daysBorrowed = bookScannerReturn.nextInt();
		book.setDaysBorrowed(daysBorrowed);
		if (daysBorrowed > 14 && daysBorrowed < 22) {
			libraryActionsService.getRentalService().penaltyAmountCalc(book);
		} else if (daysBorrowed > 21) {
			penaltySum += libraryActionsService.getRentalService().penaltyAmountCalc(book);
			penaltySum += 5;
			System.out.println("Naliczono karê za wys³anie monitu w wysokoœci 5 PLN");

		}
		System.out.println(String.format("£¹czna kara to %sPLN", penaltySum));
		return penaltySum;
	}

	public static DepartmentType setDepartment(Scanner in) {
		while (true) {
			System.out.println("Proszê wybraæ oddzia³, z którego chcia³aby/chcia³by Pani/Pan skorzystaæ: ");
			String departmentScr = in.next();
			DepartmentType departmentLocal;
			try {
				departmentLocal = DepartmentType.valueOf(departmentScr);
				return departmentLocal;
			} catch (Exception e) {
				System.out.println("Taki dzia³ nie istnieje, proszê spróbowaæ ponownie");
			}

		}
	}

}
