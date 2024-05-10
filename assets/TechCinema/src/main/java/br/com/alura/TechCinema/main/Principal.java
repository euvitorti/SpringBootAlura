package br.com.alura.TechCinema.main;

import br.com.alura.TechCinema.models.DataSeason;
import br.com.alura.TechCinema.models.DataSeries;
import br.com.alura.TechCinema.models.Episode;
import br.com.alura.TechCinema.models.Serie;
import br.com.alura.TechCinema.repository.SerieRepository;
import br.com.alura.TechCinema.service.Api;
import br.com.alura.TechCinema.service.ConvertData;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {

    private Scanner scanner = new Scanner(System.in);

    private Api api = new Api();

    private ConvertData convertData = new ConvertData();

    private final String ADDRESS = "https://www.omdbapi.com/?t=";

    private final String API_KEY = "YOURAPIKEY";

    private List<DataSeries> listDataSeries = new ArrayList<>();

    private SerieRepository serieRepository;

    private List<Serie> serieList = new ArrayList<>();

    public Principal(SerieRepository serieRepository) {
        this.serieRepository = serieRepository;
    }

    public void menu() {
        byte choice = -1;

        while (choice != 0) {
            System.out.println("""
                    Welcome! 🎥
                    -------------------
                    [1] Buscar Série
                    [2] Buscar Episódio
                    [3] Listar Série
                    [4] Buscar por título
                    -------------------
                    [0] Sair
                    """);

            choice = scanner.nextByte();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    searchSerie();
                    break;
                case 2:
                    searchEpisode();
                    break;
                case 3:
                    listSearchedSeries();
                    break;
                case 4:
                    searchSerieByTitle();
                    break;
                case 0:
                    System.out.println("Goodbye👋");
                    break;
                default:
                    System.out.println("Opção inválida.");
                    break;
            }
        }
    }

    private DataSeries getDataSerie() {
        System.out.println("""
                --------------
                Nome da série:""");

        String seriesName = scanner.nextLine();

        var json = api.connect( ADDRESS + seriesName.replace(" ", "+") + API_KEY);

        DataSeries dataSeries = convertData.getData(json, DataSeries.class);
//        System.out.println(dataSeries);
        return dataSeries;
    }

    private void searchSerie() {
        DataSeries data = getDataSerie();
        Serie serie = new Serie(data);
        serieRepository.save(serie);
        listSearchedSeries();
    }

    private void searchEpisode() {
        listSearchedSeries();

        System.out.println("Digite o nome da série: ");
        var serieName = scanner.nextLine();

        Optional<Serie> serie = serieRepository.findByTitleContainsIgnoreCase(serieName);

        if (serie.isPresent()) {

            var serieFound = serie.get();

            List<DataSeason> seasons = new ArrayList<>();

            for (int i = 1; i <= serieFound.getTotalSeasons(); i++) {
                var json = api.connect(ADDRESS + serieFound.getTitle().replace(" ", "+") + "&season=" + i + API_KEY);
                DataSeason dataSeason = convertData.getData(json, DataSeason.class);
                seasons.add(dataSeason);
            }

            seasons.forEach(System.out::println);

            List<Episode> episode = seasons.stream()
                    .flatMap(d -> d.episodes().stream()
                            .map(e -> new Episode(d.Season(), e)))
                    .collect(Collectors.toList());

            serieFound.setEpisodeList(episode);
            serieRepository.save(serieFound);
        } else {
            System.out.println("Não foi possível encontrar a série.");
        }

    }

    private void listSearchedSeries() {
        // BUSCANDO AS INFORMAÇÕES DO BANCO DE DADOS

        serieList = serieRepository.findAll();

        serieList.stream()
                .sorted(Comparator.comparing(Serie::getGenre))
                .forEach(System.out::println);
    }

    private void searchSerieByTitle() {
        System.out.println("Digite o nome da série: ");
        var serieName = scanner.nextLine();

        Optional<Serie> seriesWantedList = serieRepository.findByTitleContainsIgnoreCase(serieName);

        if (seriesWantedList.isPresent()) {
            System.out.println(seriesWantedList.get());
        } else {
            System.out.println("Não encontrada.");
        }
    }
}
//        seasons.forEach(System.out::println);
//
////         LAMBDA
//        seasons.forEach(t -> t.episodes().forEach(e -> System.out.println(e.Title())));
//
//        List<DataEpisode> dataEpisodes = seasons.stream()
//                .flatMap(t -> t.episodes().stream())
//                .collect(Collectors.toList());
//        // toList é imutável, não será possível adicionar outro episódio
//
//        // COMPARANDO A AVALIAÇÃO DO EP COM OS OUTROS, PARA RETONAR OS QUE TEM A MELHOR AVALIÇÃO
//        // IGNORA TODOS AQUELES QUE NÃO TEM AVALIAÇÃO
//        System.out.println("\nTop 5 better");
//        dataEpisodes.stream()
//                .filter(e -> !e.imdbRating().equalsIgnoreCase("N/A"))
//                .peek(e -> System.out.println("Primeiro Filtro (N/A): " + e))
//                .sorted(Comparator.comparing(DataEpisode::imdbRating).reversed())
//                .peek(e -> System.out.println("Ordenação: " + e))
//                .limit(5)
//                .peek(e -> System.out.println("Limite: " + e))
//                .forEach(System.out::println);
//
//        List<Episode> episodes = seasons.stream()
//                .flatMap(t -> t.episodes().stream()
//                        .map(d -> new Episode(t.Season(), d))
//                ).collect(Collectors.toList());
//
//        episodes.forEach(System.out::println);
//
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
//
//        System.out.println("What year?");
//        int year = scanner.nextInt();
//        // PARA NÃO OCORRER UM ERRO AO LER
//        scanner.nextLine();
//
//        LocalDate dateSearch = LocalDate.of(year, 1,1);
//
//        // DATA FORMATADA
//        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//
//        // BASEADO EM UM ANO
//        episodes.stream()
//                .filter(e -> e.getReleased() != null && e.getReleased().isAfter(dateSearch))
//                .forEach(e -> System.out.printf(
//                        "\nSeason: " + e.getSeason() +
//                                " Episode: " + e.getEpisode() +
//                                " Released: " + e.getReleased().format(dateTimeFormatter)
//                ));
//
//        // Mapa com dados da temporada
//        Map<Integer, Double> seasonRating = episodes.stream()
//                .filter(e -> e.getImdbRating() > 0.0)
//                .collect(
//                        Collectors.groupingBy(Episode::getSeason,
//                                Collectors.averagingDouble(Episode::getImdbRating)
//                        ));
//
//        System.out.println(seasonRating);
//
//        // COLETANDO ESTATÍSCAS
//        DoubleSummaryStatistics est = episodes.stream()
//                .filter(e -> e.getImdbRating() > 0.0)
//                .collect(Collectors.summarizingDouble(Episode::getImdbRating));
//
//        System.out.printf("""
//                Average: %.1f
//                Highest rating: %.1f
//                Lowest rating: %.1f
//                Count: %d
//                """, est.getAverage(), est.getMax(), est.getMin(), est.getCount());
