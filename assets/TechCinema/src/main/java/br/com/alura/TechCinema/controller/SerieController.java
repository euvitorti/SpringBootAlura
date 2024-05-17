package br.com.alura.TechCinema.controller;

import br.com.alura.TechCinema.dto.SerieDTO;
import br.com.alura.TechCinema.service.SerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController

// TODOS OS ENDPOINTS VÃO PASSAR POR ESTA URL
@RequestMapping("/series")
public class SerieController {

    @Autowired
    private SerieService serieService;

    // DEFININDO A ROTA
    @GetMapping
    public List<SerieDTO> getAllSerie() {
        return serieService.getAllSeries();
    }

    // TOP 5 SERIES
    @GetMapping("/top5")
    public List<SerieDTO> getTopFiveSerie() {
        return serieService.getTop5Series();
    }
}
