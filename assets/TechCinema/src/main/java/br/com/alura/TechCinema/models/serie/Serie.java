package br.com.alura.TechCinema.models.serie;

import br.com.alura.TechCinema.dto.serie.DataSeriesDTO;
import br.com.alura.TechCinema.models.category.Category;
import br.com.alura.TechCinema.models.episode.Episode;
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
    @OneToMany(mappedBy = "serie", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Episode> episodeList = new ArrayList<>();

    // CONSTRUTOR PADRÃO, POIS A JPA EXIGE, PARA RECUPERAR OS DADOS DO BANCO DE DAOOS
    public Serie(){}

    public Serie(DataSeriesDTO dataSeriesDTO) {
        this.title = dataSeriesDTO.Title();
        this.totalSeasons = dataSeriesDTO.totalSeasons();
        this.imdbRating = OptionalDouble.of(Double.valueOf(dataSeriesDTO.imdbRating())).orElse(0.0);
        this.genre = Category.fromString(dataSeriesDTO.Genre().split(",")[0].trim());
        this.actors = dataSeriesDTO.Actors();
        this.poster = dataSeriesDTO.Poster();
        this.plot = dataSeriesDTO.Plot();

        // Para traduzir a sinopse, use a próxima linha de código
        // this.plot = ChatGpt.getTranslate(dataSeriesDTO.Plot()).trim();
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
        // ASSOCIÇÃO PARA A CHAVE ESTRANGEIRA
        // ESTA SÉRIE É DONA DESSE EPISÓDIO~~~~~~~~~~
        episodeList.forEach(e -> e.setSerie(this));
        this.episodeList = episodeList;
    }

    public String getTitle() {
        return title;
    }

    public Integer getTotalSeasons() {
        return totalSeasons;
    }

    public double getImdbRating() {
        return imdbRating;
    }

    public Category getGenre() {
        return genre;
    }

    public String getActors() {
        return actors;
    }

    public String getPoster() {
        return poster;
    }

    public String getPlot() {
        return plot;
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
//                        "Episode: " + getEpisodeList() + "\n" +
                        "--------------------------\n"+
                        "🎥   Thanks for using   🎥\n";
    }
}
