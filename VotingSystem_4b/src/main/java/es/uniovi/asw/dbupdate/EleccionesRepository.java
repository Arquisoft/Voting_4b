package es.uniovi.asw.dbupdate;

import java.util.List;

import org.springframework.data.repository.CrudRepository;




import es.uniovi.asw.modelo.Elecciones;

public interface EleccionesRepository extends CrudRepository<Elecciones, Long>  {

	Elecciones findByNombre(String nombre);
	List<Elecciones> findAll();
}
