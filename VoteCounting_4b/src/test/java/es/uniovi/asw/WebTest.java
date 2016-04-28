package es.uniovi.asw;

import es.uniovi.asw.Calculate.voters.Referendum;
import es.uniovi.asw.DBManagement.domain.Candidate;
import es.uniovi.asw.DBManagement.domain.Vote;

import org.apache.commons.collections.map.HashedMap;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class WebTest {

	private Referendum re;

	@Test
	public void testPrepararArray () {
		WebObserver webObserver = new WebObserver();
		Map<String, Double> map = new HashedMap();
		webObserver.actualizar(map);

	}

}
