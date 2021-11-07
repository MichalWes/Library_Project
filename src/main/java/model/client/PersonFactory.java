package model.client;

import java.util.ArrayList;
import java.util.List;

import model.library.DepartmentType;
import util.Util;

public class PersonFactory {
	public List<String> maleNames = new ArrayList<>();
	public List<String> maleSurnames = new ArrayList<>();
	public List<String> femaleNames = new ArrayList<>();
	public List<String> femaleSurnames = new ArrayList<>();

	public Person createMale(List<Client> bazaKlientow) {
		int randomMaleNameIndex = (int) (Math.random() * maleNames.size());
		int randomMaleSurnameIndex = (int) (Math.random() * maleSurnames.size());
		int randomDepartmentTypeIndex = (int) (Math.random() * DepartmentType.values().length);
		DepartmentType departmentType = DepartmentType.values()[randomDepartmentTypeIndex];
		String name = maleNames.get(randomMaleNameIndex);
		String surName = maleSurnames.get(randomMaleSurnameIndex);
		int idString = Util.idCreator(bazaKlientow);
		return new Person(idString, departmentType , name, surName);
	}
	
	public Person createFemale(List<Client> bazaKlientow) {
		int randomFemaleNameIndex = (int) (Math.random() * femaleNames.size());
		int randomFemaleSurnameIndex = (int) (Math.random() * femaleSurnames.size());
		int randomDepartmentTypeIndex = (int) (Math.random() * DepartmentType.values().length);
		DepartmentType departmentType = DepartmentType.values()[randomDepartmentTypeIndex];
		String name = femaleNames.get(randomFemaleNameIndex);
		String surName = femaleSurnames.get(randomFemaleSurnameIndex);
		int id = Util.idCreator(bazaKlientow);
		return new Person(id, departmentType , name, surName);
	}


	
	

}
