package es.uniovi.asw.DBManagement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import es.uniovi.asw.DBManagement.domain.Vote;

public class DBVotes implements GetVotes{
	
	@Autowired
	private DBManagement repository;
	
	
	public DBVotes(DBManagement repository){
		this.repository=repository;
	}

	@Override
	public List<Vote> getVotes() {
		return repository.findAll();
	}

	@Override
	public void updateVote(Long id) {
		Vote voto = repository.findOne(id);
		voto.setContabilizado(true);
		repository.save(voto);
		}

}
