package br.com.alura.TechCinema.dto.serie;

import br.com.alura.TechCinema.models.category.Category;

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
