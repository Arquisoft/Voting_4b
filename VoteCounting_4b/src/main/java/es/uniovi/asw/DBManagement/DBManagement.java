package es.uniovi.asw.DBManagement;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import es.uniovi.asw.DBManagement.domain.Vote;
import es.uniovi.asw.DBManagement.domain.PollingStation;
import es.uniovi.asw.DBManagement.domain.Candidate;
import java.lang.Long;

@Component("DBManagement")
public interface DBManagement extends CrudRepository<Vote, Long> {

	List<Vote> findByColegio(PollingStation colegio);

	List<Vote> findAll();

	List<Vote> findByCandidatura(Candidate candidatura);

}
