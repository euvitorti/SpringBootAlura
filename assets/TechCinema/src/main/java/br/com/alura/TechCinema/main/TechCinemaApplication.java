package br.com.alura.TechCinema.main;

import br.com.alura.TechCinema.api.Api;
import br.com.alura.TechCinema.models.ConvertData;
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
		var json = api.connect("YOURKEY");
		ConvertData convertData = new ConvertData();
		DataSeries dataSeries = convertData.getData(json, DataSeries.class);
		System.out.println(dataSeries);
	}
}
