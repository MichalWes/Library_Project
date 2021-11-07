package libraryInterface.client;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import model.client.Client;
import model.client.Organization;
import model.client.Person;
import model.library.DepartmentType;

public class ClientActionsService {

	private List<Client> bazaKlientow;
	private Client loggedUser;
	private Optional<Organization> loggedUserOrganization;
	private DepartmentType departmentActive;

	public ClientActionsService(List<Client> bazaKlientow) {
		this.bazaKlientow = bazaKlientow;
	}

	public Client getLoggedUser() {
		return loggedUser;
	}

	public void setLoggedUser(Client loggedUser) {
		this.loggedUser = loggedUser;
	}

	public Optional<Organization> getLoggedUserOrganization() {
		return loggedUserOrganization;
	}

	public void setLoggedUserOrganization(Optional<Organization> loggedUserOrganization) {
		this.loggedUserOrganization = loggedUserOrganization;
	}

	public DepartmentType getDepartmentActive() {
		return departmentActive;
	}

	public void setDepartmentActive(DepartmentType departmentActive) {
		this.departmentActive = departmentActive;
	}

	public ClientActions availableClientActions(Scanner in) {
		String actionStr = "";
		ClientActions action;
		System.out.println("Do wyboru: " + ClientActions.LOGON_NAME + " (" + ClientActions.LOGON_NAME.getAction()
				+ ") | " + ClientActions.LOGON_ORGANIZATION + " (" + ClientActions.LOGON_ORGANIZATION.getAction()
				+ ") | " + ClientActions.LOGON_ID + " (" + ClientActions.LOGON_ID.getAction() + ") | " + "\n"
				+ ClientActions.CHANGE_DEPARTMENT + " (" + ClientActions.CHANGE_DEPARTMENT.getAction() + ") | "
				+ ClientActions.SHOW_ALL_CLIENTS + " (" + ClientActions.SHOW_ALL_CLIENTS.getAction() + ")");

		try {
			actionStr = in.next().toUpperCase();
		} catch (Exception e) {
			System.out.println("DUPA");
			e.printStackTrace();
		}

		try {
			action = ClientActions.valueOf(actionStr);

		} catch (Exception e1) {
			action = ClientActions.ILLEGAL;
		}
		return action;
	}

	public boolean logonId(Scanner in) {

		System.out.println("Proszê podaæ ID: ");
		int clientIdRead = 0;
		try {
			clientIdRead = in.nextInt();
		} catch (Exception e) {
			System.out.println("Wpisano niepoprawny typ danych");
			return false;
		}

		int clientId = clientIdRead;
		Optional<Client> loggedCheck = bazaKlientow.stream().filter(client -> client.getId() == clientId).findAny();
		if (loggedCheck.isEmpty()) {
			System.out.println("Nie znaleziono u¿ytkownika, proszê spróbowaæ ponownie.");
			return false;
		} else if (loggedCheck.get().equals(loggedUser)) {
			System.out.println("Jest ju¿ Pani/Pan/Organizacja zalogowana/y");
			return true;
		} else {

			departmentActive = setDepartment(in);
			loggedUser = loggedCheck.get();

			if (loggedUser instanceof Organization) {
				Organization organization = (Organization) loggedUser;
				System.out.println("Witaj " + organization.getOrganizationName() + "!!!");
				loggedUserOrganization = null;
			} else {
				Person person = (Person) loggedUser;
				System.out.println("Witaj " + person.getName() + " " + person.getSurName() + "!!!");
				organizationCheck(person);
			}
			System.out.println("Poprawnie zalogowano do biblioteki");
			return true;
		}
	}

	public boolean logonName(Scanner in) {
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
						return true;
					}
					departmentActive = setDepartment(in);
					loggedUser = person;
					System.out.println("Witaj " + person.getName() + " " + person.getSurName() + "!!!");
					organizationCheck(person);
					System.out.println("Poprawnie zalogowano do biblioteki");
					return true;
				}
			}
		}
		System.out.println("Nie znaleziono u¿ytkownika, proszê spróbowaæ ponownie.");
		return false;
	}

	public boolean logonOrganization(Scanner in) {
		System.out.println("Proszê podaæ nazwê organizacji: ");
		String organizationName = in.next();
		for (Client client : bazaKlientow) {
			if (client instanceof Organization) {
				Organization organization = (Organization) client;
				if (organization.getOrganizationName().equals(organizationName)) {
					if (client.equals(loggedUser)) {
						System.out.println("Organizacja jest ju¿ zalogowana");
						return true;
					}
					departmentActive = setDepartment(in);
					loggedUser = organization;
					System.out.println("Witaj " + organization.getOrganizationName() + "!!!");
					loggedUserOrganization = null;
					System.out.println("Poprawnie zalogowano do biblioteki");
					return true;
				}
			}
		}
		System.out.println("Nie znaleziono u¿ytkownika, proszê spróbowaæ ponownie.");
		return false;
	}

	private void organizationCheck(Person person) {
		if (person.isInOrganization()) {
			for (Client clientOrg : bazaKlientow) {
				if (clientOrg instanceof Organization) {
					if (((Organization) clientOrg).getMembers().contains(person)) {
						loggedUserOrganization = Optional.of((Organization) clientOrg);
					}
				}
			}
		} else
			loggedUserOrganization = null;

		if (person.isInOrganization()) {
			System.out.println("Poniewa¿ nale¿ysz do organizacji " + loggedUserOrganization.get().getOrganizationName()
					+ " jesteœ zalogowana/y te¿ w jej imieniu");
		}
	}

	public void showAllClients() {
		for (Client client : bazaKlientow) {
			System.out.println(client);
		}
		System.out.println();
	}

	public DepartmentType setDepartment(Scanner in) {
		while (true) {
			System.out.println("Proszê wybraæ oddzia³, z którego chcia³aby/chcia³by Pani/Pan skorzystaæ: ");
			String departmentScr = in.next().toUpperCase();
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
