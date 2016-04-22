package es.uniovi.asw;

import static org.junit.Assert.*;

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
import es.uniovi.asw.modelo.Voter;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = LoadUsers.class)
public class BDTest {

	@Autowired
	VoterRepository vr;
	List<Voter> votantes = new ArrayList<Voter>();
	
	@Before
	public void setUp() throws Exception {
		votantes = vr.findAll(); //Almaceno la base de datos generada de LoadUsers.class
		vr.deleteAll(); //borro toda la base de datos creada al ejecutar LoadUsers.class que inicializa VoterRepository.
		
		vr.save(new Voter("Monica Cueva", "monicac@gmail.com", "12151651s", 306, "monicac", "dnjwenk", false));
		vr.save(new Voter("Adrian Garcia", "adriang@gmail.com", "122651651s", 306,  "adriang", "rtg", false));
		vr.save(new Voter("Ana Bravo", "anab@gmail.com", "19861651s", 305, "anab", "fre", false));
		
	}
	
	/**
	 * Test que comprueba el correcto funcionamiento 
	 * de la base de datos a la hora de encontrar un votante por su NIF.
	 */
	@Test
	public void testFindByNif() {

		assertEquals("Monica Cueva", vr.findByNif("12151651s").getNombre());
		assertEquals("monicac@gmail.com", vr.findByNif("12151651s").getEmail());
		assertFalse(vr.findByNif("12151651s").isEjercioDerechoAlVoto());
		assertEquals("Adrian Garcia", vr.findByNif("122651651s").getNombre());
		assertEquals("adriang@gmail.com", vr.findByNif("122651651s").getEmail());
		assertFalse(vr.findByNif("122651651s").isEjercioDerechoAlVoto());
		assertEquals("Ana Bravo", vr.findByNif("19861651s").getNombre());
		assertEquals("anab@gmail.com", vr.findByNif("19861651s").getEmail());
		assertFalse(vr.findByNif("19861651s").isEjercioDerechoAlVoto());
		assertEquals(3, vr.count());
	
	
	}
	
	/**
	 * Método que prueba si elimina a los votantes correctos.
	 */
	@Test
	public void testDelete(){
		
		vr.delete(vr.findByNif("19861651s"));
		assertEquals(null, vr.findByNif("19861651s"));
		assertEquals(2, vr.count());
	}
	
	/**
	 * Método que comprueba si añade bien, nuevos votantes a la base de datos
	 * y si los encuentra correctamente. 
	 */
	@Test
	public void testInsertarNuevosVotantes(){
		
		vr.save(new Voter("Paco Cueva", "pacoc@gmail.com", "188551651s", 307, "pacoc", "efewf", false));
		vr.save(new Voter("Maria Garcia", "mariag@gmail.com", "128921651s", 304,  "mariag", "ewff", false));
		vr.save(new Voter("Luis Bravo", "luisb@gmail.com", "19869861s", 205, "luisb", "ferw", false));
		vr.save(new Voter("Monica Fernandez", "monicaf@gmail.com", "21251651s", 304, "monicaf", "wfcsww", false));
		vr.save(new Voter("Adrian Suarez", "adrians@gmail.com", "122787651s", 307,  "adrians", "dwedx", false));
		vr.save(new Voter("Ana Martinez", "anam@gmail.com", "19056651s", 206, "anam", "fre", false));
		vr.save(new Voter("Manolo Cueva", "manoloc@gmail.com", "18521651s", 204, "manoloc", "xwex", false));
		vr.save(new Voter("Sara Garcia", "sarag@gmail.com", "162451651s", 210,  "sarag", "xw33x3", false));
		vr.save(new Voter("Pedro Bravo", "pedrob@gmail.com", "16871651s", 300, "pedrob", "xw3fgrt", false));
	
		assertEquals(12, vr.count());

		assertEquals("Paco Cueva", vr.findByNif("188551651s").getNombre());
		assertEquals("pacoc@gmail.com", vr.findByNif("188551651s").getEmail());
		assertEquals(307, vr.findByNif("188551651s").getCodigoColegio());
		assertFalse(vr.findByNif("188551651s").isEjercioDerechoAlVoto());
		
		assertEquals("Maria Garcia", vr.findByNif("128921651s").getNombre());
		assertEquals("mariag@gmail.com", vr.findByNif("128921651s").getEmail());
		assertEquals(304, vr.findByNif("128921651s").getCodigoColegio());
		assertFalse(vr.findByNif("128921651s").isEjercioDerechoAlVoto());
		
		assertEquals("Luis Bravo", vr.findByNif("19869861s").getNombre());
		assertEquals("luisb@gmail.com", vr.findByNif("19869861s").getEmail());
		assertEquals(205, vr.findByNif("19869861s").getCodigoColegio());
		assertFalse(vr.findByNif("19869861s").isEjercioDerechoAlVoto());
		
		assertEquals("Monica Fernandez", vr.findByNif("21251651s").getNombre());
		assertEquals("monicaf@gmail.com", vr.findByNif("21251651s").getEmail());
		assertEquals(304, vr.findByNif("21251651s").getCodigoColegio());
		assertFalse(vr.findByNif("21251651s").isEjercioDerechoAlVoto());
		
		assertEquals("Adrian Suarez", vr.findByNif("122787651s").getNombre());
		assertEquals("adrians@gmail.com", vr.findByNif("122787651s").getEmail());
		assertEquals(307, vr.findByNif("122787651s").getCodigoColegio());
		assertFalse(vr.findByNif("122787651s").isEjercioDerechoAlVoto());
		
		assertEquals("Ana Martinez", vr.findByNif("19056651s").getNombre());
		assertEquals("anam@gmail.com", vr.findByNif("19056651s").getEmail());
		assertEquals(206, vr.findByNif("19056651s").getCodigoColegio());
		assertFalse(vr.findByNif("19056651s").isEjercioDerechoAlVoto());
		
		assertEquals("Manolo Cueva", vr.findByNif("18521651s").getNombre());
		assertEquals("manoloc@gmail.com", vr.findByNif("18521651s").getEmail());
		assertEquals(204, vr.findByNif("18521651s").getCodigoColegio());
		assertFalse(vr.findByNif("18521651s").isEjercioDerechoAlVoto());
		
		assertEquals("Sara Garcia", vr.findByNif("162451651s").getNombre());
		assertEquals("sarag@gmail.com", vr.findByNif("162451651s").getEmail());
		assertEquals(210, vr.findByNif("162451651s").getCodigoColegio());
		assertFalse(vr.findByNif("162451651s").isEjercioDerechoAlVoto());
		
		assertEquals("Pedro Bravo", vr.findByNif("16871651s").getNombre());
		assertEquals("pedrob@gmail.com", vr.findByNif("16871651s").getEmail());
		assertEquals(300, vr.findByNif("16871651s").getCodigoColegio());
		assertFalse(vr.findByNif("16871651s").isEjercioDerechoAlVoto());
		
		
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
