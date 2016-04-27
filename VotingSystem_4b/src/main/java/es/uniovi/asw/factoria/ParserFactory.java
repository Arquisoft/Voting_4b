package es.uniovi.asw.factoria;

import java.io.IOException;

import com.itextpdf.text.DocumentException;

import es.uniovi.asw.dbupdate.VoterRepository;
import es.uniovi.asw.modelo.Voter;
import es.uniovi.asw.parser.GeneradorCartas;
import es.uniovi.asw.parser.ReadCensus;
import es.uniovi.asw.parser.impl.CartasPDF;
import es.uniovi.asw.parser.impl.CartasTXT;
import es.uniovi.asw.parser.impl.LeerFicheroXlsx;

public class ParserFactory {

	public ParserFactory(){
		
	}
	public static ReadCensus getReadCensusXlsx(VoterRepository repository){
		return new LeerFicheroXlsx(repository);
	}
	
	public static GeneradorCartas getGeneradorTxt(Voter votante) throws IOException, DocumentException{
		return new CartasTXT(votante);
	}
	
	public static GeneradorCartas getGeneradorPdf(Voter votante) throws IOException, DocumentException{
		return new CartasPDF(votante);
	}

}
