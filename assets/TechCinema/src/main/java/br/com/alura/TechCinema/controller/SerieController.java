package br.com.alura.TechCinema.controller;

import br.com.alura.TechCinema.dto.SerieDTO;
import br.com.alura.TechCinema.service.SerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SerieController {

    @Autowired
    private SerieService serieService;

    // DEFININDO A ROTA
    @GetMapping("/series")
    public List<SerieDTO> getSerie() {
        return serieService.getAllSeries();
    }
}
