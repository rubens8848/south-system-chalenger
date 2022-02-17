package br.com.forja.bits.donation;

import br.com.forja.bits.donation.utils.Env;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TemplateApplication {

	public static void main(String[] args) {
		// Spring Application
		SpringApplication.run(TemplateApplication.class, args);

		// Starting .env
		Env.getInstance();
	}

}
