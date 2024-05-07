package br.com.alura.TechCinema;

import br.com.alura.TechCinema.main.Principal;
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

        Principal principal = new Principal();
        principal.menu();

    }
}
