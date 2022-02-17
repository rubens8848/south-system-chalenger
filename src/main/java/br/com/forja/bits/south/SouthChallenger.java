package br.com.forja.bits.south;

import br.com.forja.bits.south.utils.Env;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableKafka
@SpringBootApplication
public class SouthChallenger {

	public static void main(String[] args) {
		// Spring Application
		SpringApplication.run(SouthChallenger.class, args);

		// Starting .env
		Env.getInstance();
	}


}
