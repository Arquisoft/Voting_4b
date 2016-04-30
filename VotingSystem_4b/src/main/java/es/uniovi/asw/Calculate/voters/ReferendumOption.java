package es.uniovi.asw.Calculate.voters;

import java.util.HashMap;
import java.util.Map;

import es.uniovi.asw.modelo.Voto;

public class ReferendumOption implements OptionType {
	private Map<String, Integer> recuento;
	private int nVoto;
	
	public ReferendumOption(){
		this.recuento = new HashMap<String,Integer>();	
		}
	
	@Override
	public void contarVoto(Voto vote){
		String dato = vote.getOpcion();
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
	
	@Override
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
	
	@Override
	public int nVoto(){
		return nVoto;
	}

}
