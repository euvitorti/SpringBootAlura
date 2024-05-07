package br.com.alura.TechCinema.main;

import br.com.alura.TechCinema.service.Api;
import br.com.alura.TechCinema.models.*;
import br.com.alura.TechCinema.service.ConvertData;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {

    private Scanner scanner = new Scanner(System.in);

    private Api api = new Api();

    private ConvertData convertData = new ConvertData();

    private final String ADDRESS = "https://www.omdbapi.com/?t=";

    private final String API_KEY = "YOUR API KEY";

    private List<DataSeries> listDataSeries = new ArrayList<>();

    public void menu() {
        byte choice = -1;

        while (choice != 0) {
            System.out.println("""
                    Welcome! 🎥
                    -------------------
                    [1] Buscar Série
                    [2] Buscar Episódio
                    [3] Listar Série
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
                case 0:
                    System.out.println("Goodbye👋");
                    break;
                default:
                    System.out.println("Opção inválida.");
                    break;
            }
        }
    }

    private void searchSerie() {
        DataSeries data = getDataSerie();
        listDataSeries.add(data);
        listSearchedSeries();
//        System.out.println(data);
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

    private void searchEpisode() {
        DataSeries dataSeries = getDataSerie();
        List<DataSeason> seasons = new ArrayList<>();

        for (int i = 1; i <= dataSeries.totalSeasons(); i++) {
            var json = api.connect(ADDRESS + dataSeries.Title().replace(" ", "+") + "&season=" + i + API_KEY);
            DataSeason dataSeason = convertData.getData(json, DataSeason.class);
            seasons.add(dataSeason);
        }

        seasons.forEach(System.out::println);
    }

    private void listSearchedSeries() {
        List<Serie> serieList = new ArrayList<>();

        serieList = listDataSeries.stream()
                .map(d -> new Serie(d))
                .collect(Collectors.toList());

        serieList.stream()
                .sorted(Comparator.comparing(Serie::getGenre))
                .forEach(System.out::println);
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
