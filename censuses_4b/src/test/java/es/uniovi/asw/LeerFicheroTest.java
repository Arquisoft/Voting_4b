package es.uniovi.asw;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import es.uniovi.asw.dbupdate.VoterRepository;
import es.uniovi.asw.factoria.ParserFactory;
import es.uniovi.asw.modelo.Voter;
import es.uniovi.asw.parser.impl.LeerFicheroXlsx;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = LoadUsers.class)
public class LeerFicheroTest {

	@Autowired
	VoterRepository vr;
	List<Voter> votantes = new ArrayList<Voter>();
	
	@Before
	public void setUp() throws Exception {
		votantes = vr.findAll(); //Almaceno la base de datos generada de LoadUsers.class
		vr.deleteAll(); //borro toda la base de datos creada al ejecutar LoadUsers.class que inicializa VoterRepository.
	}
	
	@Test
	public void testLeerFichero() throws IOException {
		LeerFicheroXlsx lf = new LeerFicheroXlsx();
		ArrayList<Voter> votantes = new ArrayList<Voter>();
		Voter v1 = new Voter("Juan Torres Pardo", "juan@correo.es", "90500084Y", 234);
		Voter v2 = new Voter("Luis López Fernando", "Luis@correo.es", "19160962F", 345);
		Voter v3 = new Voter("Ana Torres Pardo", "Ana@correo.es", "09940449X", 456);
		votantes.add(v1);
		votantes.add(v2);
		votantes.add(v3);

		lf.readCensus("test.xlsx");
		assertEquals(lf.getVotantes().toString(), votantes.toString());
		
	}

	@Test
	public void testGuardarDatosBDD() {
		
		LeerFicheroXlsx lf = (LeerFicheroXlsx)ParserFactory.getReadCensusXlsx(vr);
		
		List<Voter> votantes = new ArrayList<Voter>();
		Voter v1 = new Voter("Juan Torres Pardo", "juan@correo.es", "90500084Y", 234, "", "", false);
		Voter v2 = new Voter("Luis López Fernando", "Luis@correo.es", "19160962F", 345, "", "", false);
		Voter v3 = new Voter("Ana Torres Pardo", "Ana@correo.es", "09940449X", 456, "", "", false);
		votantes.add(v1);
		votantes.add(v2);
		votantes.add(v3);
		
		
		lf.readCensus("test.xlsx");
		lf.insert();
	
		assertEquals(v1.getNombre(), vr.findByNif("90500084Y").getNombre());
		assertEquals(v1.getEmail(), vr.findByNif("90500084Y").getEmail());
		assertEquals(v1.getCodigoColegio(), vr.findByNif("90500084Y").getCodigoColegio());
		
		assertEquals(v2.getNombre(), vr.findByNif("19160962F").getNombre());
		assertEquals(v2.getEmail(), vr.findByNif("19160962F").getEmail());
		assertEquals(v2.getCodigoColegio(), vr.findByNif("19160962F").getCodigoColegio());
		
		assertEquals(v3.getNombre(), vr.findByNif("09940449X").getNombre());
		assertEquals(v3.getEmail(), vr.findByNif("09940449X").getEmail());
		assertEquals(v3.getCodigoColegio(), vr.findByNif("09940449X").getCodigoColegio());
		
		

	}
	
	/**
	 * Método que vuelve almacenar los datos que 
	 * había en la bd al cargar LoadUsers.class
	 */
	@After
	public void testAñadirVotantesEliminados(){
		
		vr.deleteAll(); //borro los datos creados en el test
		assertEquals(0, vr.count());
		
		
		for (Voter voter : votantes) {
			vr.save(voter); //inserto los datos creados en LoadUsers.class
		}
		
		assertEquals(votantes.toString(), vr.findAll().toString());
		
	}
}
