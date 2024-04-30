package br.com.alura.TechCinema.main;

import br.com.alura.TechCinema.api.Api;
import br.com.alura.TechCinema.models.ConvertData;
import br.com.alura.TechCinema.models.DataEpisode;
import br.com.alura.TechCinema.models.DataSeries;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TechCinemaApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(TechCinemaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Api api = new Api();
		var json = api.connect("https://www.omdbapi.com/?t=gilmore+girls&apikey=cea70f0f");
		ConvertData convertData = new ConvertData();
		DataSeries dataSeries = convertData.getData(json, DataSeries.class);
		System.out.println(dataSeries);

		json = api.connect("https://www.omdbapi.com/?t=gilmore+girls&season=1&episode=1&apikey=cea70f0f");
		DataEpisode dataEpisode = convertData.getData(json, DataEpisode.class);
		System.out.println(dataEpisode);

	}
}
