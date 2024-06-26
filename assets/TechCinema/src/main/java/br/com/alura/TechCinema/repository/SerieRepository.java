package br.com.alura.TechCinema.repository;

import br.com.alura.TechCinema.models.category.Category;
import br.com.alura.TechCinema.models.episode.Episode;
import br.com.alura.TechCinema.models.serie.Serie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

//QUAL É ENTIDADE, E A CHAVE PRIMÁRIA
// SALVANDO AS INFORMAÇÕES NO BANCO DE DADOS
public interface SerieRepository extends JpaRepository<Serie, Long> {

    Optional<Serie> findByTitleContainsIgnoreCase(String serieName);

    Optional<Serie> findByActorsContainsIgnoreCase(String actorName);

    List<Serie> findTop5ByOrderByImdbRatingDesc();

    List<Serie> findByGenre(Category category);

    //TODO JPQL
    @Query("SELECT s FROM Serie s WHERE s.totalSeasons <= :totalSeasons AND s.imdbRating >= :imdbRating")
    List<Serie> seriesBySeasonAndRating(int totalSeasons, double imdbRating);

    @Query("SELECT e FROM Serie s JOIN s.episodeList e WHERE e.title ILIKE %:episodeSnippet")
    List<Episode> episodeBySnippet(String episodeSnippet);

    @Query("SELECT e FROM Serie s JOIN s.episodeList e WHERE s = :serie ORDER BY e.imdbRating DESC LIMIT 5")
    List<Episode> topFiveEpisode(Serie serie);

    @Query("SELECT e FROM Serie s JOIN s.episodeList e WHERE s = :serie AND YEAR(e.released) >= :year")
    List<Episode> findByYear(Serie serie, int year);

    @Query("SELECT s FROM Serie s " +
            "JOIN s.episodeList e " +
            "GROUP BY s " +
            "ORDER BY MAX(e.released) DESC LIMIT 5"
    )
    List<Serie> latestReleases();

    @Query("SELECT e FROM Serie s JOIN s.episodeList e WHERE s.id = :id AND e.season = :season")
    List<Episode> getEpisodeBySeason(Long id, Long season);
}
