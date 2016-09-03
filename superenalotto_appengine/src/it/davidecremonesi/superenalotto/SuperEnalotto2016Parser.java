package it.davidecremonesi.superenalotto;

import it.davidecremonesi.superenalotto.parserHelpers.Risposta;
import it.davidecremonesi.superenalotto.parserHelpers.Vincita;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Currency;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;

import javax.swing.text.DateFormatter;

import com.google.appengine.api.urlfetch.HTTPHeader;
import com.google.appengine.api.urlfetch.HTTPRequest;
import com.google.appengine.api.urlfetch.HTTPResponse;
import com.google.appengine.api.urlfetch.URLFetchService;
import com.google.appengine.api.urlfetch.URLFetchServiceFactory;
import com.google.gson.Gson;


public class SuperEnalotto2016Parser extends ISuperEnalottoParser {

	private static final Logger logger = Logger.getLogger(SuperEnalotto2016Parser.class.getName());

	public Estrazione getEstrazione(int anno, int n) throws Exception {

		Estrazione retval = new Estrazione();

		try {
			URLFetchService urlFetchService = URLFetchServiceFactory.getURLFetchService();
			
			String url2016 = "http://www.gntn-pgd.it/gntn-info-web/rest/gioco/superenalotto/estrazioni/ultimoconcorso?idPartner=vetrina";
			if (anno>0 && n>0) {
				url2016 = "http://www.gntn-pgd.it/gntn-info-web/rest/gioco/superenalotto/estrazioni/dettaglioconcorso/"+anno+"/"+n+"?idPartner=vetrina";
			}
			URL url = new URL(url2016);
			
			HTTPRequest request = new HTTPRequest(url);
			//Without these headers, AppEngine caches the url fetch
			request.setHeader(new HTTPHeader("Cache-Control", "no-cache,max-age=0"));
			request.setHeader(new HTTPHeader("Pragma", "no-cache"));
			request.setHeader(new HTTPHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; rv:2.0.1) Gecko/20100101 Firefox/4.0.1"));
			request.setHeader(new HTTPHeader("Accept-Language", "it-IT,it;q=0.8,en-us;q=0.6,en;q=0.4"));
			HTTPResponse response = urlFetchService.fetch(request); 
	
			ByteArrayInputStream bais = new ByteArrayInputStream(response.getContent());
			BufferedReader reader = new BufferedReader(new InputStreamReader(bais));
			String page = "";
			String lline;
			while ((lline = reader.readLine()) != null) {
				page += lline.trim();
			}
			reader.close();
            String subject = ("SuperenalottoParser @"+new Date());
			
			int foundend = 0;
			Gson gson = new Gson();
			String json = page;
			Risposta parsed = gson.fromJson(json, Risposta.class);
			
			retval.setDataEstrazione(new SimpleDateFormat("dd/MM/YYYY").format(new Date(parsed.getDataEstrazione())));
			retval.setEstratti(parsed.getCombinazioneVincente().getEstratti());
			retval.setGiornoEstrazione(Utils.extractWeekDay(parsed.getDataEstrazione()));
			retval.setJolly(parsed.getCombinazioneVincente().getNumeroJolly());
			retval.setSuperstar(parsed.getCombinazioneVincente().getSuperstar());
			
			retval.setMontepremi_corr(Utils.parseValuta(parsed.getMontepremi().getMontepremiConcorso()));
			retval.setMontepremi_prec(Utils.parseValuta(parsed.getMontepremi().getRiportoMontepremiPrecedente()));
			retval.setMontepremi_totale(Utils.parseValuta(parsed.getMontepremi().getMontepremiTotale()));
			retval.setNestrazione(parsed.getConcorso().getNumero());
			
			
			String quanti3  = "";
			String vincita3 = "";
			String quanti4  = "";
			String vincita4 = "";
			String quanti5  = "";
			String vincita5 = "";
			String quanti51 = "";
			String vincita51= "";
			String quanti6  = "";
			String vincita6 = "";
			
			for (Vincita vincita : parsed.getDettaglioVincite().getVincite()) {
				String tipo = vincita.getQuota().getCategoriaVincita().getTipo();
				if (tipo.equals("14")) {
					quanti6 = vincita.getNumero()>0 ? ""+vincita.getNumero() : "Nessuna";
					vincita6 = Utils.parseValuta(vincita.getQuota().getImporto());
				} else if (tipo.equals("13")) {
					quanti51 = vincita.getNumero()>0 ? ""+vincita.getNumero() : "Nessuna";
					vincita51 = Utils.parseValuta(vincita.getQuota().getImporto());
				} else if (tipo.equals("12")) {
					quanti5 = vincita.getNumero()>0 ? ""+vincita.getNumero() : "Nessuna";
					vincita5 = Utils.parseValuta(vincita.getQuota().getImporto());
				} else if (tipo.equals("11")) {
					quanti4 = vincita.getNumero()>0 ? ""+vincita.getNumero() : "Nessuna";
					vincita4 = Utils.parseValuta(vincita.getQuota().getImporto());
				} else if (tipo.equals("10")) {
					quanti3 = vincita.getNumero()>0 ? ""+vincita.getNumero() : "Nessuna";
					vincita3 = Utils.parseValuta(vincita.getQuota().getImporto());
				}
			}
			
			retval.setQuanti3(quanti3);
			retval.setQuanti4(quanti4);
			retval.setQuanti5(quanti5);
			retval.setQuanti51(quanti51);
			retval.setQuanti6(quanti6);
			retval.setVincita3(vincita3);
			retval.setVincita4(vincita4);
			retval.setVincita5(vincita5);
			retval.setVincita51(vincita51);
			retval.setVincita6(vincita6);
			
			retval.setDettaglioVincite(parsed.getDettaglioVincite());

		} catch (Exception e) {
			throw e;
		}
		return retval;
	}


}


/**********************
 * {"concorso":{"numero":"37","anno":"2016"},"versioneGioco":"1","tipoGioco":100
 * ,"dataEstrazione":1459018800000,"combinazioneVincente":{"estratti":["28","43"
 * ,"67","68","72","79"],"numeroJolly":"18","superstar":"60"},
 * "codiceSuperVincitore"
 * :null,"dettaglioDisponibile":1,"montepremi":{"riportoMontepremiPrecedente"
 * :6118827534,"riportoMontepremiDecreto":258504,"montepremiConcorso":463286100,
 * "montepremiTotale"
 * :6582372138},"jackpot":6200000000,"dettaglioVincite":{"vincite"
 * :[{"quota":{"categoriaVincita"
 * :{"tipo":"14","descrizione":"Punti 6"},"importo"
 * :0},"numero":"0","totale":0},{
 * "quota":{"categoriaVincita":{"tipo":"13","descrizione"
 * :"Punti 5+1"},"importo":
 * 0},"numero":"0","totale":0},{"quota":{"categoriaVincita"
 * :{"tipo":"12","descrizione"
 * :"Punti 5"},"importo":2432252},"numero":"8","totale"
 * :19458016},{"quota":{"categoriaVincita"
 * :{"tipo":"11","descrizione":"Punti 4"},
 * "importo":40469},"numero":"484","totale"
 * :19586996},{"quota":{"categoriaVincita"
 * :{"tipo":"10","descrizione":"Punti 3"},
 * "importo":3226},"numero":"18419","totale"
 * :59419694},{"quota":{"categoriaVincita"
 * :{"tipo":"9","descrizione":"Punti 2"},"importo"
 * :617},"numero":"300130","totale"
 * :185180210},{"quota":{"categoriaVincita":{"tipo"
 * :"57","descrizione":"Premio Instantaneo 8a Categoria"
 * },"importo":2500},"numero"
 * :"13751","totale":34377500},{"quota":{"categoriaVincita"
 * :{"tipo":"25","descrizione"
 * :"5 Stella"},"importo":0},"numero":"0","totale":0},
 * {"quota":{"categoriaVincita"
 * :{"tipo":"24","descrizione":"4 Stella"},"importo":
 * 4046900},"numero":"2","totale"
 * :8093800},{"quota":{"categoriaVincita":{"tipo":"23"
 * ,"descrizione":"3 Stella"},
 * "importo":322600},"numero":"71","totale":22904600},
 * {"quota":{"categoriaVincita"
 * :{"tipo":"22","descrizione":"2 Stella"},"importo":
 * 10000},"numero":"1421","totale"
 * :14210000},{"quota":{"categoriaVincita":{"tipo"
 * :"21","descrizione":"1 Stella"}
 * ,"importo":1000},"numero":"9020","totale":9020000
 * },{"quota":{"categoriaVincita"
 * :{"tipo":"20","descrizione":"0 Stella"},"importo"
 * :500},"numero":"21811","totale"
 * :10905500}],"vincitaPromozione":null,"numeroTotaleVincite"
 * :"365117","importoTotaleVincite"
 * :383156316},"attributiExtra":{"attributi":[]}}
 */