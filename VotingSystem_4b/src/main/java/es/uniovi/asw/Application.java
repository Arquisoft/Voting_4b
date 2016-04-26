package es.uniovi.asw;

import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import es.uniovi.asw.dbupdate.VoterRepository;
import es.uniovi.asw.modelo.Voter;


@EnableAutoConfiguration
@SpringBootApplication
public class Application {
	
	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(Application.class);
		app.setBannerMode(Banner.Mode.OFF);	
		app.run(args);
	}
	
	
	@Bean
	public CommandLineRunner demo(final VoterRepository repository) {
		return new CommandLineRunner() {
			@Override
			public void run(String... args) throws Exception {

				Voter pamela = new Voter("Pamela", "pamela@gmail.com", "1111111A", 01, "pamela", "patata", false);
				Voter juntaElectoral = new Voter("Junta Electoral", "junta@gmail.com", "22222A", 01, "junta", "junta", false);
				repository.save(pamela);
				repository.save(juntaElectoral);
			}
		};
	
}

}