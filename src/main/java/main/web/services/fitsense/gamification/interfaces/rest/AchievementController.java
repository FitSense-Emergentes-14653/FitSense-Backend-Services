package main.web.services.fitsense.gamification.interfaces.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import main.web.services.fitsense.gamification.application.internal.services.GamificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import main.web.services.fitsense.gamification.interfaces.rest.transform.AchievementResourceAssembler;

import java.util.Map;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(value = "/api/v1/achievements", produces = "application/json")
@Tag(name = "Achievements", description = "Gamification endpoints")
public class AchievementController {

    private final GamificationService gamificationService;

    public AchievementController(GamificationService gamificationService) {
        this.gamificationService = gamificationService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getAchievements(@PathVariable String userId) {
        var list = gamificationService.getUserAchievements(userId);
        var achievements = AchievementResourceAssembler.toResourceFromEntity(list);

        return ResponseEntity.ok(Map.of(
                "status", "success",
                "total", achievements.size(),
                "achievements", achievements
        ));
    }


    @PostMapping("/check/{userId}")
    public ResponseEntity<?> checkAchievements(@PathVariable String userId) {
        var unlocked = gamificationService.checkAndUnlockAchievements(userId);
        return ResponseEntity.ok(Map.of(
                "status", "success",
                "newUnlocked", unlocked.size(),
                "achievements", unlocked
        ));
    }
}
