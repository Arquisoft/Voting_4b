package es.uniovi.asw.dbupdate;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import es.uniovi.asw.modelo.ColegioElectoral;

public interface ColegioRepository extends CrudRepository<ColegioElectoral, Long> {
	
	ColegioElectoral findByCodigoColegio(int codigoColegio);
	List<ColegioElectoral> findAll();
}
