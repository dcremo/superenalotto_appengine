package it.davidecremonesi.superenalotto.persistence;

import java.util.List;
import java.util.Vector;

import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;

public class EstrazioniCache {
	
	static MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
	static final String key_dataUltimaEstrazione = "dataUltimaEstrazione";
	static final String key_tutteEstrazioni = "tutteEstrazioni";
	
	public static String getDataUltimaEstrazione() {
		String retval = null;
		retval = (String)syncCache.get(key_dataUltimaEstrazione); // read from cache

		if (retval==null) {
			List<Estrazione> estrazioni = EstrazioneService.caricaLista();
			if (estrazioni.size()>0) {
				retval = estrazioni.get(0).getDataestrazione();
				setDataUltimaEstrazione(retval);
			}
		}
		return retval;
	}

	public static void setDataUltimaEstrazione(String dataUltimaEstrazione) {
		syncCache.put(key_dataUltimaEstrazione, dataUltimaEstrazione);
	}

	public static void clear() {
		syncCache.clearAll();
	}

	public static String get(String rssKey) {
		return (String)syncCache.get(rssKey);
	}

	public static void put(String rssKey, String rssValue) {
		syncCache.put(rssKey, rssValue);
	}

	public static Estrazione getEstrazione(String key) {
		return (Estrazione)syncCache.get(key);
	}

	public static Vector<Estrazione> getEstrazioni() {
		return (Vector<Estrazione>)syncCache.get(key_tutteEstrazioni);
	}

	public static void putEstrazione(String key, Estrazione value) {
		syncCache.put(key, value);
	}
}
