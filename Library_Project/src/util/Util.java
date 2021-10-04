package util;

import java.util.List;

import model.client.Client;

public class Util {
	
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

}
