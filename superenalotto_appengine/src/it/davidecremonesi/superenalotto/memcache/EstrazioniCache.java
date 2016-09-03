package it.davidecremonesi.superenalotto.memcache;

import it.davidecremonesi.superenalotto.Estrazione;
import it.davidecremonesi.superenalotto.xml.EstrazioneService;

import java.util.List;

import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;

public class EstrazioniCache {
	
	static MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
	static final String key_dataUltimaEstrazione = "dataUltimaEstrazione";
	
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
}
