package br.com.alura.TechCinema.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DataEpisode(String Title,
                          Integer Episode,
                          String imdbRating,
                          String Released) {
}
