package it.davidecremonesi.superenalotto.dto;

public class Risposta {

	long dataEstrazione;
	Concorso concorso;
	CombinazioneVincente combinazioneVincente;
	Montepremi montepremi;
	DettaglioVincite dettaglioVincite;
	
	
	public DettaglioVincite getDettaglioVincite() {
		return dettaglioVincite;
	}

	public void setDettaglioVincite(DettaglioVincite dettaglioVincite) {
		this.dettaglioVincite = dettaglioVincite;
	}

	public Montepremi getMontepremi() {
		return montepremi;
	}

	public void setMontepremi(Montepremi montepremi) {
		this.montepremi = montepremi;
	}

	public CombinazioneVincente getCombinazioneVincente() {
		return combinazioneVincente;
	}

	public void setCombinazioneVincente(CombinazioneVincente combinazioneVincente) {
		this.combinazioneVincente = combinazioneVincente;
	}

	public Concorso getConcorso() {
		return concorso;
	}

	public void setConcorso(Concorso concorso) {
		this.concorso = concorso;
	}

	public void setDataEstrazione(long dataEstrazione) {
		this.dataEstrazione = dataEstrazione;
	}

	public long getDataEstrazione() {
		return dataEstrazione;
	}

}
