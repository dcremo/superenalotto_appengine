package it.davidecremonesi.superenalotto.persistence;


import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import javax.jdo.PersistenceManager;

public class EstrazioneService {
	
	public static Estrazione save(Estrazione estr, boolean replace) throws Exception {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			if (replace) {
				List<Estrazione> vecchie = caricaLista(estr.getDataestrazione(), pm);
				for (Estrazione vecchia : vecchie) {
					pm.deletePersistent(vecchia);
				}
			}
			pm.makePersistent(estr);
		} finally {
			pm.close();
		}
		return estr;
	}
	
	private static Estrazione save(Estrazione estr) throws Exception {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			pm.makePersistent(estr);
		} finally {
			pm.close();
		}
		return estr;
	};

	public static LinkedList<Estrazione> caricaLista(PersistenceManager pm) {
	    String query = "select from " + Estrazione.class.getName() + " order by nestrazione";
	    List<Estrazione> orig = (List<Estrazione>) pm.newQuery(query).execute();
	    LinkedList<Estrazione> ordinata = new LinkedList<Estrazione>(orig);
	    Collections.sort(ordinata, Collections.reverseOrder(new GreaterThan()));
	    return ordinata;
	}

	public static LinkedList<Estrazione> caricaLista() {
		PersistenceManager pm = PMF.get().getPersistenceManager();
	    String query = "select from " + Estrazione.class.getName()+ " order by nestrazione";
	    List<Estrazione> orig = (List<Estrazione>) pm.newQuery(query).execute();
	    LinkedList<Estrazione> ordinata = new LinkedList<Estrazione>(orig);
	    Collections.sort(ordinata, Collections.reverseOrder(new GreaterThan()));
	    return ordinata;
	}

	private static List<Estrazione> caricaLista(String data, PersistenceManager pm) {
		String query = "select from " + Estrazione.class.getName() + " where dataestrazione=='"+data+"'";
	    return (List<Estrazione>) pm.newQuery(query).execute();
	}

	public static List<Estrazione> caricaWhereData(String dataEstrazione) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		String query = "select from " + Estrazione.class.getName() + " where dataestrazione=='"+dataEstrazione+"'";
	    return (List<Estrazione>) pm.newQuery(query).execute();
	}
	
	public static class GreaterThan implements Comparator<Estrazione> {
		@Override
		public int compare(Estrazione arg0, Estrazione arg1) {
			String datax = arg0.getDataestrazione_YYYYMMDD(); //DD/MM/YYYY
			String datay = arg1.getDataestrazione_YYYYMMDD(); //0123456789
			return datax.compareTo(datay);
		}
	}


}
