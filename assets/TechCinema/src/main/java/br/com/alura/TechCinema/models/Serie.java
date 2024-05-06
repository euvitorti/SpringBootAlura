package br.com.alura.TechCinema.models;

import java.util.OptionalDouble;

public class Serie {
    private String title;
    private Integer totalSeasons;
    private double imdbRating;
    private Category genre;
    private String actors;
    private String poster;
    private String plot;

    public Serie(DataSeries dataSeries) {
        this.title = dataSeries.Title();
        this.totalSeasons = dataSeries.totalSeasons();
        this.imdbRating = OptionalDouble.of(Double.valueOf(dataSeries.imdbRating())).orElse(0.0);
        this.genre = Category.fromString(dataSeries.Genre().split(",")[0].trim());
        this.actors = dataSeries.Actors();
        this.poster = dataSeries.Poster();
        this.plot = dataSeries.Plot();
    }
}
