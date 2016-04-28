package es.uniovi.asw.Calculate.info;

import java.util.Map;

public class Info implements ObjectInfo {

	private Map<String, Double> resultados;

	@Override
	public void actualiza(Map<String, Double> resultados) {
		this.resultados = resultados;
	}

	@Override
	public Map<String, Double> getResultado() {
		return resultados;
	}

}
