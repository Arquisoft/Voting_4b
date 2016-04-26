package es.uniovi.asw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Component;

import es.uniovi.asw.dbupdate.VoterRepository;
import es.uniovi.asw.modelo.ServerResponse;
import es.uniovi.asw.modelo.Voter;

@Component("voterAccess")
public class VoterAcces implements GetVoterInfo, ChangePassword, ChangeVotar {

	@Autowired
	private VoterRepository repository;

	public VoterAcces() {
	}

	public VoterAcces(VoterRepository userRepository) {
		this.repository = userRepository;

	}

	@Override
	public ServerResponse getVoter(String email, String clave) {
		Voter user = this.repository.findByEmailAndClave(email, clave);
		if (user == null) {
			throw new ResourceNotFoundException("El usuario no se encuentra en la base de datos");
		} else {
			return new ServerResponse(user.getNombre(), user.getNif(), user.getEmail(), user.getCodigoColegio()+"");
		}
	}

	@Override
	public void updatePassword(String email, String clave, String newPassword) {
		Voter user = this.repository.findByEmailAndClave(email, clave);
		// this.repository.delete(user);
		user.setClave(newPassword);
		this.repository.save(user);

	}

	@Override
	public void updateEjercioDerechoAlVoto(boolean ejercioDerechoAlVoto,
			String usuario) {
		Voter voter = this.repository.findByUsuario(usuario);
		voter.setEjercioDerechoAlVoto(ejercioDerechoAlVoto);
		this.repository.save(voter);
		
	}

}
