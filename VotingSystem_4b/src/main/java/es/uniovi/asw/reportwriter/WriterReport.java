package es.uniovi.asw.reportwriter;

public interface WriterReport {

	
	/**
	 * Método que escribe una linea en el fichero log.
	 */
	public void errorMismoEmail(String email);

	void error(String mensaje);

	void info(String mensaje);
}
