package br.com.alura.TechCinema.controller;

import br.com.alura.TechCinema.dto.EpisodeDTO;
import br.com.alura.TechCinema.dto.SerieDTO;
import br.com.alura.TechCinema.service.SerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/lancamentos")
    public List<SerieDTO> getRelease() {
        return serieService.getRelease();
    }

    @GetMapping("/{id}")
//    @PathVariable - INFORMANDO QUE VAI VIM UM PARÂMETROS
    public SerieDTO getById(@PathVariable Long id) {
        return serieService.getById(id);
    }

    @GetMapping("/{id}/temporadas/todas")
    public List<EpisodeDTO> getAllSeasons(@PathVariable Long id) {
        return serieService.getAllSeason(id);
    }
}
