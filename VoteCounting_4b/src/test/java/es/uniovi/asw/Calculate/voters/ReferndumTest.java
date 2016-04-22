package es.uniovi.asw.Calculate.voters;

import static org.junit.Assert.*;

import org.junit.Test;

import es.uniovi.asw.DBManagement.domain.Candidate;
import es.uniovi.asw.DBManagement.domain.Vote;

public class ReferndumTest {

	private Referendum re;

	@Test
	public void testResult() {
		re = new Referendum();
		Vote v1 = new Vote();v1.setCandidatura(Candidate.NO);
		Vote v2 = new Vote();v2.setCandidatura(Candidate.SI);
		
		re.actualizar(v1);
		assertTrue(re.getResult().containsKey("NO"));
		assertEquals(1,re.getResult().get("NO").intValue());
		re.actualizar(v2);re.actualizar(v2);re.actualizar(v2);
		re.actualizar(v1);
		assertTrue(re.getResult().containsKey("NO"));
		assertEquals(3,re.getResult().get("SI").intValue());
		assertEquals(2,re.getResult().get("NO").intValue());
	}
	
	@Test
	public void testPercents() {
		re = new Referendum();
		Vote v1 = new Vote();v1.setCandidatura(Candidate.NO);
		Vote v2 = new Vote();v2.setCandidatura(Candidate.SI);
		
		re.actualizar(v1);
		assertTrue(re.getPercents().containsKey("NO"));
		assertEquals(100.0, re.getPercents().get("NO").doubleValue(), 0);
		
		re.actualizar(v2);re.actualizar(v2);re.actualizar(v2);
		re.actualizar(v1);//T.Votos=5
		assertEquals(5, re.nVoto());
		assertTrue(re.getPercents().containsKey("SI"));
		//60%
		assertEquals(60.0, re.getPercents().get("SI").doubleValue(), 0);
		assertEquals(40.0, re.getPercents().get("NO").doubleValue(), 0);
	}

}
