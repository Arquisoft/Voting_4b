package prueba;

import static org.junit.Assert.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.cloud.cloudfoundry.com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.cloud.cloudfoundry.com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import aplication.Application;
import aplication.domain.User;
import aplication.service.DBManagement;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest
public class DBTest {

	@Autowired
	DBManagement manager;

	public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
	private RestTemplate restTemplate = new TestRestTemplate();

	@Before
	public void setUp() throws Exception {
		manager.save(new User("test@gmail.com", "test"));
	}

	@Test
	public void findExistingUserTest() {
		User user;

		user = manager.findByEmailAndPassword("test@gmail.com", "test");
		assertEquals("test@gmail.com", user.getEmail());
		assertEquals("test", user.getPassword());

	}

	@Test
	public void findNonExistingUserTest() {
		User user;

		user = manager.findByEmailAndPassword("tst@gmail.com", "test");
		assertNull(user);

		user = manager.findByEmailAndPassword("test@gmail.com", "tst");
		assertNull(user);
	}

	@Test
	public void findNewUserTest() {
		User user;

		user = manager.findByEmailAndPassword("prueba@gmail.com", "123");
		assertNull(user);

		manager.save(new User("prueba@gmail.com", "123"));
		user = manager.findByEmailAndPassword("prueba@gmail.com", "123");
		assertEquals("prueba@gmail.com", user.getEmail());
		assertEquals("123", user.getPassword());
	}

	@Test
	public void postMethodExistingUserTest(){

		Map<String, String> requestBody = new HashMap<String, String>();
		requestBody.put("email", "pamela@gmail.com");
		requestBody.put("password", "patata");

		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> httpEntity = null;
		try {
			httpEntity = new HttpEntity<String>(OBJECT_MAPPER.writeValueAsString(requestBody),
					requestHeaders);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		@SuppressWarnings("unchecked")
		Map<String, String> apiResponse = restTemplate.postForObject("http://localhost:8080/user", httpEntity,
				Map.class, Collections.EMPTY_MAP);

		assertNotNull(apiResponse);

		assertEquals("pamela@gmail.com", apiResponse.get("email"));
		assertEquals("Pamela", apiResponse.get("name"));
		assertEquals("11111111A", apiResponse.get("nif"));
		assertEquals("01", apiResponse.get("pollingStationCode"));

	}
	
	@Test()
	public void postMethodNonExistingUserTest() {
		Map<String, String> requestBody = new HashMap<String, String>();
		requestBody.put("email", "pamela@gmail.com");
		requestBody.put("password", "error");

		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> httpEntity = null;
		try {
			httpEntity = new HttpEntity<String>(OBJECT_MAPPER.writeValueAsString(requestBody),
					requestHeaders);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		@SuppressWarnings("unchecked")
		Map<String, String> apiResponse = restTemplate.postForObject("http://localhost:8080/user", httpEntity,
				Map.class, Collections.EMPTY_MAP);

		assertNull(apiResponse.get("email"));
		assertNull(apiResponse.get("Nif"));
		assertNull(apiResponse.get("name"));
		assertNull(apiResponse.get("pollingStationCode"));
		
	}

}