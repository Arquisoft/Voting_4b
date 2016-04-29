package es.uniovi.asw.Calculate.info;

import java.util.Map;

import es.uniovi.asw.Calculate.voters.ReferendumObject;

public class Info implements ObjectInfo {

	private Map<String, Integer> resultados;

	@Override
	public void actualiza(Map<String, Integer> resultados) {
		this.resultados = resultados;
	}

	@Override
	public Map<String, Integer> getResultado() {
		return resultados;
	}

}
