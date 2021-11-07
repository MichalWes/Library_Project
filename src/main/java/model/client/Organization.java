package model.client;

import java.util.HashSet;
import java.util.Objects;

import model.library.DepartmentType;

public class Organization extends Client {

	private String organizationName;
	private HashSet<Person> members;

	public Organization(int idString, DepartmentType motherDepartment, String organizationName) {
		super(idString, motherDepartment);
		this.organizationName = organizationName;
		this.members = new HashSet<>();
	}



	public String getOrganizationName() {
		return organizationName;
	}



	public HashSet<Person> getMembers() {
		return members;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(organizationName);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Organization other = (Organization) obj;
		return Objects.equals(organizationName, other.organizationName);
	}

	@Override
	public String toString() {
		return "Organization [" + (organizationName != null ? "organizationName=" + organizationName + ", " : "")
				+ (getId() != null ? "getId()=" + getId() + ", " : "")
				+ (getMotherDepartment() != null ? "getMotherDepartment()=" + getMotherDepartment() : "") + "]";
	}
	

}
