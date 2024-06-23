package br.com.alura.TechCinema.service.dataconverter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConvertData implements Data {
    // PARA CONVERTER DADOS USA A CLASSE OBJECT MAPPER

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public <T> T getData(String json, Class<T> grade) {
        try {
            return objectMapper.readValue(json, grade);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
