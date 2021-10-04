package model.client;

import java.util.Objects;

import model.library.DepartmentType;

public abstract class Client {

	private int id;
	private DepartmentType motherDepartment;

	public Client(int id, DepartmentType motherDepartment) {
		this.id = id;
		this.motherDepartment = motherDepartment;
	}

	public Integer getId() {
		return id;
	}

	public DepartmentType getMotherDepartment() {
		return motherDepartment;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Client other = (Client) obj;
		return id == other.id;
	}

}
