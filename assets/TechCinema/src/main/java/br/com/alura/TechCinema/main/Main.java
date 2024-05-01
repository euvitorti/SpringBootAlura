package br.com.alura.TechCinema.main;

import br.com.alura.TechCinema.api.Api;
import br.com.alura.TechCinema.models.ConvertData;
import br.com.alura.TechCinema.models.DataEpisode;
import br.com.alura.TechCinema.models.DataSeason;
import br.com.alura.TechCinema.models.DataSeries;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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

        // LAMBDA
        seasons.forEach(t -> t.dataEpisodes().forEach(e -> System.out.println(e.Title())));
    }

}
