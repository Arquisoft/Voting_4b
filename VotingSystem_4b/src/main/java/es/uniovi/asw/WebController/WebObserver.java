package es.uniovi.asw.WebController;

import java.util.Map;

import es.uniovi.asw.Calculate.voters.ReferendumObject;
import es.uniovi.asw.controller.Main;

/**
 * Created by Daplerio on 7/4/16.
 */
public class WebObserver implements Observer {

	public void actualizar(ReferendumObject resultados) {

		Main.resultados = resultados;
	}

	/*private int[] prepararArray(Map<String, Double> resultados) {
		int[] array = new int[resultados.size()];
		for (int i = 0; i < resultados.size(); i++) {
			array[i] = (int) (resultados.get(i) * 100);
		}
		return array;
	}*/

}
