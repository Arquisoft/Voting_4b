package es.uniovi.asw.service;

import es.uniovi.asw.modelo.ServerResponse;

public interface GetVoterInfo {
	ServerResponse getVoter(String email, String password);

}
