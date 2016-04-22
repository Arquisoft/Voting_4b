package aplication.service;

import aplication.domain.ServerResponse;

public interface GetVoterInfo {
	ServerResponse getVoter(String email, String password);

}
