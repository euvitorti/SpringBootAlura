package br.com.alura.TechCinema.service;

import br.com.alura.TechCinema.dto.SerieDTO;
import br.com.alura.TechCinema.models.Serie;
import br.com.alura.TechCinema.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SerieService {

    @Autowired
    private SerieRepository serieRepository;

    private List<SerieDTO> convertData(List<Serie> series) {
        return series.stream()
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

    public List<SerieDTO> getAllSeries() {
        return convertData(serieRepository.findAll());
    }

    public List<SerieDTO> getTop5Series() {
        return convertData(serieRepository.findTop5ByOrderByImdbRatingDesc());
    }

    public List<SerieDTO> getRelease() {
       return convertData(serieRepository.findTop5ByOrderByEpisodeListReleasedDesc());
    }
}
