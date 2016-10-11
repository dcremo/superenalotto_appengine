package it.davidecremonesi.superenalotto.output;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import it.davidecremonesi.atom.RSSEntry;
import it.davidecremonesi.atom.RSSFeed;
import it.davidecremonesi.atom.RSSHeader;
import it.davidecremonesi.atom.RSSWriter;
import it.davidecremonesi.superenalotto.Versione;
import it.davidecremonesi.superenalotto.persistence.Estrazione;
import it.davidecremonesi.superenalotto.persistence.EstrazioneService;
import it.davidecremonesi.superenalotto.persistence.EstrazioniCache;

public class SuperEnalottoRSS4SEO {

	public static String getFeed() throws Exception {
		List<Estrazione> estrazioni = EstrazioneService.caricaLista();
		RSSFeed feed = new RSSFeed();

		RSSHeader header = new RSSHeader();
		// header.setCopyright("Copyright by Davide Cremonesi");
		header.setTitle("Ultime estrazioni superenalotto [" + Versione.getVersione() + "]");
		header.setId("tag:superenalottodroid,2011:ultimeestrazioni");
		header.setSubtitle("Estrazioni, quote e montepremi delle ultime estrazioni superenalotto");
		// header.setLanguage("it");
		header.setLink("http://superenalottodroid.appspot.com/jsp/SuperEnalottoRSS4SEO.jsp");
		header.setUpdated(RSSFeed.formatDate(Calendar.getInstance()));

		feed.setHeader(header);

		ArrayList<RSSEntry> entries = new ArrayList<RSSEntry>();
		for (Estrazione estr : estrazioni) {
			RSSEntry entry = new RSSEntry();
			if (estr.isValid()) {
				entry.setTitle("Estrazione Superenalotto concorso n. " + estr.getNestrazione() + " del "
						+ estr.getDataestrazione());
				entry.setSummary(estr.getDescrizioneTxt());
				entry.setId(estr.getNestrazione() + "@" + estr.getDataestrazione());
				entry.setLink("http://superenalottodroid.appspot.com/jsp/dettagliEstrazione.jsp?data="
						+ estr.getDataestrazione());
				entry.setUpdated(RSSFeed.formatDate(estr.getDataestrazione()));
				entry.setAuthorName(estr.getDescrShort());
				entries.add(entry);
			}
		}
		feed.setEntries(entries);
		return RSSWriter.write(feed);
	}
}
