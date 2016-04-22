package es.uniovi.asw;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import es.uniovi.asw.pageobjects.PO_colegio;
import es.uniovi.asw.pageobjects.PO_elecciones;
import es.uniovi.asw.pageobjects.PO_voto;
import es.uniovi.asw.utils.SeleniumUtils;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SeleniumTest {
	
	WebDriver driver;
    List<WebElement> elementos = null;

    
    @Before
    public void run() {

	driver = new FirefoxDriver();
	driver.manage().window().maximize();
	
	//vamos a la página
	driver.get("http://localhost:8080/");
	
	

    }

    @After
    public void end() {
	// Cerramos el navegador
	driver.quit();
    }

    @Test
    public void testVotarNulo() {
    	// Buscar el enlace con id votar, y pinchar en él
    	driver.get("http://localhost:8080/votar.html");

    	// Esperamos a que se cargue la pagina de votar
    	SeleniumUtils.EsperaCargaPagina(driver, "text", "Votar", 10);

    	// Vamos a rellenar el formulario
    	new PO_voto().rellenaFormulario(driver, "Voto nulo");

    	// Esperamos a que se cargue la pagina de votar
    	SeleniumUtils.EsperaCargaPagina(driver, "text", "Votar", 10);

    	// Comprobamos que aparezca el mensaje en la página
    	SeleniumUtils.textoPresentePagina(driver, "Ha votado correctamente: voto nulo");
    }
    
    @Test
    public void testVotarBlanco() {
    	// Buscar el enlace con id votar, y pinchar en él
    	driver.get("http://localhost:8080/votar.html");

    	// Esperamos a que se cargue la pagina de votar
    	SeleniumUtils.EsperaCargaPagina(driver, "text", "Votar", 10);

    	// Vamos a rellenar el formulario
    	new PO_voto().rellenaFormulario(driver, "");

    	// Esperamos a que se cargue la pagina de votar
    	SeleniumUtils.EsperaCargaPagina(driver, "text", "Votar", 10);

    	// Comprobamos que aparezca el mensaje en la página
    	SeleniumUtils.textoPresentePagina(driver, "Ha votado correctamente: voto en blanco");
    }
    
    @Test
    public void testVotarPartido() {
    	// Buscar el enlace con id votar, y pinchar en él
    	driver.get("http://localhost:8080/votar.html");

    	// Esperamos a que se cargue la pagina de votar
    	SeleniumUtils.EsperaCargaPagina(driver, "text", "Votar", 10);

    	// Vamos a rellenar el formulario
    	new PO_voto().rellenaFormulario(driver, "PP");

    	// Esperamos a que se cargue la pagina de votar
    	SeleniumUtils.EsperaCargaPagina(driver, "text", "Votar", 10);

    	// Comprobamos que aparezca el mensaje en la página
    	SeleniumUtils.textoPresentePagina(driver, "Ha votado correctamente");
    }
    
    
    
    @Test
    public void testAddColegio() {
    	// Buscar el enlace con id modificar elecciones, y pinchar en él
    	driver.get("http://localhost:8080/modificar_elecciones.html");

    	// Esperamos a que se cargue la pagina de modificar elecciones
    	SeleniumUtils.EsperaCargaPagina(driver, "text", "Modificar elecciones", 10);

    	//Pinchamos en el enlace Añadir colegio electoral
    	By boton = By.className("boton_add_colegio");
 		driver.findElement(boton).click();	  
 			
 		// Esperamos a que se cargue la pagina de añadir colegio
    	SeleniumUtils.EsperaCargaPagina(driver, "text", "Añadir colegio electoral", 10);
 		
    	// Vamos a rellenar el formulario
    	new PO_colegio().rellenaFormulario(driver, "10000", "Asturias", "Principado de Asturias");

    	// Esperamos a que se cargue la pagina de añadir colegio
    	SeleniumUtils.EsperaCargaPagina(driver, "text", "Añadir colegio electoral", 10);

    	// Comprobamos que aparezca el mensaje en la página
    	SeleniumUtils.textoPresentePagina(driver, "Se ha añadido el nuevo colegio electoral con código:");
    }
    
    
    @Test
    public void testAddElecciones() {
    	// Buscar el enlace con id modificar elecciones, y pinchar en él
    	driver.get("http://localhost:8080/modificar_elecciones.html");

    	// Esperamos a que se cargue la pagina de modificar elecciones
    	SeleniumUtils.EsperaCargaPagina(driver, "text", "Modificar elecciones", 10);

    	// Vamos a rellenar el formulario
    	new PO_elecciones().rellenaFormulario(driver, "Referendum", "02/06/2016 09:00", "02/06/2016 20:00", "opciones");

    	// Esperamos a que se cargue la pagina de modificar elecciones
    	SeleniumUtils.EsperaCargaPagina(driver, "text", "Modificar elecciones", 10);

    	// Comprobamos que aparezca el mensaje en la página
    	SeleniumUtils.textoPresentePagina(driver, "Se han convocado las nuevas elecciones con nombre:");
    }
    

    

}
