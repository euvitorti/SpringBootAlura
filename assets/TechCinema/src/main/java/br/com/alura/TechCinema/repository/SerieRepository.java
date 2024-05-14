package br.com.alura.TechCinema.repository;

import br.com.alura.TechCinema.models.Category;
import br.com.alura.TechCinema.models.Serie;
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

//    List<Serie> findByTotalSeasonLessThanEqualAndImdbRatingGreaterThanEqual(int totalSeasons, double rating);

    //TODO JPQL
    @Query("SELECT s FROM Serie s WHERE s.totalSeasons <= :totalSeasons AND s.imdbRating >= :imdbRating")
    List<Serie> seriesBySeasonAndRating(int totalSeasons, double imdbRating);
}
