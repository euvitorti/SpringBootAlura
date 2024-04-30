package br.com.alura.TechCinema.main;

import br.com.alura.TechCinema.api.Api;

import java.util.Scanner;

public class Main {

    private Scanner scanner = new Scanner(System.in);
    private final String ADDRESS = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "";
    private Api api = new Api();

    public void menu() {
        System.out.println("Welcome!  😊");
        System.out.println("------------");
        System.out.println("Series name:");

        String seriesName = scanner.next();

        var json = api.connect( ADDRESS + seriesName.replace(" ", "+") + API_KEY);
        //https://www.omdbapi.com/?t=gilmore+girls&season=1&episode=1&apikey=cea70f0f
    }

    public static void main(String[] args) {

    }
}
