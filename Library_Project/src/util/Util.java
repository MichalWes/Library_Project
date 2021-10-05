package util;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;

import model.client.Client;
import model.library.book.Book;

public class Util {
	
	public static NumberFormat plNumberFormat = NumberFormat.getCurrencyInstance(Locale.getDefault());
	
	public static int idCreator(List<Client> bazaKlientow) {
		int id;
		int idProposal;
		while (true) {
			idProposal = (int) (Math.random() * 100 + 100);
			if (!Util.containsId(bazaKlientow, idProposal)) {
				id = idProposal;
				break;
			}
		}
		return id;
	}

	public static boolean containsId(List<Client> clients, Integer id) {
		for (Client client : clients) {
			if (client != null && client.getId().equals(id)) {
				return true;
			}
		}
		return false;
	}
	
	public static List<Book> filter(List<Book> books, Predicate<Book> bookPredicate){
		List<Book> result = new ArrayList<>();
		
		for (Book book : books) {
			if (bookPredicate.test(book)) {
				result.add(book);
			}
			
		} 
		
		return result;
		
		
	}
}
