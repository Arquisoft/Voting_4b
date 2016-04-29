package es.uniovi.asw.Calculate.info;

import java.util.Map;

import es.uniovi.asw.Calculate.voters.ReferendumObject;

public interface ObjectInfo {

	public void actualiza(Map<String, Integer> map);

	public Map<String, Integer> getResultado();

}
