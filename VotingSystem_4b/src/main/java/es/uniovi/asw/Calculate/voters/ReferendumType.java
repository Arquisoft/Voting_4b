package es.uniovi.asw.Calculate.voters;

import java.util.List;
import java.util.Map;

import es.uniovi.asw.modelo.Voto;
import es.uniovi.asw.service.GetVotes;

public class ReferendumType implements VotersType {

	private Referendum tipo;

	public ReferendumType(List<Voto> votes) {
		tipo = new Referendum();
	}

	@Override
	public Map<String, Double> getResult() {
		return tipo.getPercents();
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
