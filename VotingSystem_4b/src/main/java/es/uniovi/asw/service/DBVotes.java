package es.uniovi.asw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import es.uniovi.asw.dbupdate.VotoRepository;
import es.uniovi.asw.modelo.Voto;


public class DBVotes implements GetVotes{
	
	@Autowired
	private VotoRepository repository;
	
	
	public DBVotes(VotoRepository repository){
		this.repository=repository;
	}

	@Override
	public List<Voto> getVotes() {
		return repository.findAll();
	}

	@Override
	public void updateVote(Long id) {
		Voto voto = repository.findOne(id);
		voto.setContabilizado(true);
		repository.save(voto);
		}

}
