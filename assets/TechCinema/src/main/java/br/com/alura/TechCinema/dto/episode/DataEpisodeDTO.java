package br.com.alura.TechCinema.dto.episode;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DataEpisodeDTO(String Title,
                             Integer Episode,
                             String imdbRating,
                             String Released) {
}
