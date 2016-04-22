package es.uniovi.asw.DBManagement;

import java.util.List;

import es.uniovi.asw.DBManagement.domain.Vote;

public interface GetVotes {

	List<Vote> getVotes();

	void updateVote(Long id);

}
