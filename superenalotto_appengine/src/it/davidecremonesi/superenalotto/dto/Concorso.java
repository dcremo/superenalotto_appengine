package it.davidecremonesi.superenalotto.dto;

/**
 * "concorso":{
      "numero":"37",
      "anno":"2016"
   },
 * @author Davide
 *
 */
public class Concorso {
	String numero;
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public int getAnno() {
		return anno;
	}
	public void setAnno(int anno) {
		this.anno = anno;
	}
	int anno;
}
