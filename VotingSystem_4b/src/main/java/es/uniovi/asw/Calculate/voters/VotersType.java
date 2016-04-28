package es.uniovi.asw.Calculate.voters;

import java.util.Map;

import es.uniovi.asw.DBManagement.GetVotes;

public interface VotersType {

	public Map<String,Double> getResult();
	public void actualize(GetVotes votes);
	
}
