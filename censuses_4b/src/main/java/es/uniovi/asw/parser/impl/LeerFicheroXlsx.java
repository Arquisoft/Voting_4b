package es.uniovi.asw.parser.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import es.uniovi.asw.dbupdate.VoterRepository;
import es.uniovi.asw.factoria.WriterFactory;
import es.uniovi.asw.modelo.Voter;
import es.uniovi.asw.parser.Insert;
import es.uniovi.asw.parser.ReadCensus;
import es.uniovi.asw.reportwriter.impl.WriterReportImpl;

/**
 * 
 * @author Ana
 *Clase que lee el fichero excel con los datos de los ciudadanos
 */
public class LeerFicheroXlsx  implements ReadCensus, Insert{
	
	 List<Voter> votantes;
	 VoterRepository repository = null;
	

	public LeerFicheroXlsx(VoterRepository repository) {
		votantes = new ArrayList<Voter>();
		this.repository = repository;
	}
	
	public LeerFicheroXlsx(){
		votantes = new ArrayList<Voter>();
	}
	
	
	/**
	 * Método que lee el fichero excel con los datos del censo
	 */
	@SuppressWarnings({ "rawtypes" })
	@Override
	public  void readCensus(String ruta) {
		
		InputStream ExcelFileToRead;
		XSSFWorkbook wb =null;
		try {
			ExcelFileToRead = new FileInputStream(ruta);
			try {
				wb = new XSSFWorkbook(ExcelFileToRead);
			} catch (IOException e) {
				e.printStackTrace();
			}
		
		} catch (FileNotFoundException e) {
			System.out.println("No se ha encontrado el fichero.");
		}

		XSSFSheet sheet = wb.getSheetAt(0);
		XSSFRow row;
		XSSFCell cell;

		Iterator rows = sheet.rowIterator();
		
		rows.next();
		while (rows.hasNext()) {
			row = (XSSFRow) rows.next();
			Iterator cells = row.cellIterator();
			ArrayList<Object> datos = new ArrayList<Object>();
			while (cells.hasNext()) {
				cell = (XSSFCell) cells.next();

				if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
					datos.add(cell.getStringCellValue());
				} else if (cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
					datos.add(cell.getNumericCellValue());
				} else {
					System.out.println("No está especificado el tipo");
				}

			}
	
			
			votantes.add(new Voter(datos.get(0).toString(), datos.get(2).toString(),
					datos.get(1).toString(),(int) Double.parseDouble(datos.get(3).toString()),
					"", "", false));
			
			
			
		}

	}
	
	/**
	 * Método que genera el nombre de usuario y la contraseña
	 * @param Voter
	 */
	public void updateUsuarioYClave(Voter voter){
		
		voter.setUsuario(voter.getEmail());
		voter.setClave(generarPass(voter));

	}
	
	/**
	 * Método que genera una contraseña aleatoria.
	 * @param votante
	 * @return
	 */
	private String generarPass(Voter votante) {
		
		int i, valor;
		char[] codigo = new char[10];
		char[] elementos = {'0','1','2','3','4','5','6','7','8','9',
						'a','b','c','d','e','f','g','h','i','j','k','l','m','n',
						'o','p','q','r','s','t','u','v','w','x','y','z',
						'A','B','C','D','E','F','G','H','I','J','K','L','M','N',
						'O','P','Q','R','S','T','U','V','W','X','Y','Z',
						'*','+','.',','};
		
		for(i = 0; i < codigo.length; i++){
			valor = (int)(Math.random() * elementos.length);
			codigo[i] = (char)elementos[valor];
		}
		
		return new String(codigo);
	}


	/**
	 * Llama a un método del componente DBUpdate para hacer la inserción en la base de datos
	 */
	@Override
	public void insert(){
		
		for (Voter votante : votantes) {
			updateUsuarioYClave(votante);
			if(repository.findByNif(votante.getNif())==null){ //si el votante no está ya en la base de datos
				if(!comprobar(votante)){
					repository.save(votante);
				}
			}
		}
	}
	
	/**
	 * Método que devuelve una colección de votantes.
	 * @return
	 */
	public  List<Voter> getVotantes(){
		return votantes;
	}

	/**
	 * Método que comprueba si hay varios usuarios con el mismo email.
	 * @param voter
	 */
	public boolean comprobar(Voter voter){
		
		for (Voter votante : repository.findAll()) {
			if(voter.getEmail().equals(votante.getEmail())){ //si encuentra a otro votante con el mismo email
				WriterReportImpl w = (WriterReportImpl)WriterFactory.getWriterReportImpl();
				w.errorMismoEmail(voter.getEmail());
				return true;
			}
		}
		return false;
	}



	
	
}
