package br.com.alura.TechCinema.controller;

import br.com.alura.TechCinema.models.Serie;
import br.com.alura.TechCinema.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SerieController {

    @Autowired
    private SerieRepository serieRepository;

    // DEFININDO A ROTA
    @GetMapping("/series")
    public List<Serie> getSerie() {
        return serieRepository.findAll();
    }
}
