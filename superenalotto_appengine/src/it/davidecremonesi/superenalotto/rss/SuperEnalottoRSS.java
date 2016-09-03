package it.davidecremonesi.superenalotto.rss;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;

import it.davidecremonesi.atom.RSSEntry;
import it.davidecremonesi.atom.RSSFeed;
import it.davidecremonesi.atom.RSSHeader;
import it.davidecremonesi.atom.RSSWriter;
import it.davidecremonesi.superenalotto.DownloadServlet;
import it.davidecremonesi.superenalotto.Estrazione;
import it.davidecremonesi.superenalotto.Utils;
import it.davidecremonesi.superenalotto.Versione;
import it.davidecremonesi.superenalotto.memcache.EstrazioniCache;
import it.davidecremonesi.superenalotto.xml.EstrazioneService;

public class SuperEnalottoRSS {
	
	public static String getFeed() throws Exception {
		// carica l'ultima estrazione
	    String dataUltima = EstrazioniCache.getDataUltimaEstrazione();
	    String rssKey = "rss_"+dataUltima;
	    String rssValue = EstrazioniCache.get(rssKey); // read from cache
	    if (rssValue == null || rssValue.length()==0) {
	    	rssValue = createFeed();
			Utils.sendEmail(rssValue, "Nuovo feed RSS, key="+rssKey);
			EstrazioniCache.put(rssKey, rssValue); // populate cache
	    }
		return rssValue;
	}
	
	private static String createFeed() throws Exception {
		List<Estrazione> estrazioni = EstrazioneService.caricaLista();
		RSSFeed feed = new RSSFeed();

		RSSHeader header = new RSSHeader();
		//header.setCopyright("Copyright by Davide Cremonesi");
		header.setTitle("Ultime estrazioni superenalotto ["+Versione.getVersione()+"]");
		header.setId("tag:superenalottodroid,2011:ultimeestrazioni");
		header.setSubtitle("Tutti i dettagli delle ultime estrazioni superenalotto");
		//header.setLanguage("it");
		header.setLink("http://superenalottodroid.appspot.com/jsp/SuperEnalottoRSS.jsp");
		header.setUpdated(RSSFeed.formatDate(Calendar.getInstance()));

		feed.setHeader(header);

		ArrayList<RSSEntry> entries = new ArrayList<RSSEntry>();
		for (Estrazione estr : estrazioni) {
			RSSEntry entry = new RSSEntry();
			if (estr.isValid()) {
				entry.setTitle("Estrazione n. "+estr.getNestrazione()+" del "+estr.getDataestrazione());
				entry.setSummary(estr.getDescrizione());
				entry.setId("http://superenalottodroid.appspot.com/estrazione.jsp?data="+estr.getDataestrazione());
				entry.setLink("http://superenalottodroid.appspot.com/jsp/dettagliEstrazione.jsp?data="+estr.getDataestrazione());
				entry.setUpdated(RSSFeed.formatDate(estr.getDataestrazione()));
				entry.setAuthorName(estr.getDescrShort());
				entries.add(entry);
			}
		}
		feed.setEntries(entries);
		return RSSWriter.write(feed);
	}
}
