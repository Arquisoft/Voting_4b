package es.uniovi.asw.WebController;

import java.util.Map;

import es.uniovi.asw.controller.Main;

/**
 * Created by Daplerio on 7/4/16.
 */
public class WebObserver implements Observer {

	public void actualizar(Map<String, Integer> map) {

		Main.resultados = map;
	}

}
