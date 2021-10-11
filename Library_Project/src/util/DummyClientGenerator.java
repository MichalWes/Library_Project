package util;

import java.util.ArrayList;
import java.util.List;

import model.client.Client;
import model.client.Organization;
import model.client.OrganizationFactory;
import model.client.Person;
import model.client.PersonFactory;

public class DummyClientGenerator {

	public List<Client> generate(int clientNumber) {
		OrganizationFactory organizationFactory = new OrganizationFactory();
		PersonFactory personFactory = new PersonFactory();
		List<Client> bazaKlientow = new ArrayList<>();

		organizationFactory.organizationNames.add("ONZ");
		organizationFactory.organizationNames.add("Zrzeszenie Kaszubsko-Pomorskie");
		organizationFactory.organizationNames.add("WHO");
		organizationFactory.organizationNames.add("WWF");
		organizationFactory.organizationNames.add("Towarzystwo Opieki Nad Studentami");
		personFactory.femaleNames.add("Anna");
		personFactory.femaleNames.add("Katarzyna");
		personFactory.femaleNames.add("Natalia");
		personFactory.femaleNames.add("Karolina");
		personFactory.femaleNames.add("Sylwia");
		personFactory.femaleNames.add("Anita");
		personFactory.femaleNames.add("Weronika");
		personFactory.femaleNames.add("Ma³gorzata");
		personFactory.femaleNames.add("Klaudia");
		personFactory.femaleNames.add("Patrycja");
		personFactory.femaleSurnames.add("Leciwa");
		personFactory.femaleSurnames.add("Groteska");
		personFactory.femaleSurnames.add("Kowalska");
		personFactory.femaleSurnames.add("Ciemiê¿ycka");
		personFactory.femaleSurnames.add("Wêgorzewska");
		personFactory.femaleSurnames.add("Suwalska");
		personFactory.femaleSurnames.add("Kruszyna");
		personFactory.femaleSurnames.add("Cerekwicka");
		personFactory.femaleSurnames.add("Budzieszyñska");
		personFactory.femaleSurnames.add("Kolokwialna");
		personFactory.maleNames.add("Micha³");
		personFactory.maleNames.add("Andrzej");
		personFactory.maleNames.add("Karol");
		personFactory.maleNames.add("Józef");
		personFactory.maleNames.add("Bart³omiej");
		personFactory.maleNames.add("Bo¿ydar");
		personFactory.maleNames.add("Jan");
		personFactory.maleNames.add("Jaros³aw");
		personFactory.maleNames.add("Supermen");
		personFactory.maleNames.add("Marek");
		personFactory.maleSurnames.add("Brudzyñski");
		personFactory.maleSurnames.add("Kruszewski");
		personFactory.maleSurnames.add("Wroc³awski");
		personFactory.maleSurnames.add("Ewangelicki");
		personFactory.maleSurnames.add("Katolicki");
		personFactory.maleSurnames.add("Buddyjski");
		personFactory.maleSurnames.add("Hinduski");
		personFactory.maleSurnames.add("Protestancki");
		personFactory.maleSurnames.add("Kaczyñski");
		personFactory.maleSurnames.add("Tusk");

		for (int i = 0; i < clientNumber; i++) {
			if (Math.random() < 0.1) {
				bazaKlientow.add(organizationFactory.createOrganization(bazaKlientow));
			} else {
				if (Math.random() < 0.5) {
					bazaKlientow.add(personFactory.createMale(bazaKlientow));
				} else
					bazaKlientow.add(personFactory.createFemale(bazaKlientow));
			}
		}

		for (Client client : bazaKlientow) {
			if (client instanceof Organization) {
				Organization organization = (Organization) client;
				for (Client clientInner : bazaKlientow) {
					if (clientInner instanceof Person && !((Person) clientInner).isInOrganization()) {
						Person person = (Person) clientInner;
						if (organization.getMembers().size() < 2 && Math.random() < 0.3) { 
							organization.getMembers().add(person);
							person.setInOrganization(true);
						}
					}
				}

			}
		}

		for (Client client : bazaKlientow) {

			if (client instanceof Organization) {
				Organization organization = (Organization) client;
				System.out.println(organization);
				for (Person person : organization.getMembers()) {
					System.out.println(person);
				}
			}
		}

		return bazaKlientow;

	}

}
