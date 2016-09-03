package it.davidecremonesi.superenalotto;

import java.io.IOException;

public abstract class ISuperEnalottoParser {
	public abstract Estrazione getEstrazione(int anno, int n) throws Exception;

	public String getHTML() throws IOException {
		String retval = "";
		try {
			retval = getEstrazione(0, 0).createXML();
		} catch (Exception e) {
			retval = "<errore>"+e.toString()+"</errore>";
		}
		return retval;
	}
}