package br.com.alura.TechCinema.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DataSeason(Integer Season,
                         @JsonAlias("Episodes") List<DataEpisode> dataEpisodes) {
}
