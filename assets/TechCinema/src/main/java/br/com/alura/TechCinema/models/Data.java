package br.com.alura.TechCinema.models;

public interface Data {

    // QUANDO NAO SABE COMO OS DAOOS VÃO VIM, USA O SEGUINTE PARA PEGAR O GENÉRICO
    <T> T getData(String json, Class<T> grade);
}
