package br.com.alura.TechCinema.models;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@Entity
@Table(name = "episode")

public class Episode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;
    private LocalDate released;
    private Integer season;
    private Integer episode;
    private double imdbRating;

    // RELACIONANDO COM A TABELA SERIES
    @ManyToOne
    private Serie serie;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Serie getSerie() {
        return serie;
    }

    public void setSerie(Serie serie) {
        this.serie = serie;
    }

    public Episode(Integer seasonNumber, DataEpisode dataEpisode) {
        this.season = seasonNumber;
        this.title = dataEpisode.Title();
        this.episode = dataEpisode.Episode();

        // CASO O VALOR DA AVALIAÇÃO NÃO VENHA COM A FORMATAÇÃO CORRETA
        try {
            this.imdbRating = Double.valueOf(dataEpisode.imdbRating());
        } catch (NumberFormatException e) {
            this.imdbRating = 0.0;
        }

        try {
            this.released = LocalDate.parse(dataEpisode.Released());
        } catch (DateTimeParseException e) {
            this.released = null;
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getReleased() {
        return released;
    }

    public void setReleased(LocalDate released) {
        this.released = released;
    }

    public Integer getSeason() {
        return season;
    }

    public void setSeason(Integer season) {
        this.season = season;
    }

    public Integer getEpisode() {
        return episode;
    }

    public void setEpisode(Integer episode) {
        this.episode = episode;
    }

    public double getImdbRating() {
        return imdbRating;
    }

    public void setImdbRating(double imdbRating) {
        this.imdbRating = imdbRating;
    }

    @Override
    public String toString() {
        return "\n-------------------------\n" +
                "Title: " + title + "\n" +
                "Released: " + released + "\n" +
                "Season: " + season + "\n" +
                "Episode: " + episode + "\n" +
                "Rating: " + imdbRating +
                "\n-------------------------\n";
    }
}
