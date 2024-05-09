package br.com.alura.TechCinema.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;

// TODO ESTUDAR JPA

// ESSA CLASSE É UMA TABELA DO BANCO DE DADOS
@Entity
@Table(name = "series")
public class Serie {

    // EXISTE VÁRIAS FORMAS DE GERAR O ID
    // IDENTITY É AUTO INCREMENT
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String title;

    private Integer totalSeasons;

    private double imdbRating;

    @Enumerated(EnumType.STRING)
    private Category genre;

    private String actors;

    private String poster;

    private String plot;

    //RELACIONANDO COM A TABELA SERIE
    @OneToMany(mappedBy = "serie")
    private List<Episode> episodeList = new ArrayList<>();

    // CONSTRUTOR PADRÃO, POIS A JPA EXIGE, PARA RECUPERAR OS DADOS DO BANCO DE DAOOS
    public Serie(){}

    public Serie(DataSeries dataSeries) {
        this.title = dataSeries.Title();
        this.totalSeasons = dataSeries.totalSeasons();
        this.imdbRating = OptionalDouble.of(Double.valueOf(dataSeries.imdbRating())).orElse(0.0);
        this.genre = Category.fromString(dataSeries.Genre().split(",")[0].trim());
        this.actors = dataSeries.Actors();
        this.poster = dataSeries.Poster();
        this.plot = dataSeries.Plot();

        // Para traduzir a sinopse, use a próxima linha de código
        // this.plot = ChatGpt.getTranslate(dataSeries.Plot()).trim();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Episode> getEpisodeList() {
        return episodeList;
    }

    public void setEpisodeList(List<Episode> episodeList) {
        this.episodeList = episodeList;
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
                "--------------------------\n" +
                        "Genre: " + genre + "\n" +
                        "Title: " + title + "\n" +
                        "TotalSeasons: " + totalSeasons + "\n" +
                        "Rating: " + imdbRating + "\n" +
                        "Actors: " + actors + "\n" +
                        "Poster: " + poster + "\n" +
                        "Plot: " + plot + "\n" +
                        "🎥   Thanks for using   🎥\n";
    }
}
