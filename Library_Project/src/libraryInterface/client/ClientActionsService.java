package libraryInterface.client;

import java.util.List;
import java.util.Scanner;

import model.client.Client;
import model.client.Organization;
import model.client.Person;
import model.library.DepartmentType;

public class ClientActionsService {

	private List<Client> bazaKlientow;
	private Client loggedUser;
	private Organization loggedUserOrganization;
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

	public Organization getLoggedUserOrganization() {
		return loggedUserOrganization;
	}

	public void setLoggedUserOrganization(Organization loggedUserOrganization) {
		this.loggedUserOrganization = loggedUserOrganization;
	}

	public DepartmentType getDepartmentActive() {
		return departmentActive;
	}

	public void setDepartmentActive(DepartmentType departmentActive) {
		this.departmentActive = departmentActive;
	}
	
	public ClientActions availableClientActions(Scanner in) {
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

	public void logonId(Scanner in) {
		boolean breakPowitalny = false;
		System.out.println("Proszê podaæ ID: ");
		int clientId = in.nextInt();
		for (Client client : bazaKlientow) {
			if (client.getId() == clientId) {
				if (client.equals(loggedUser)) {
					System.out.println("Jest ju¿ Pani/Pan/Organizacja zalogowana/y");
					breakPowitalny = true;
					break;
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
				break;

			}
		}
		if (!breakPowitalny) {
			System.out.println("Nie znaleziono u¿ytkownika, proszê spróbowaæ ponownie.");
		}

	}
	
	public void logonName(Scanner in) {
		boolean breakPowitalny = false;
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

	public void logonOrganization(Scanner in) {
		boolean breakPowitalny = false;
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
	
	public void showAllClients() {
		for (Client client : bazaKlientow) {
			System.out.println(client);
		}
		System.out.println();
	}

	public DepartmentType setDepartment(Scanner in) {
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
