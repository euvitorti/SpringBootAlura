package br.com.alura.TechCinema.main;

import br.com.alura.TechCinema.api.Api;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TechCinemaApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(TechCinemaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Api api = new Api();
		var json = api.connect("https://v6.exchangerate-api.com/v6/9c669a74faaada81ba36788e/latest/USD");
		System.out.println(json);
	}
}
