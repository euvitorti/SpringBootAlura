package br.com.alura.TechCinema.dto.season;

import br.com.alura.TechCinema.dto.episode.DataEpisodeDTO;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DataSeasonDTO(Integer Season,
                            @JsonAlias("Episodes") List<DataEpisodeDTO> episodes) {
}
