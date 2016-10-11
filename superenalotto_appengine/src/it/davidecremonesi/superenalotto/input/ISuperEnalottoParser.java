package it.davidecremonesi.superenalotto.input;

import java.io.IOException;

import it.davidecremonesi.superenalotto.output.EstrazioneXMLOutputter;
import it.davidecremonesi.superenalotto.persistence.Estrazione;

public abstract class ISuperEnalottoParser {
	public abstract Estrazione getEstrazione(int anno, int n) throws Exception;

	public String getHTML() throws IOException {
		String retval = "";
		try {
			Estrazione estrazione = getEstrazione(0, 0);
			retval = new EstrazioneXMLOutputter().createXML(estrazione);
		} catch (Exception e) {
			retval = "<errore>"+e.toString()+"</errore>";
		}
		return retval;
	}
}