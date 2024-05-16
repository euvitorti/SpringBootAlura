package br.com.alura.TechCinema.dto;

import br.com.alura.TechCinema.models.Category;

public record SerieDTO(
        long id,

        String title,

        Integer totalSeasons,

        double imdbRating,

        Category genre,

        String actors,

        String poster,

        String plot) {
}
