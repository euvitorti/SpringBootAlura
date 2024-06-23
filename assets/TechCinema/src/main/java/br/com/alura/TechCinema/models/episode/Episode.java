package br.com.alura.TechCinema.models.episode;

import br.com.alura.TechCinema.dto.episode.DataEpisodeDTO;
import br.com.alura.TechCinema.models.serie.Serie;
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

    public Serie getSerie() {
        return serie;
    }

    public void setSerie(Serie serie) {
        this.serie = serie;
    }

    // CONSTRUTOR PADRÃO
    public Episode(){}

    public Episode(Integer seasonNumber, DataEpisodeDTO dataEpisodeDTO) {
        this.season = seasonNumber;
        this.title = dataEpisodeDTO.Title();
        this.episode = dataEpisodeDTO.Episode();

        // CASO O VALOR DA AVALIAÇÃO NÃO VENHA COM A FORMATAÇÃO CORRETA
        try {
            this.imdbRating = Double.valueOf(dataEpisodeDTO.imdbRating());
        } catch (NumberFormatException e) {
            this.imdbRating = 0.0;
        }

        try {
            this.released = LocalDate.parse(dataEpisodeDTO.Released());
        } catch (DateTimeParseException e) {
            this.released = null;
        }
    }

    public String getTitle() {
        return title;
    }

    public Integer getSeason() {
        return season;
    }

    public Integer getEpisode() {
        return episode;
    }

    public double getImdbRating() {
        return imdbRating;
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
