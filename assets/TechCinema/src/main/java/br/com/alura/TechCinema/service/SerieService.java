package br.com.alura.TechCinema.service;

import br.com.alura.TechCinema.dto.SerieDTO;
import br.com.alura.TechCinema.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SerieService {

    @Autowired
    private SerieRepository serieRepository;

    public List<SerieDTO> getAllSeries() {
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
