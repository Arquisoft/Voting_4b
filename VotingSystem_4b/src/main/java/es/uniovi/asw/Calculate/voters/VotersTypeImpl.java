package es.uniovi.asw.Calculate.voters;

import java.util.Map;

import es.uniovi.asw.modelo.Voto;
import es.uniovi.asw.service.GetVotes;

public class VotersTypeImpl implements VotersType {

	private OptionType tipo;

	public VotersTypeImpl() {
		tipo = new ReferendumOption();
	}

	@Override
	public Map<String, Integer> getResult() {
		return tipo.getResult();
	}

	@Override
	public void actualize(GetVotes votes) {
		for (Voto v : votes.getVotes()) {
			if (!v.isContabilizado()) {
				tipo.contarVoto(v);
				votes.updateVote(v.getId());// actualizamos el estado del voto
			}
		}

	}

}
