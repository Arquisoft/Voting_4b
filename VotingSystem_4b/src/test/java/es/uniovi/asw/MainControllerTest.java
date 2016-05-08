package es.uniovi.asw;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest({ "server.port=0" })
public class MainControllerTest {

  @Autowired
  private WebApplicationContext context;

  private MockMvc mvc;

  @Before
  public void setUp() throws Exception {
    mvc = MockMvcBuilders.webAppContextSetup(context).build();
  }

  @Test
  public void testLanding() throws Exception {
    mvc.perform(get("/")).andExpect(status().isOk()).andExpect(content().string(containsString("Voting")));
  }

  
  @Test
  public void testVotarSinEleccionesSinInicio() throws Exception {
	//mvc.perform(post("/WelcomePage").param("field1", "pamela@gmail.com").param("field2", "patata")).andExpect(status().isOk()).andExpect(content().string(containsString("Voting System")));
    mvc.perform(get("/votar")).andExpect(status().isOk()).andExpect(content().string(containsString("Se ha producido un error, por favor, inténtelo de nuevo.")));
  }
  
  
  @Test
  public void testCrearEleccionesSinInicioSesion() throws Exception {
	  
	//mvc.perform(post("/WelcomePage").param("field1", "junta@gmail.com").param("field2", "junta")).andExpect(status().isOk()).andExpect(content().string(containsString("Tipo de elecciones")));
    mvc.perform(get("/modificar_elecciones")).andExpect(status().isOk()).andExpect(content().string(containsString("Se ha producido un error, por favor, inténtelo de nuevo.")));
//    mvc.perform(get("/modificar_elecciones")).andExpect(status().isOk()).andExpect(content().string(containsString("Nombre de las elecciones")));
//    mvc.perform(get("/modificar_elecciones")).andExpect(status().isOk()).andExpect(content().string(containsString("NUEVO COLEGIO ELECTORAL")));
//    mvc.perform(get("/modificar_elecciones")).andExpect(status().isOk()).andExpect(content().string(containsString("CREAR")));
  }
  
  @Test
  public void testAddColegioSinInicio() throws Exception {
	//mvc.perform(post("/WelcomePage").param("field1", "junta@gmail.com").param("field2", "junta")).andExpect(status().isOk()).andExpect(content().string(containsString("Tipo de elecciones")));
    mvc.perform(get("/add_colegio")).andExpect(status().isOk()).andExpect(content().string(containsString("Se ha producido un error, por favor, inténtelo de nuevo.")));
//    mvc.perform(get("/add_colegio")).andExpect(status().isOk()).andExpect(content().string(containsString("Circunscripción")));
//    mvc.perform(get("/add_colegio")).andExpect(status().isOk()).andExpect(content().string(containsString("Comunidad Autónoma")));
//    mvc.perform(get("/add_colegio")).andExpect(status().isOk()).andExpect(content().string(containsString("AÑADIR")));
  }
  

  
  
  
  
  
  
}