package es.uniovi.asw.parser.impl;

import java.io.*;

import com.itextpdf.text.*;

import es.uniovi.asw.modelo.Voter;
import es.uniovi.asw.parser.GeneradorCartas;

/**
 * Clase que genera las cartas de los votantes con su usuario y su clave
 * 
 * @author Adrian
 */
public class CartasTXT implements GeneradorCartas {
		
	private String rutaCartasTxt = "cartasTXT/";

	@Override
	public void generador(Voter votante) throws IOException, DocumentException {

		generarCarta(votante);
	}	
	
	/**
	 * Constructor de la clase
	 * @param votante
	 * @throws IOException
	 * @throws DocumentException 
	 */
	public CartasTXT(Voter votante) throws IOException, DocumentException {
		
		generarCarta(votante);
	}

	/**
	 * Metodo principal que genera la carta
	 * @param votante
	 * @throws IOException 
	 * @throws DocumentException 
	 */
	private void generarCarta(Voter votante) throws IOException, DocumentException {
		
		String rutaCarta = rutaCartasTxt + votante.getNif() + ".txt";

		File fichero = new File(rutaCarta);
		
		/*if(votante.getClave().length() < 10)
		{
			GeneradorPasswordsImpl gp = new GeneradorPasswordsImpl();
			String pass = gp.generarPass(votante);
			votante.setClave(pass);
		}*/
		
		if (!fichero.exists())
		{
			File carpeta = new File(rutaCartasTxt);
			
			if (!carpeta.exists()) 
				carpeta.mkdir();
						
	        BufferedWriter carta = new BufferedWriter(new FileWriter(new 
	        		File(rutaCartasTxt + votante.getNif() + ".txt")));
	            
	        carta.write("Usuario: " + votante.getUsuario());
			String pass = "\nClave: " + votante.getClave();
	        carta.write(pass);
	        
			carta.close();
		}
		else
			System.out.println("La carta en formato TXT para este usuario ya existe");
	}
}