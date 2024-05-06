package br.com.alura.TechCinema.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

// Disserializando Dados

// PARA IGNORAR CAMPOS QUE NÃO PRECISAMOS, USAMOS @JsonIgnoreProperties(ignoreUnknown = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public record DataSeries(String Title,
                         Integer totalSeasons,
                         String imdbRating,
                         String Genre,
                         String Actors,
                         String Poster,
                         String Plot) {
}
