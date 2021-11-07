package model.client;

import java.util.Objects;

import model.library.DepartmentType;

public class Person extends Client {
	private String name;
	private String surName;
	private boolean inOrganization;

	public Person(int idString, DepartmentType motherDepartment, String name, String surName) {
		super(idString, motherDepartment);
		this.name = name;
		this.surName = surName;
	}

	public String getName() {
		return name;
	}

	public String getSurName() {
		return surName;
	}
	
	

	public boolean isInOrganization() {
		return inOrganization;
	}

	public void setInOrganization(boolean belongsToOrganization) {
		this.inOrganization = belongsToOrganization;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(name, surName);
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
		Person other = (Person) obj;
		return Objects.equals(name, other.name) && Objects.equals(surName, other.surName);
	}

	@Override
	public String toString() {
		return "Person [" + (name != null ? "name=" + name + ", " : "")
				+ (surName != null ? "surName=" + surName + ", " : "") + "inOrganization=" + inOrganization + ", "
				+ (getId() != null ? "getId()=" + getId() + ", " : "")
				+ (getMotherDepartment() != null ? "getMotherDepartment()=" + getMotherDepartment() : "") + "]";
	}

}
