package in.vishal.foodiesapi.controller;

import in.vishal.foodiesapi.service.GeminiService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/ai")
public class GeminiController {

    private final GeminiService geminiService;

    public GeminiController(GeminiService geminiService) {
        this.geminiService = geminiService;
    }

    @PostMapping("/description")
    public ResponseEntity<?> generateDescription(@RequestBody Map<String, String> request) {
        String dishName = request.get("dishName");

        if (dishName == null || dishName.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Dish name is required"));
        }

        try {
            String description = geminiService.generateFoodDescription(dishName);
            return ResponseEntity.ok(Map.of("description", description));
        } catch (Exception e) {
            e.printStackTrace(); // for debug
            return ResponseEntity.status(500).body(Map.of("error", "Failed to generate description"));
        }
    }
}

