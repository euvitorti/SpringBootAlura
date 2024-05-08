package br.com.alura.TechCinema;

import br.com.alura.TechCinema.main.Principal;
import br.com.alura.TechCinema.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TechCinemaApplication implements CommandLineRunner {

    @Autowired
    private SerieRepository serieRepository;

    public static void main(String[] args) {
        SpringApplication.run(TechCinemaApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        Principal principal = new Principal(serieRepository);
        principal.menu();

    }
}
