package it.davidecremonesi.superenalotto;

import it.davidecremonesi.superenalotto.memcache.EstrazioniCache;
import it.davidecremonesi.superenalotto.xml.EstrazioneService;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

@SuppressWarnings("serial")
public class DownloadServlet extends HttpServlet {

	private static final Logger logger = Logger.getLogger(DownloadServlet.class.getName());

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		boolean replace = req.getParameter("keep")==null;
		resp.getWriter().print("DownloadServlet V."+Versione.getVersione()+"<br>");
		
		Estrazione estr;
		try {
			String req_anno = req.getParameter("anno");
			String req_n = req.getParameter("n");
			int anno = 0;
			int n = 0;
			try {
				anno = Integer.parseInt(req_anno);
				n = Integer.parseInt(req_n);
			} catch (NumberFormatException nfe) {
				
			}
			//Utils.sendTweet("DownloadServlet V."+Versione.getVersione());
			//estr = new SuperEnalottoParser().getEstrazione();
			estr = new SuperEnalotto2016Parser().getEstrazione(anno, n);
			resp.getWriter().print("SuperEnalottoParser finished "+estr+"<br>");
			if (estr.getJolly()!=null && estr.getJolly().length()>0) {
				resp.getWriter().print("L'estrazione � valida (Jolly=["+estr.getJolly()+"]<br>");
				String dataUltimaEstrazione = EstrazioniCache.getDataUltimaEstrazione();
				resp.getWriter().println("Data dell'ultima estrazione presente: " + dataUltimaEstrazione+"<br>");
				resp.getWriter().println("Data dell'estrazione scaricata: " + estr.getDataestrazione()+"<br>");
				if (!estr.getDataestrazione().equals(dataUltimaEstrazione)) {
					String txt = "Data dell'ultima estrazione presente: " + dataUltimaEstrazione+"<br>";
					txt += "Data dell'estrazione scaricata: " + estr.getDataestrazione()+"<br>";
		            String subject = "Nuova estrazione superenalotto @"+new Date();
					Utils.sendEmail(txt, subject);
					estr = EstrazioneService.save(estr, replace);
					resp.getWriter().print("Scarico dati avvenuto correttamente. Replace was ("+(replace)+")<br>");
					resp.getWriter().print("Nuovo record persistent"+estr.key+"<br>");
					dataUltimaEstrazione = estr.getDataestrazione();
					EstrazioniCache.clear();
					EstrazioniCache.setDataUltimaEstrazione(dataUltimaEstrazione);
					sendTweet(estr);
				} else {
					resp.getWriter().println("Non � necessario aggiornare la banca dati.<br>");
				}
				
			} else {
				resp.getWriter().print("Lo scarico dati non � stato completato.<br>");
			}
		} catch (Exception e) {
			resp.getWriter().print("Errore in fase di download." + e + "<br>");
			logger.severe("Errore in fase di download." + e);
		}
	}

	private void sendTweet(Estrazione estr) throws TwitterException {
		String msg = "#superenalotto Nuova estrazione del "+estr.getDataestrazione()+"\n";
		msg += " Concorso n: " + estr.getNestrazione();
		msg += " Estratti: ";
		msg += estr.getEstratti()[0]+",";
		msg += estr.getEstratti()[1]+",";
		msg += estr.getEstratti()[2]+",";
		msg += estr.getEstratti()[3]+",";
		msg += estr.getEstratti()[4]+",";
		msg += estr.getEstratti()[5];
		msg += " Jolly: "+estr.getJolly();
		msg += " Superstar: "+estr.getSuperstar();
		
		//String url = ("http://superenalottodroid.appspot.com/jsp/dettagliEstrazione.jsp?data="+estr.getDataestrazione());
		//msg += url;

		Utils.sendTweet(msg);
	}
}
