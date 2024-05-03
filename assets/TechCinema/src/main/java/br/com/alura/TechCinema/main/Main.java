package br.com.alura.TechCinema.main;

import br.com.alura.TechCinema.api.Api;
import br.com.alura.TechCinema.models.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    private Scanner scanner = new Scanner(System.in);
    private ConvertData convertData = new ConvertData();
    private Api api = new Api();

    private final String ADDRESS = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "YOURAPIKEY";

    public void menu() {
        System.out.println("Welcome!  😊");
        System.out.println("------------");
        System.out.println("Series name:");

        String seriesName = scanner.nextLine();

        var json = api.connect( ADDRESS + seriesName.replace(" ", "+") + API_KEY);

        DataSeries dataSeries = convertData.getData(json, DataSeries.class);
        System.out.println(dataSeries);

        List<DataSeason> seasons = new ArrayList<>();

        for (int i = 1; i <= dataSeries.totalSeasons(); i++) {
            json = api.connect(ADDRESS + seriesName.replace(" ", "+") + "&season=" + i + API_KEY);
            DataSeason dataSeason = convertData.getData(json, DataSeason.class);
            seasons.add(dataSeason);
        }

        seasons.forEach(System.out::println);

//         LAMBDA
        seasons.forEach(t -> t.episodes().forEach(e -> System.out.println(e.Title())));

        List<DataEpisode> dataEpisodes = seasons.stream()
                .flatMap(t -> t.episodes().stream())
                .collect(Collectors.toList());
        // toList é imutável, não será possível adicionar outro episódio

        // COMPARANDO A AVALIAÇÃO DO EP COM OS OUTROS, PARA RETONAR OS QUE TEM A MELHOR AVALIÇÃO
        // IGNORA TODOS AQUELES QUE NÃO TEM AVALIAÇÃO
        System.out.println("\nTop 5 better");
        dataEpisodes.stream()
                .filter(e -> !e.imdbRating().equalsIgnoreCase("N/A"))
                .peek(e -> System.out.println("Primeiro Filtro (N/A): " + e))
                .sorted(Comparator.comparing(DataEpisode::imdbRating).reversed())
                .peek(e -> System.out.println("Ordenação: " + e))
                .limit(5)
                .peek(e -> System.out.println("Limite: " + e))
                .forEach(System.out::println);

        List<Episode> episodes = seasons.stream()
                .flatMap(t -> t.episodes().stream()
                        .map(d -> new Episode(t.Season(), d))
                ).collect(Collectors.toList());

        episodes.forEach(System.out::println);

//        // ENCONTRANDO A PRIMEIRA OCORRÊNCIA DE UMA BUSCA EM UMA COLEÇÃO
//        System.out.println("--------------");
//        System.out.println("Episode Name: ");
//        String episodesName = scanner.nextLine();
//
//        // OPTIONAL É UM OBJETO CONTÊINER QUE PODE OU NÃO CONTER UM VALOR NÃO NULO
//        Optional<Episode> searchEpisode = episodes.stream()
//                .filter(e -> e.getTitle().toUpperCase().contains(episodesName.toUpperCase()))
//                .findFirst();
//
//        if (searchEpisode.isPresent()) {
//            System.out.println("\nEpisode found!");
//            System.out.println("Season: " + searchEpisode.get().getSeason());
//        } else {
//            System.out.println("Episode not found!\n");
//        }

        System.out.println("What year?");
        int year = scanner.nextInt();
        // PARA NÃO OCORRER UM ERRO AO LER
        scanner.nextLine();

        LocalDate dateSearch = LocalDate.of(year, 1,1);

        // DATA FORMATADA
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // BASEADO EM UM ANO
        episodes.stream()
                .filter(e -> e.getReleased() != null && e.getReleased().isAfter(dateSearch))
                .forEach(e -> System.out.printf(
                        "\nSeason: " + e.getSeason() +
                                " Episode: " + e.getEpisode() +
                                " Released: " + e.getReleased().format(dateTimeFormatter)
                ));

    }
}


