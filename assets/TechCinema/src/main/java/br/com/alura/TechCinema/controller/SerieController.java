package br.com.alura.TechCinema.controller;

import br.com.alura.TechCinema.dto.SerieDTO;
import br.com.alura.TechCinema.models.Serie;
import br.com.alura.TechCinema.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class SerieController {

    @Autowired
    private SerieRepository serieRepository;

    // DEFININDO A ROTA
    @GetMapping("/series")
    public List<SerieDTO> getSerie() {
        return serieRepository.findAll()
                .stream()
                .map(s -> new SerieDTO(s.getId(),
                        s.getTitle(),
                        s.getTotalSeasons(),
                        s.getImdbRating(),
                        s.getGenre(),
                        s.getActors(),
                        s.getPoster(),
                        s.getPlot()))
                .collect(Collectors.toList());
    }
}
