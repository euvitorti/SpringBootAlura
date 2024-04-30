package br.com.alura.TechCinema.main;

import br.com.alura.TechCinema.api.Api;
import br.com.alura.TechCinema.models.ConvertData;
import br.com.alura.TechCinema.models.DataEpisode;
import br.com.alura.TechCinema.models.DataSeason;
import br.com.alura.TechCinema.models.DataSeries;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class TechCinemaApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(TechCinemaApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Api api = new Api();
        var json = api.connect("");
        ConvertData convertData = new ConvertData();
        DataSeries dataSeries = convertData.getData(json, DataSeries.class);
        System.out.println(dataSeries);

        json = api.connect("");
        DataEpisode dataEpisode = convertData.getData(json, DataEpisode.class);
        System.out.println(dataEpisode);

		List<DataSeason> seasons = new ArrayList<>();

        for (int i = 1; i < dataSeries.totalSeasons(); i++) {
			json = api.connect("" + i + "");
			DataSeason dataSeason = convertData.getData(json, DataSeason.class);
			seasons.add(dataSeason);
        }

		seasons.forEach(System.out::println);
    }
}
