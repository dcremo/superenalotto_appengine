package it.davidecremonesi.superenalotto;

import it.davidecremonesi.superenalotto.parserHelpers.DettaglioVincite;
import it.davidecremonesi.superenalotto.parserHelpers.Vincita;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable
public class Estrazione {

	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    public Key key;

	// e' stata caricata correttamente?
	public boolean isValid() {
		boolean retval = estratti.length==6;
		if (retval) {
			for (int i=0; i<6; i++) {
				retval &= estratti[i].length()>0;
			}
		}
		return retval;
	}
	
    @Persistent
	private String dataestrazione;

    public String getDataestrazione() {
		return dataestrazione;
	}

    public String getDataestrazione_YYYYMMDD() {
		String anno = dataestrazione.substring(6,10);
		String mese = dataestrazione.substring(3,5);
		String giorno = dataestrazione.substring(0,2);
		return anno+mese+giorno;
	}


	public void setDataestrazione(String dataestrazione) {
		this.dataestrazione = dataestrazione;
	}

	public String getJolly() {
		return jolly;
	}

	public String getSuperstar() {
		return superstar;
	}

	public String[] getEstratti() {
		return estratti;
	}

	public void setEstratti(String[] estratti) {
		this.estratti = estratti;
	}

	@Persistent
    private String jolly;

    @Persistent
    private String superstar;

    @Persistent
    private String nestrazione;

    public String getNestrazione() {
		return nestrazione;
	}

	public void setNestrazione(String nestrazione) {
		this.nestrazione = nestrazione;
	}

	@Persistent
    private String[] estratti = new String[6];

	public void setDataEstrazione(String dataestrazione) {
		this.dataestrazione = dataestrazione;
	}


	public void setJolly(String jolly) {
		this.jolly = jolly;
	}

	public void setSuperstar(String superstar) {
		this.superstar = superstar;
	}

    @Persistent
    private String giornoEstrazione;

    public String getGiornoEstrazione() {
		return giornoEstrazione.substring(0, 3);
	}

	public void setGiornoEstrazione(String giornoEstrazione) {
		this.giornoEstrazione = giornoEstrazione;
	}
	
	public String getDescrShort() {
		StringBuffer sb = new StringBuffer();
		for (String estr : estratti) {
			sb.append(estr);
			sb.append(", ");
		}	

		sb.append("j: "+this.jolly+", ");
		sb.append("s: "+this.superstar);
		return sb.toString();
	}
	
	public String getDescrizione() {
		StringBuffer sb = new StringBuffer();
		for (String estr : estratti) {
			sb.append(estr);
			sb.append(", ");
		}	
		sb = sb.deleteCharAt(sb.length()-1);
		sb = sb.deleteCharAt(sb.length()-1);

		sb.append("<br>");
		sb.append("Jolly: "+this.jolly+"<br>");
		sb.append("Superstar: "+this.superstar+"<br><br>");

		sb.append("Montepremi: "+this.montepremi_totale+"<br>");
		sb.append("6 punti: "+this.vincita6+" ("+this.quanti6+")<br>");
		sb.append("5+ punti: "+this.vincita51+" ("+this.quanti51+")<br>");
		sb.append("5 punti: "+this.vincita5+" ("+this.quanti5+")<br>");
		sb.append("4 punti: "+this.vincita4+" ("+this.quanti4+")<br>");
		sb.append("3 punti: "+this.vincita3+" ("+this.quanti3+")<br>");

		return sb.toString();
	}

	public String getDescrizioneTxt() {
		StringBuffer sb = new StringBuffer();
		
		sb.append("Estrazione, quote e montepremi del concorso n. "+this.getNestrazione()+" del "+this.getDataestrazione()+".\n\n\n\n");

		sb.append("\n");
		sb.append("Numeri estratti: ");
		for (String estr : estratti) {
			sb.append(estr);
			sb.append(", ");
		}	
		sb = sb.deleteCharAt(sb.length()-1);
		sb = sb.deleteCharAt(sb.length()-1);

		sb.append("\n");
		sb.append("Jolly: "+this.jolly+"\n");
		sb.append("Superstar: "+this.superstar+"\n\n");

		sb.append("Montepremi: "+this.montepremi_totale+"\n");
		sb.append("6 punti: "+this.vincita6+" ("+this.quanti6+")\n");
		sb.append("5+ punti: "+this.vincita51+" ("+this.quanti51+")\n");
		sb.append("5 punti: "+this.vincita5+" ("+this.quanti5+")\n");
		sb.append("4 punti: "+this.vincita4+" ("+this.quanti4+")\n");
		sb.append("3 punti: "+this.vincita3+" ("+this.quanti3+")\n");

		return sb.toString();
	}
	
	public String montepremi_corr = "";
	public String getMontepremi_corr() {
		return montepremi_corr;
	}

	public void setMontepremi_corr(String montepremiCorr) {
		montepremi_corr = montepremiCorr;
	}

	public String getMontepremi_prec() {
		return montepremi_prec;
	}

	public void setMontepremi_prec(String montepremiPrec) {
		montepremi_prec = montepremiPrec;
	}

	public String getMontepremi_totale() {
		return montepremi_totale;
	}

	public void setMontepremi_totale(String montepremiTotale) {
		montepremi_totale = montepremiTotale;
	}

	public String getVincita6() {
		return vincita6;
	}

	public void setVincita6(String vincita6) {
		this.vincita6 = vincita6;
	}

	public String getQuanti6() {
		return quanti6;
	}

	public void setQuanti6(String quanti6) {
		this.quanti6 = quanti6;
	}

	public String getVincita51() {
		return vincita51;
	}

	public void setVincita51(String vincita51) {
		this.vincita51 = vincita51;
	}

	public String getQuanti51() {
		return quanti51;
	}

	public void setQuanti51(String quanti51) {
		this.quanti51 = quanti51;
	}

	public String getVincita5() {
		return vincita5;
	}

	public void setVincita5(String vincita5) {
		this.vincita5 = vincita5;
	}

	public String getQuanti5() {
		return quanti5;
	}

	public void setQuanti5(String quanti5) {
		this.quanti5 = quanti5;
	}

	public String getVincita4() {
		return vincita4;
	}

	public void setVincita4(String vincita4) {
		this.vincita4 = vincita4;
	}

	public String getQuanti4() {
		return quanti4;
	}

	public void setQuanti4(String quanti4) {
		this.quanti4 = quanti4;
	}

	public String getVincita3() {
		return vincita3;
	}

	public void setVincita3(String vincita3) {
		this.vincita3 = vincita3;
	}

	public String getQuanti3() {
		return quanti3;
	}

	public void setQuanti3(String quanti3) {
		this.quanti3 = quanti3;
	}

	public String montepremi_prec = "";
	public String montepremi_totale = "";

	public String vincita6 = "";
	public String quanti6 = "";
	public String vincita51 = "";
	public String quanti51 = "";
	public String vincita5 = "";
	public String quanti5 = "";
	public String vincita4 = "";
	public String quanti4 = "";
	public String vincita3 = "";
	public String quanti3 = "";

	private DettaglioVincite dettaglioVincite;

	
	public String createXML() throws IOException {
	    SAXBuilder builder = new SAXBuilder();
	
	    XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
	    Document doc = new Document();
	    Element root = new Element("superenalottodroid");
	    root.setAttribute("version", Versione.getVersione());
	    //kml.setAttribute("xmlns","http://www.opengis.net/kml/2.2");
	    doc.addContent(root);
	    Element estrazioneElem = new Element("estrazione");
	    estrazioneElem.setAttribute("date", this.getGiornoEstrazione()+" "+this.getDataestrazione());
	    root.addContent(estrazioneElem);
	    
	    for (int i=0; i<this.getEstratti().length; i++) {
	    	Element estrattoElem = new Element("estratto");
	    	estrattoElem.setText(this.getEstratti()[i]);
	    	estrazioneElem.addContent(estrattoElem);
	    }
	
	    Element jollyElem = new Element("jolly");
	    jollyElem.setText(this.getJolly());
		estrazioneElem.addContent(jollyElem);
	    
		Element superstarElem = new Element("superstar");
		superstarElem.setText(this.getSuperstar());
		estrazioneElem.addContent(superstarElem);
		{
	    	Element montepremi = new Element("montepremi");
	    	estrazioneElem.addContent(montepremi);
	    	Element attuale = new Element("attuale");
	    	attuale.setText(this.montepremi_corr);
	    	montepremi.addContent(attuale);
	    	Element precedente = new Element("precedente");
	    	precedente.setText(this.montepremi_prec);
	    	montepremi.addContent(precedente);
	    	Element totale = new Element("totale");
	    	totale.setText(this.montepremi_totale);
	    	montepremi.addContent(totale);
		}
	
		{
	    	Element vincite = new Element("vincite");
	    	estrazioneElem.addContent(vincite);
	    	
	    	Element sei = new Element("sei");
	    	sei.setAttribute("n", this.quanti6);
	    	sei.setAttribute("euro", this.vincita6);
	    	vincite.addContent(sei);
	    	
	    	Element cinquepiuuno = new Element("cinquepiuuno");
	    	cinquepiuuno.setAttribute("n", this.quanti51);
	    	cinquepiuuno.setAttribute("euro", this.vincita51);
	    	vincite.addContent(cinquepiuuno);
	    	
	    	Element cinque = new Element("cinque");
	    	cinque.setAttribute("n", this.quanti5);
	    	cinque.setAttribute("euro", this.vincita5);
	    	vincite.addContent(cinque);
	    	
	    	Element quattro = new Element("quattro");
	    	quattro.setAttribute("n", this.quanti4);
	    	quattro.setAttribute("euro", this.vincita4);
	    	vincite.addContent(quattro);
	    	
	    	Element tre = new Element("tre");
	    	tre.setAttribute("n", this.quanti3);
	    	tre.setAttribute("euro", this.vincita3);
	    	vincite.addContent(tre);
		}
		{
	    	Element dettaglioVinciteElem = new Element("dettaglioVincite");
	    	estrazioneElem.addContent(dettaglioVinciteElem);
			for(Vincita vinc : this.dettaglioVincite.getVincite()) {
		    	Element vincitaElem = new Element("vincita");
		    	vincitaElem.setAttribute("descr", vinc.getQuota().getCategoriaVincita().getDescrizione());
		    	vincitaElem.setAttribute("tipo", vinc.getQuota().getCategoriaVincita().getTipo());
		    	vincitaElem.setAttribute("n", ""+vinc.getNumero());
		    	vincitaElem.setAttribute("euro", Utils.parseValuta(vinc.getQuota().getImporto()));
		    	dettaglioVinciteElem.addContent(vincitaElem);
	    	}	    	
		}
	    
	    StringWriter sw = new StringWriter();
	    outputter.output(doc, sw);
	    return sw.toString();
	}

	public void setDettaglioVincite(DettaglioVincite dettaglioVincite) {
		// TODO Auto-generated method stub
		this.dettaglioVincite = dettaglioVincite;
	}
}
