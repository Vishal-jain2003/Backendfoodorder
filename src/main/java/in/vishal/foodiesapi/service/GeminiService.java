package in.vishal.foodiesapi.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class GeminiService {

    @Value("${gemini.api.key}")
    private String apiKey;

    private static final String GEMINI_URL =
            "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=";


    public String generateFoodDescription(String dishName) {
        RestTemplate restTemplate = new RestTemplate();

        // 🧠 Create the prompt
        String prompt = "Give a simple easy english desi, delicious under thirty words accurate description  \"" + dishName + "\". No headings, no options, just the sentence.";



        // 📦 Gemini expects: { contents: [{ parts: [{ text: "..." }] }] }
        Map<String, Object> body = Map.of(
                "contents", List.of(
                        Map.of("parts", List.of(Map.of("text", prompt)))
                )
        );

        // 🛡 Headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 🔗 Send request
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
        ResponseEntity<Map> response = restTemplate.postForEntity(GEMINI_URL + apiKey, request, Map.class);

        // 🔄 Extract description from response
        Map content = (Map) ((List) response.getBody().get("candidates")).get(0);
        Map part = (Map) ((List) ((Map) content.get("content")).get("parts")).get(0);

        return part.get("text").toString();
    }

    public String generateRecipe(String dishName) {
        RestTemplate restTemplate = new RestTemplate();
        String prompt = String.format("""
        You are a  home chef. Give a clear, step-by-step  recipe for "%s".
        Keep it simple english with desi fun , give best explantion from zero to zenith, and beginner-friendly.
        Make step by step best so that it looks better in frontend also with everything and tasty and best recipe
        and english simple and easy to understand and not too long 7
        Do not include heading or intro. Just steps directly.
        """, dishName);

        Map<String, Object> body = Map.of(
                "contents", List.of(
                        Map.of("parts", List.of(Map.of("text", prompt)))
                )
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);
        String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=" + apiKey;

        ResponseEntity<Map> response = restTemplate.postForEntity(url, entity, Map.class);

        Map content = (Map) ((List) response.getBody().get("candidates")).get(0);
        Map part = (Map) ((List) ((Map) content.get("content")).get("parts")).get(0);

        return part.get("text").toString();
    }

}
