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
		fabrykaKsiazek.authors.add("Joachim Jasiñski");
		fabrykaKsiazek.authors.add("Cyprian Brzeziñski");
		fabrykaKsiazek.authors.add("Kacper WoŸniak");
		fabrykaKsiazek.authors.add("Eustachy Ostrowski");
		fabrykaKsiazek.authors.add("Igor Brzeziñski");
		fabrykaKsiazek.authors.add("Ariel Zieliñski");
		fabrykaKsiazek.authors.add("Albert Jankowski");
		fabrykaKsiazek.authors.add("Borys B³aszczyk");
		fabrykaKsiazek.authors.add("Kamil B¹k");
		fabrykaKsiazek.authors.add("Miros³aw B³aszczyk");
		fabrykaKsiazek.authors.add("Miros³awa Szymczak");
		fabrykaKsiazek.authors.add("Ola Borkowska");
		fabrykaKsiazek.authors.add("Lila Laskowska");
		fabrykaKsiazek.authors.add("Eleonora Kalinowska");
		fabrykaKsiazek.authors.add("Julianna Szczepañska");
		fabrykaKsiazek.authors.add("Bogna Jakubowska");
		fabrykaKsiazek.authors.add("Oksana Zieliñska");
		fabrykaKsiazek.authors.add("Eliza Stêpieñ");
		fabrykaKsiazek.authors.add("Elwira Chmielewska");
		fabrykaKsiazek.authors.add("Ewelina Kowalczyk");
		fabrykaKsiazek.titles.add("Samotnoœæ liczb pierwszych");
		fabrykaKsiazek.titles.add("Modne trumny, które zbudujesz sam");
		fabrykaKsiazek.titles.add("Trzech panów w ³ódce, nie licz¹c psa");
		fabrykaKsiazek.titles.add("Mê¿czyzna, który pomyli³ swoj¹ ¿onê z kapeluszem");
		fabrykaKsiazek.titles.add("Wszystko co wiem o kobietach nauczy³em siê od mojego traktora");
		fabrykaKsiazek.titles.add("Jak za³o¿yæ swój w³asny kraj");
		fabrykaKsiazek.titles.add("Zastosowanie technik alpinistycznych w ¿eglarstwie");
		fabrykaKsiazek.titles.add("Nostradamus zjad³ mi chomika");
		fabrykaKsiazek.titles.add("Czy ten rudy kot to pies?");
		fabrykaKsiazek.titles.add("Cz³owiek o jednakowych zêbach");
		fabrykaKsiazek.titles.add("Pi¹ta kolumna");
		fabrykaKsiazek.titles.add("Szósty zmys³");
		fabrykaKsiazek.titles.add("Siódmy grzech");
		fabrykaKsiazek.titles.add("Ósmy karze³");
		fabrykaKsiazek.titles.add("Dziewi¹ty samiec");
		fabrykaKsiazek.titles.add("Dziesi¹ta karawana");
		fabrykaKsiazek.titles.add("Alleluja");
		fabrykaKsiazek.titles.add("Ahoj Kreciku");
		fabrykaKsiazek.titles.add("S³ownik Jêzyka Kaszubskiego");
		fabrykaKsiazek.titles.add("Niesamowite Potwory");
		fabrykaKsiazek.titles.add("S³ownik Jêzyka Angielskiego");
		fabrykaKsiazek.titles.add("S³ownik Jêzyka Polskiego");

		for (int i = 0; i < booksNumber/2; i++) {
			biblioteka.add(fabrykaKsiazek.create());
		}

		biblioteka.addAll(biblioteka);
		
		return biblioteka;

	}

}
