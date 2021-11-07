package util;

import java.util.ArrayList;
import java.util.List;

import model.library.book.Book;
import model.library.book.BookFactory;

public class DummyBooksGenerator {

	public List<Book> generate(int booksNumber) {
		BookFactory fabrykaKsiazek = new BookFactory();
		List<Book> biblioteka = new ArrayList<>();

		fabrykaKsiazek.authors.add("Amir Mazur");
		fabrykaKsiazek.authors.add("Lucjan Baranowski");
		fabrykaKsiazek.authors.add("Eugeniusz Kowalski");
		fabrykaKsiazek.authors.add("Hubert Zalewski");
		fabrykaKsiazek.authors.add("Andrzej Zalewski");
		fabrykaKsiazek.authors.add("Joachim Jasi�ski");
		fabrykaKsiazek.authors.add("Cyprian Brzezi�ski");
		fabrykaKsiazek.authors.add("Kacper Wo�niak");
		fabrykaKsiazek.authors.add("Eustachy Ostrowski");
		fabrykaKsiazek.authors.add("Igor Brzezi�ski");
		fabrykaKsiazek.authors.add("Ariel Zieli�ski");
		fabrykaKsiazek.authors.add("Albert Jankowski");
		fabrykaKsiazek.authors.add("Borys B�aszczyk");
		fabrykaKsiazek.authors.add("Kamil B�k");
		fabrykaKsiazek.authors.add("Miros�aw B�aszczyk");
		fabrykaKsiazek.authors.add("Miros�awa Szymczak");
		fabrykaKsiazek.authors.add("Ola Borkowska");
		fabrykaKsiazek.authors.add("Lila Laskowska");
		fabrykaKsiazek.authors.add("Eleonora Kalinowska");
		fabrykaKsiazek.authors.add("Julianna Szczepa�ska");
		fabrykaKsiazek.authors.add("Bogna Jakubowska");
		fabrykaKsiazek.authors.add("Oksana Zieli�ska");
		fabrykaKsiazek.authors.add("Eliza St�pie�");
		fabrykaKsiazek.authors.add("Elwira Chmielewska");
		fabrykaKsiazek.authors.add("Ewelina Kowalczyk");
		fabrykaKsiazek.titles.add("Samotno�� liczb pierwszych");
		fabrykaKsiazek.titles.add("Modne trumny, kt�re zbudujesz sam");
		fabrykaKsiazek.titles.add("Trzech pan�w w ��dce, nie licz�c psa");
		fabrykaKsiazek.titles.add("M�czyzna, kt�ry pomyli� swoj� �on� z kapeluszem");
		fabrykaKsiazek.titles.add("Wszystko co wiem o kobietach nauczy�em si� od mojego traktora");
		fabrykaKsiazek.titles.add("Jak za�o�y� sw�j w�asny kraj");
		fabrykaKsiazek.titles.add("Zastosowanie technik alpinistycznych w �eglarstwie");
		fabrykaKsiazek.titles.add("Nostradamus zjad� mi chomika");
		fabrykaKsiazek.titles.add("Czy ten rudy kot to pies?");
		fabrykaKsiazek.titles.add("Cz�owiek o jednakowych z�bach");
		fabrykaKsiazek.titles.add("Pi�ta kolumna");
		fabrykaKsiazek.titles.add("Sz�sty zmys�");
		fabrykaKsiazek.titles.add("Si�dmy grzech");
		fabrykaKsiazek.titles.add("�smy karze�");
		fabrykaKsiazek.titles.add("Dziewi�ty samiec");
		fabrykaKsiazek.titles.add("Dziesi�ta karawana");
		fabrykaKsiazek.titles.add("Alleluja");
		fabrykaKsiazek.titles.add("Ahoj Kreciku");
		fabrykaKsiazek.titles.add("S�ownik J�zyka Kaszubskiego");
		fabrykaKsiazek.titles.add("Niesamowite Potwory");
		fabrykaKsiazek.titles.add("S�ownik J�zyka Angielskiego");
		fabrykaKsiazek.titles.add("S�ownik J�zyka Polskiego");

		for (int i = 0; i < booksNumber/2; i++) {
			biblioteka.add(fabrykaKsiazek.create());
		}

		biblioteka.addAll(biblioteka);
		
		return biblioteka;

	}

}
