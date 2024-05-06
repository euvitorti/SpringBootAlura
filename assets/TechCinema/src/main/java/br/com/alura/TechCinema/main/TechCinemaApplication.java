package br.com.alura.TechCinema.main;

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

        Principal main = new Principal();
        main.menu();

    }
}
