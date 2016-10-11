package it.davidecremonesi.superenalotto.dto;

public class Vincita {
	Quota quota;
	public Quota getQuota() {
		return quota;
	}
	public void setQuota(Quota quota) {
		this.quota = quota;
	}
	public long getNumero() {
		return numero;
	}
	public void setNumero(long numero) {
		this.numero = numero;
	}
	public long getTotale() {
		return totale;
	}
	public void setTotale(long totale) {
		this.totale = totale;
	}
	long numero;
	long totale;
}

/*
"quota":{
"categoriaVincita":{
   "tipo":"14",
   "descrizione":"Punti 6"
},
"importo":0
},
"numero":"0",
"totale":0
*/