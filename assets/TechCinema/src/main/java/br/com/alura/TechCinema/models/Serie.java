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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getTotalSeasons() {
        return totalSeasons;
    }

    public void setTotalSeasons(Integer totalSeasons) {
        this.totalSeasons = totalSeasons;
    }

    public double getImdbRating() {
        return imdbRating;
    }

    public void setImdbRating(double imdbRating) {
        this.imdbRating = imdbRating;
    }

    public Category getGenre() {
        return genre;
    }

    public void setGenre(Category genre) {
        this.genre = genre;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    @Override
    public String toString() {
        return
                "\n" +
                        "Genre: " + genre +
                        "Title: " + title +
                        "TotalSeasons: " + totalSeasons +
                        "Rating: " + imdbRating +
                        "Actors: '" + actors +
                        "Poster='" + poster +
                        "Plot='" + plot;
    }
}
