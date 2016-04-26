package es.uniovi.asw.service;

import org.springframework.data.repository.CrudRepository;

import es.uniovi.asw.modelo.Voter;

public interface DBManagement extends CrudRepository<Voter, Long> {

	Voter findByEmailAndClave(String email, String clave);
	

}
