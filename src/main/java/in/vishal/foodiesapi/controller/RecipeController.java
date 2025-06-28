package in.vishal.foodiesapi.controller;

import in.vishal.foodiesapi.service.GeminiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/ai")
public class RecipeController {
    private final GeminiService geminiService;

    public RecipeController(GeminiService geminiService) {
        this.geminiService = geminiService;
    }

    @PostMapping("/recipe")
    public ResponseEntity<String> getRecipe(@RequestBody Map<String, String> body) {
        String name = body.get("name");
        String recipe = geminiService.generateRecipe(name);
        return ResponseEntity.ok(recipe);
    }

}
