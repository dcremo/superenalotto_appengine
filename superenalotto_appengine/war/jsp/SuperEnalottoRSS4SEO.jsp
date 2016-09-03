<%@page pageEncoding="UTF-8" %><%@page import="it.davidecremonesi.superenalotto.Versione"%><%@page import="java.util.List"%><%@page import="it.davidecremonesi.atom.*"%><%@page import="it.davidecremonesi.superenalotto.xml.EstrazioneService"%><%@page import="it.davidecremonesi.superenalotto.Estrazione"%><%@page import="java.util.Calendar"%><%@page import="java.util.ArrayList"%><%@page import="it.davidecremonesi.superenalotto.SuperEnalottoParser"%><%
response.setContentType("text/xml");

List<Estrazione> estrazioni = EstrazioneService.caricaLista();
RSSFeed feed = new RSSFeed();

RSSHeader header = new RSSHeader();
//header.setCopyright("Copyright by Davide Cremonesi");
header.setTitle("Ultime estrazioni superenalotto ["+Versione.getVersione()+"]");
header.setId("tag:superenalottodroid,2011:ultimeestrazioni");
header.setSubtitle("Estrazioni, quote e montepremi delle ultime estrazioni superenalotto");
//header.setLanguage("it");
header.setLink("http://superenalottodroid.appspot.com/jsp/SuperEnalottoRSS4SEO.jsp");
header.setUpdated(RSSFeed.formatDate(Calendar.getInstance()));

feed.setHeader(header);

ArrayList<RSSEntry> entries = new ArrayList<RSSEntry>();
for (Estrazione estr : estrazioni) {
	RSSEntry entry = new RSSEntry();
	if (estr.isValid()) {
		entry.setTitle("Estrazione Superenalotto concorso n. "+estr.getNestrazione()+" del "+estr.getDataestrazione());
		entry.setSummary(estr.getDescrizioneTxt());
		entry.setId(estr.getNestrazione()+"@"+estr.getDataestrazione());
		entry.setLink("http://superenalottodroid.appspot.com/jsp/dettagliEstrazione.jsp?data="+estr.getDataestrazione());
		entry.setUpdated(RSSFeed.formatDate(estr.getDataestrazione()));
		entry.setAuthorName(estr.getDescrShort());
		entries.add(entry);
	}
}
feed.setEntries(entries);
%><%= RSSWriter.write(feed) %>