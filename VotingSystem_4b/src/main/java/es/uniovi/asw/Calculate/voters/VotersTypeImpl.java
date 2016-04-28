package es.uniovi.asw.Calculate.voters;

import java.util.Map;

import es.uniovi.asw.modelo.Voto;
import es.uniovi.asw.service.GetVotes;

public class VotersTypeImpl implements VotersType {

	private Option tipo;

	public VotersTypeImpl() {
		tipo = new Option();
	}

	@Override
	public Map<String, Integer> getResult() {
		return tipo.getResult();
	}

	@Override
	public void actualize(GetVotes votes) {
		for (Voto v : votes.getVotes()) {
			if (!v.isContabilizado()) {

				votes.updateVote(v.getId());// actualizamos el estado del voto
			}
		}

	}

}
