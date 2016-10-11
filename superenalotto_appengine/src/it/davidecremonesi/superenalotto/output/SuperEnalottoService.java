package it.davidecremonesi.superenalotto.output;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import it.davidecremonesi.superenalotto.Versione;
import it.davidecremonesi.superenalotto.persistence.Estrazione;
import it.davidecremonesi.superenalotto.persistence.EstrazioneService;
import it.davidecremonesi.superenalotto.persistence.EstrazioniCache;

public class SuperEnalottoService {
	public String getVersion() {
		return Versione.getVersione();
	}
	
	public Estrazione getUltimaEstrazione() {
		Estrazione retval = null;
	    String dataUltima = EstrazioniCache.getDataUltimaEstrazione();
		List<Estrazione> lista = EstrazioneService.caricaWhereData(dataUltima);
		retval = lista.size()>0 ? lista.get(0) : null;
		return retval;
	}

	public Vector<Estrazione> getTutteEstrazioni() {
	    return EstrazioniCache.getEstrazioni();
	}

	public Estrazione getEstrazione(Integer anno, String progr) {
	    return EstrazioniCache.getEstrazione(Estrazione.buildEstrazioneUID(anno, progr));
	}

	public Vector<Estrazione> getEstrazioniIn(Integer annoFrom, Integer progrFrom, Integer annoTo, Integer progrTo) {
		List<Estrazione> all = getTutteEstrazioni();
		Vector<Estrazione> retval = new Vector<Estrazione>();
		for (Iterator<Estrazione> iterator = all.iterator(); iterator.hasNext();) {
			Estrazione estrazione = iterator.next();
			if (estrazione.fallsBetween(annoFrom, progrFrom, annoTo, progrTo)) {
				retval.add(estrazione);
			}
		}
		return retval;
	}
}
