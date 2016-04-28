package es.uniovi.asw.service;

import java.util.List;

import es.uniovi.asw.modelo.Voto;

public interface GetVotes {

	List<Voto> getVotes();

	void updateVote(Long id);

}
