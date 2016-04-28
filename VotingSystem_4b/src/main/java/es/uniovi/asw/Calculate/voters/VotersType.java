package es.uniovi.asw.Calculate.voters;

import java.util.Map;

import es.uniovi.asw.service.GetVotes;


public interface VotersType {

	public Map<String,Double> getResult();
	public void actualize(GetVotes votes);
	
}
