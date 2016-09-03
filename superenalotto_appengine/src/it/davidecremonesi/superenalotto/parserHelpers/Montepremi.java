package it.davidecremonesi.superenalotto.parserHelpers;

public class Montepremi {
	long riportoMontepremiPrecedente;
	public long getRiportoMontepremiPrecedente() {
		return riportoMontepremiPrecedente;
	}
	public void setRiportoMontepremiPrecedente(long riportoMontepremiPrecedente) {
		this.riportoMontepremiPrecedente = riportoMontepremiPrecedente;
	}
	public long getRiportoMontepremiDecreto() {
		return riportoMontepremiDecreto;
	}
	public void setRiportoMontepremiDecreto(long riportoMontepremiDecreto) {
		this.riportoMontepremiDecreto = riportoMontepremiDecreto;
	}
	public long getMontepremiConcorso() {
		return montepremiConcorso;
	}
	public void setMontepremiConcorso(long montepremiConcorso) {
		this.montepremiConcorso = montepremiConcorso;
	}
	public long getMontepremiTotale() {
		return montepremiTotale;
	}
	public void setMontepremiTotale(long montepremiTotale) {
		this.montepremiTotale = montepremiTotale;
	}
	long riportoMontepremiDecreto;
	long montepremiConcorso;
	long montepremiTotale;
}
/*
"montepremi":{
"riportoMontepremiPrecedente":6118827534,
"riportoMontepremiDecreto":258504,
"montepremiConcorso":463286100,
"montepremiTotale":6582372138
},
*/