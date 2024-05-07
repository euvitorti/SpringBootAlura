package br.com.alura.TechCinema.service;

import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;

public class ChatGpt {
    public static String getTranslate(String text) {
        OpenAiService service = new OpenAiService("SUA API KEY DO CHATGPT");

        CompletionRequest requeriment = CompletionRequest.builder()
                .model("gpt-3.5-turbo-instruct")
                .prompt("Traduza para o português o texto: " + text)
                .maxTokens(1000)
                .temperature(0.7)
                .build();

        var response = service.createCompletion(requeriment);
        return response.getChoices().get(0).getText();
    }
}
