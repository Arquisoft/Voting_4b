package es.uniovi.asw.WebController;

import java.util.Map;

import es.uniovi.asw.WebController.Main;

/**
 * Created by Daplerio on 7/4/16.
 */
public class WebObserver implements Observer {

	public void actualizar(Map<String, Double> resultados) {

		Main.resultados = prepararArray(resultados);
	}

	private int[] prepararArray(Map<String, Double> resultados) {
		int[] array = new int[resultados.size()];
		for (int i = 0; i < resultados.size(); i++) {
			array[i] = (int)(resultados.get(i) * 100);
		}
		return array;
	}

}
