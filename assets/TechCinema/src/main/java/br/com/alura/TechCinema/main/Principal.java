package br.com.alura.TechCinema.main;

import br.com.alura.TechCinema.models.*;
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

    private  Optional<Serie> searchSerieNameOptional;

    public Principal(SerieRepository serieRepository) {
        this.serieRepository = serieRepository;
    }

    // O CÓDIGO A SEGUIR, É FORMATO DE CONSOLE
    // HABILITE O A CLASSE TechCinemaApplicationWithOutWeb.Java PARA USAR O FORMATO CONSOLE
    public void menu() {
        byte choice = -1;

        while (choice != 0) {
            System.out.println("""
                    Welcome! 🎥
                    ------------------------------------
                    [1] Buscar Série
                    [2] Buscar Episódio
                    [3] Listar Série
                    [4] Buscar por título
                    [5] Buscar por ator
                    [6] Top 5 Séries
                    [7] Buscar por categoria
                    [8] Buscar por temporada e avaliação
                    [9] Buscar por trecho
                    [10] Top 5 Episódiosn
                    [11] Buscar por data
                    ------------------------------------
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
                case 5:
                    searchByActor();
                    break;
                case 6:
                    topFive();
                    break;
                case 7:
                    searchByCategory();
                    break;
                case 8:
                    searchBySeasonAndRating();
                    break;
                case 9:
                    findBySnippet();
                    break;
                case 10:
                    topFiveEpisode();
                    break;
                case 11:
                    searchByDate();
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

        searchSerieNameOptional = serieRepository.findByTitleContainsIgnoreCase(serieName);

        if (searchSerieNameOptional.isPresent()) {
            System.out.println(searchSerieNameOptional.get());
        } else {
            System.out.println("Não encontrada.");
        }
    }

    private void searchByActor() {
        System.out.println("Digite o nome do ator: ");
        var actorName = scanner.nextLine();

        Optional<Serie> seriesFoundList = serieRepository.findByActorsContainsIgnoreCase(actorName);

        if (seriesFoundList.isPresent()) {
            System.out.println(seriesFoundList.get());
        } else {
            System.out.println("Ator(a) não encontrado.");
        }
    }

    private void topFive() {
        List<Serie> topFiveList = serieRepository.findTop5ByOrderByImdbRatingDesc();

        topFiveList.forEach(s ->
                System.out.printf("""
                        Título: %s.
                        Avaliação: %.1f
                        """, s.getTitle(), s.getImdbRating()));
    }

    private void searchByCategory() {
        System.out.println("Qual gênero? ");
        String typeGenre = scanner.nextLine();

        Category category = Category.fromPortuguese(typeGenre);

        List<Serie> seriesCategoryList = serieRepository.findByGenre(category);

        System.out.printf("\nSéries de %s.", typeGenre);

        seriesCategoryList.forEach(System.out::println);
    }

    private void searchBySeasonAndRating() {
        System.out.println("Filtrar séries até quantas temporadas? ");
        var season = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Média da avaliação: ");
        var rating =  scanner.nextDouble();
        scanner.nextLine();

        List<Serie> filterSerie = serieRepository.seriesBySeasonAndRating(season, rating);
        filterSerie.forEach(s ->
                System.out.printf("""
                        ------------------------
                        Título: %s.
                        Avaliação: %.1f
                        ------------------------
                        """, s.getTitle(), s.getImdbRating()));
    }

    private void findBySnippet() {
        System.out.println("Nome do episódio: ");
        String episodeSnippet = scanner.nextLine();

        List<Episode> episodeSnippetList = serieRepository.episodeBySnippet(episodeSnippet);
        episodeSnippetList.forEach(e ->
                System.out.printf("""
                        ------------------------
                        Série: %s
                        Temporada: %d
                        Episódio: %s
                        Título: %s.
                        ------------------------
                        """,
                        e.getSerie().getTitle(),
                        e.getSeason(),
                        e.getEpisode(),
                        e.getTitle()));
    }

    private void topFiveEpisode() {
        searchSerieByTitle();

        Serie serie = searchSerieNameOptional.get();
        List<Episode> topFiveEpisode = serieRepository.topFiveEpisode(serie);
        topFiveEpisode.forEach(e ->
                System.out.printf("""
                        ----------------------------------
                        Série: %s
                        Temporada: %d
                        Episódio: %s
                        Título: %s
                        Avaliação: %f
                        """,
                        e.getSerie().getTitle(),
                        e.getSeason(),
                        e.getEpisode(),
                        e.getTitle(),
                        e.getImdbRating()));
    }

    private void searchByDate() {
        searchSerieByTitle();

        System.out.println("Digite o ano: ");
        int year = scanner.nextInt();

        Serie serie = searchSerieNameOptional.get();

        List<Episode> episodesYearList = serieRepository.findByYear(serie, year);
        episodesYearList.forEach(System.out::println);
    }
}
