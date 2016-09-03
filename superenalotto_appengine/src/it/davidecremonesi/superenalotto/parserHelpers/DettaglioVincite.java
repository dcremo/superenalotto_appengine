package it.davidecremonesi.superenalotto.parserHelpers;

import java.util.List;
import java.util.Vector;

public class DettaglioVincite {
	List<Vincita> vincite = new Vector<Vincita>();

	public List<Vincita> getVincite() {
		return vincite;
	}

	public void setVincite(List<Vincita> vincite) {
		this.vincite = vincite;
	}
}
/*
"dettaglioVincite":{
"vincite":[
   {
      "quota":{
         "categoriaVincita":{
            "tipo":"14",
            "descrizione":"Punti 6"
         },
         "importo":0
      },
      "numero":"0",
      "totale":0
   },
*/