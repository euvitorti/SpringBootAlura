package br.com.alura.TechCinema.repository;

import br.com.alura.TechCinema.models.Serie;
import org.springframework.data.jpa.repository.JpaRepository;

//QUAL É ENTIDADE, E A CHAVE PRIMÁRIA
// SALVANDO AS INFORMAÇÕES NO BANCO DE DADOS
public interface SerieRepository extends JpaRepository<Serie, Long> {
}
