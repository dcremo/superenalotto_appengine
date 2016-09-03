package it.davidecremonesi.superenalotto.parserHelpers;

public class Quota {
	public CategoriaVincita categoriaVincita;
	public CategoriaVincita getCategoriaVincita() {
		return categoriaVincita;
	}
	public void setCategoriaVincita(CategoriaVincita categoriaVincita) {
		this.categoriaVincita = categoriaVincita;
	}
	public long getImporto() {
		return importo;
	}
	public void setImporto(long importo) {
		this.importo = importo;
	}
	long importo;
}
/*
"quota":{
"categoriaVincita":{
   "tipo":"14",
   "descrizione":"Punti 6"
},
"importo":0
},
*/