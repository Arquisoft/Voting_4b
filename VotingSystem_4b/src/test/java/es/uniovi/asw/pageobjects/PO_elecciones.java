package es.uniovi.asw.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PO_elecciones {

	
	public void rellenaFormulario(WebDriver driver, String nombre, String fechaInicio, String fechaFin, String opciones)
    {
 		WebElement nombreWeb = driver.findElement(By.id("nombre"));
 		nombreWeb.click();
 		nombreWeb.clear();
 		nombreWeb.sendKeys(nombre);
 		
 		WebElement fechaInicioWeb = driver.findElement(By.id("fecha_inicial"));
 		fechaInicioWeb.click();
 		fechaInicioWeb.clear();
 		fechaInicioWeb.sendKeys(fechaInicio);
 		
 		WebElement fechaFinWeb = driver.findElement(By.id("fecha_final"));
 		fechaFinWeb.click();
 		fechaFinWeb.clear();
 		fechaFinWeb.sendKeys(fechaFin);
 		
 		WebElement opcionesWeb = driver.findElement(By.id("opciones"));
 		opcionesWeb.click();
 		opcionesWeb.clear();
 		opcionesWeb.sendKeys(opciones);
 		
 		

 		//Pulsar el boton de Modificar.
 		By boton = By.className("boton_modificar");
 		driver.findElement(boton).click();	   
    }
}
