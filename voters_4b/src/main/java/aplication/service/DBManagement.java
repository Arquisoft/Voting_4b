package aplication.service;

import org.springframework.data.repository.CrudRepository;

import aplication.domain.User;

public interface DBManagement extends CrudRepository<User, Long> {

	User findByEmailAndPassword(String email, String password);
	

}
