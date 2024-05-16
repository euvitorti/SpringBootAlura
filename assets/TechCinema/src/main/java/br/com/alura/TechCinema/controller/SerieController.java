package br.com.alura.TechCinema.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SerieController {

    // DEFININDO A ROTA
    @GetMapping("/series")
    public String getSerie() {
        return "Welcome bro 😎";
    }
}
