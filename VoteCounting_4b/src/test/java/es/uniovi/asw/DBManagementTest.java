package es.uniovi.asw;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import es.uniovi.asw.DBManagement.DBManagement;
import es.uniovi.asw.DBManagement.PollingStationManagement;
import es.uniovi.asw.DBManagement.domain.Candidate;
import es.uniovi.asw.DBManagement.domain.PollingStation;
import es.uniovi.asw.DBManagement.domain.Vote;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class DBManagementTest {

	@Autowired
	DBManagement repository;

	@Autowired
	PollingStationManagement colegioRepository;

	@Before
	public void before() {
		repository.deleteAll();
		colegioRepository.deleteAll();

		colegioRepository.save(new PollingStation(new Long(50), "Asturias", "Principado de Asturias"));
		colegioRepository.save(new PollingStation(new Long(51), "Madrid", "Comunidad de Madrid"));
		colegioRepository.save(new PollingStation(new Long(52), "León", "Comunidad de Castillo y León"));
		colegioRepository.save(new PollingStation(new Long(53), "Extremadura", "Comunidad de Extremadura"));

		repository.save(new Vote(colegioRepository.findByCircunscripcion("Asturias").get(0), Candidate.SI));
		repository.save(new Vote(colegioRepository.findByCircunscripcion("Asturias").get(0), Candidate.SI));
		repository.save(new Vote(colegioRepository.findByCircunscripcion("Asturias").get(0), Candidate.NO));
		repository.save(new Vote(colegioRepository.findByCircunscripcion("Asturias").get(0), Candidate.SI));
		repository.save(new Vote(colegioRepository.findByCircunscripcion("Madrid").get(0), Candidate.SI));
		repository.save(new Vote(colegioRepository.findByCircunscripcion("Madrid").get(0), Candidate.SI));
		repository.save(new Vote(colegioRepository.findByCircunscripcion("León").get(0), Candidate.SI));
		repository.save(new Vote(colegioRepository.findByCircunscripcion("León").get(0), Candidate.NO));
		repository.save(new Vote(colegioRepository.findByCircunscripcion("León").get(0), Candidate.SI));
		repository.save(new Vote(colegioRepository.findByCircunscripcion("León").get(0), Candidate.NO));
		repository.save(new Vote(colegioRepository.findByCircunscripcion("Extremadura").get(0), Candidate.SI));
		repository.save(new Vote(colegioRepository.findByCircunscripcion("Extremadura").get(0), Candidate.NO));
		repository.save(new Vote(colegioRepository.findByCircunscripcion("Extremadura").get(0), Candidate.SI));
		repository.save(new Vote(colegioRepository.findByCircunscripcion("Extremadura").get(0), Candidate.NO));
		repository.save(new Vote(colegioRepository.findByCircunscripcion("Extremadura").get(0), Candidate.SI));

	}

	@Test
	public void testFindAllVotes() {

		assertTrue(repository.findAll().size() == 15);
	}

	@Test
	public void testFindByCandidate() {
		assertTrue(repository.findByCandidatura(Candidate.SI).size() == 10);
		assertTrue(repository.findByCandidatura(Candidate.NO).size() == 5);

	}

	@Test
	public void testFindByCodigo() {
		assertTrue(!colegioRepository.findByCodigo(new Long(50)).isEmpty());
	}

	@Test
	public void testFindByCircunscripcion() {
		assertTrue(colegioRepository.findByCircunscripcion("Asturias").size() == 1);
	}

}
