package es.uniovi.asw.factoria;

import es.uniovi.asw.reportwriter.WriterReport;
import es.uniovi.asw.reportwriter.impl.WriterReportImpl;

public class WriterFactory {

public WriterFactory(){
		
	}
	public static WriterReport getWriterReportImpl(){
		return new WriterReportImpl();
	}
}
