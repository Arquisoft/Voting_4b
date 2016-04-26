package es.uniovi.asw.dbupdate;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import es.uniovi.asw.modelo.Voter;

public interface VoterRepository extends CrudRepository<Voter, Long> {

	Voter findByNif(String nif);
	Voter findByUsuario(String usuario);
	Voter findByEmailAndClave(String email, String clave);
	List<Voter> findAll();
	
	@Modifying
	@Query("update Voter v set v.ejercioDerechoAlVoto = ?1 where v.nif = ?2")
	void setEjercioDerechoAlVotoFor(boolean ejercioDerechoAlVoto, String nif);
}
