package es.uniovi.asw.Calculate.info;

import java.util.Map;

import es.uniovi.asw.Calculate.voters.ReferendumObject;

public class Info implements ObjectInfo {

	private ReferendumObject resultados;

	@Override
	public void actualiza(ReferendumObject resultados) {
		this.resultados = resultados;
	}

	@Override
	public ReferendumObject getResultado() {
		return resultados;
	}

}
