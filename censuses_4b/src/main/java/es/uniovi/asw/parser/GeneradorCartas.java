package es.uniovi.asw.parser;

import java.io.IOException;

import com.itextpdf.text.DocumentException;

import es.uniovi.asw.modelo.Voter;

public interface GeneradorCartas {
	
	public void generador(Voter votante) throws IOException, DocumentException;
}
