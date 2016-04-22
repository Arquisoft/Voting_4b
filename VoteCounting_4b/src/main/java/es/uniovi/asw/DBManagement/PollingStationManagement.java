package es.uniovi.asw.DBManagement;

import org.springframework.data.repository.CrudRepository;

import es.uniovi.asw.DBManagement.domain.PollingStation;
import java.lang.String;
import java.util.List;
import java.lang.Long;

public interface PollingStationManagement extends CrudRepository<PollingStation, Long>{
	
	List<PollingStation> findByCircunscripcion(String circunscripcion);
	
	List<PollingStation> findByComunidadAutonoma(String comunidadautonoma);
	
	List<PollingStation> findByCodigo(Long codigo);
	
	

}
