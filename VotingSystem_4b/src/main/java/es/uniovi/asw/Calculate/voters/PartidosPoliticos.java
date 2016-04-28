package es.uniovi.asw.Calculate.voters;

import java.util.HashMap;
import java.util.Map;

import es.uniovi.asw.modelo.PartidoPolitico;

public class PartidosPoliticos {
	private Map<PartidoPolitico, Integer> recuento;
	private int nVoto;

	public PartidosPoliticos() {
		this.recuento = new HashMap<PartidoPolitico, Integer>();
	}

	
}
