package es.uniovi.asw.reportwriter.impl;

import es.uniovi.asw.LoadUsers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import es.uniovi.asw.reportwriter.WriterReport;

public class WriterReportImpl implements WriterReport{

	private static final Logger log = LoggerFactory.getLogger(LoadUsers.class);

	/**
	 * Método que escribe una linea en el fichero log.
	 */
	@Override
	public void errorMismoEmail(String email) {
		log.info("Se ha producido un error debido a que hay varios usuarios con un mismo email: " + email);
		
	}
}
