package model.client;

import model.library.DepartmentType;
import util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OrganizationFactory {
	public List<String> organizationNames = new ArrayList<>();
	
	public Organization createOrganization(List<Client> bazaKlientow) {
		int randomOrganizationNameIndex = (int) (Math.random() * organizationNames.size());
		int randomDepartmentTypeIndex = (int) (Math.random() * DepartmentType.values().length);
		DepartmentType departmentType = DepartmentType.values()[randomDepartmentTypeIndex];
		String name = organizationNames.get(randomOrganizationNameIndex);
		if (organizationNames.size() != 1) {
			organizationNames.remove(randomOrganizationNameIndex);
		} else name = (UUID.randomUUID()).toString();

		
		int idString = Util.idCreator(bazaKlientow);
		return new Organization(idString, departmentType , name);
	}

}
