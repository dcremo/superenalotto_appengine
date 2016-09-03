package it.davidecremonesi.superenalotto;

import it.davidecremonesi.superenalotto.memcache.EstrazioniCache;
import it.davidecremonesi.superenalotto.xml.EstrazioneService;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;
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
public class Utils {

	private static final Logger logger = Logger.getLogger(Utils.class.getName());

	public static void sendEmail(String page, String subject) {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        String msgBody = page;

        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress("dvd.crm@gmail.com", "SuperEnalottoDroid Admin"));
            msg.addRecipient(Message.RecipientType.TO,
                             new InternetAddress("dvd.crm@gmail.com", "Davide User"));
            msg.setSubject(subject);
            msg.setText(msgBody);
            Transport.send(msg);

        } catch (AddressException e) {
            // ...
        } catch (MessagingException e) {
            // ...
        } catch (UnsupportedEncodingException e) {
            // ...
        }
	}
	
	public static void sendTweet(String latestStatus) throws TwitterException {
		// The factory instance is re-useable and thread safe.
	    Twitter twitter = new TwitterFactory().getInstance();
	    Status status = twitter.updateStatus(latestStatus);
	    System.out.println("Successfully updated the status to [" + status.getText() + "].");	
	}
	
	public static String parseValuta(long importo) {
		NumberFormat nf2 = NumberFormat.getInstance(Locale.ITALIAN);
		//Locale currentLocale = new Locale.Builder().setLanguage("it").setRegion("IT").build();
		//Currency currentCurrency = Currency.getInstance(currentLocale);
	    //NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(currentLocale);
	    return nf2.format(importo*100/100);
	}
	
	public static String extractWeekDay(long dataEstrazione) {
		return "TODO";
	}

}
