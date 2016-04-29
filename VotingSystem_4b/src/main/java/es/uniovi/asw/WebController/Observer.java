package es.uniovi.asw.WebController;

import java.util.Map;

import es.uniovi.asw.Calculate.voters.ReferendumObject;

/**
 * Created by Daplerio on 7/4/16.
 */
public interface Observer {

	public void actualizar(Map<String, Integer> resultados);

}
