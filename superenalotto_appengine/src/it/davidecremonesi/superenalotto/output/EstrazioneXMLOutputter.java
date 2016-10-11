package it.davidecremonesi.superenalotto.output;

import java.io.IOException;
import java.io.StringWriter;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import it.davidecremonesi.superenalotto.Versione;
import it.davidecremonesi.superenalotto.dto.Vincita;
import it.davidecremonesi.superenalotto.persistence.Estrazione;
import it.davidecremonesi.superenalotto.utils.Utils;

public class EstrazioneXMLOutputter {
	public String createXML(Estrazione e) throws IOException {
	
	    XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
	    Document doc = new Document();
	    Element root = new Element("superenalottodroid");
	    root.setAttribute("version", Versione.getVersione());
	    //kml.setAttribute("xmlns","http://www.opengis.net/kml/2.2");
	    doc.addContent(root);
	    Element estrazioneElem = new Element("estrazione");
	    estrazioneElem.setAttribute("date", e.getGiornoEstrazione()+" "+e.getDataestrazione());
	    root.addContent(estrazioneElem);
	    
	    for (int i=0; i<e.getEstratti().length; i++) {
	    	Element estrattoElem = new Element("estratto");
	    	estrattoElem.setText(e.getEstratti()[i]);
	    	estrazioneElem.addContent(estrattoElem);
	    }
	
	    Element jollyElem = new Element("jolly");
	    jollyElem.setText(e.getJolly());
		estrazioneElem.addContent(jollyElem);
	    
		Element superstarElem = new Element("superstar");
		superstarElem.setText(e.getSuperstar());
		estrazioneElem.addContent(superstarElem);
		{
	    	Element montepremi = new Element("montepremi");
	    	estrazioneElem.addContent(montepremi);
	    	Element attuale = new Element("attuale");
	    	attuale.setText(e.montepremi_corr);
	    	montepremi.addContent(attuale);
	    	Element precedente = new Element("precedente");
	    	precedente.setText(e.montepremi_prec);
	    	montepremi.addContent(precedente);
	    	Element totale = new Element("totale");
	    	totale.setText(e.montepremi_totale);
	    	montepremi.addContent(totale);
		}
	
		{
	    	Element vincite = new Element("vincite");
	    	estrazioneElem.addContent(vincite);
	    	
	    	Element sei = new Element("sei");
	    	sei.setAttribute("n", e.quanti6);
	    	sei.setAttribute("euro", e.vincita6);
	    	vincite.addContent(sei);
	    	
	    	Element cinquepiuuno = new Element("cinquepiuuno");
	    	cinquepiuuno.setAttribute("n", e.quanti51);
	    	cinquepiuuno.setAttribute("euro", e.vincita51);
	    	vincite.addContent(cinquepiuuno);
	    	
	    	Element cinque = new Element("cinque");
	    	cinque.setAttribute("n", e.quanti5);
	    	cinque.setAttribute("euro", e.vincita5);
	    	vincite.addContent(cinque);
	    	
	    	Element quattro = new Element("quattro");
	    	quattro.setAttribute("n", e.quanti4);
	    	quattro.setAttribute("euro", e.vincita4);
	    	vincite.addContent(quattro);
	    	
	    	Element tre = new Element("tre");
	    	tre.setAttribute("n", e.quanti3);
	    	tre.setAttribute("euro", e.vincita3);
	    	vincite.addContent(tre);
		}
		{
	    	Element dettaglioVinciteElem = new Element("dettaglioVincite");
	    	estrazioneElem.addContent(dettaglioVinciteElem);
			for(Vincita vinc : e.getDettaglioVincite().getVincite()) {
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

}
