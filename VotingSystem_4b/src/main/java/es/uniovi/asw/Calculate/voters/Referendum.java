package es.uniovi.asw.Calculate.voters;

import java.util.HashMap;
import java.util.Map;

import es.uniovi.asw.modelo.Voto;

public class Referendum {
	private Map<String, Integer> recuento;
	private int nVoto;
	
	public Referendum(){
		this.recuento = new HashMap<String,Integer>();	
		}
	
	public void actualizar(Voto vote){
		String dato = vote.getPartidoPolitico();//TODO generalizar
		try{
			recuento.put(dato
					,recuento.get(dato).intValue()+1);
		}
		catch(NullPointerException e){
			recuento.put(dato,1);
		}finally{
			nVoto++;
		}
	}
	
	public Map<String,Integer> getResult(){
		return recuento;
	}
	
	public Map<String,Double> getPercents(){
		Map<String,Double> porcentajes = new HashMap<String, Double>();
		for(String k: recuento.keySet()){
			porcentajes.put(k, (recuento.
					get(k).doubleValue()/nVoto)*100);
		}
		return porcentajes;
	}
	
	public int nVoto(){
		return nVoto;
	}
}
