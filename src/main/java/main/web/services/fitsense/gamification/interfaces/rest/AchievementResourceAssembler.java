package main.web.services.fitsense.gamification.interfaces.rest.transform;

import main.web.services.fitsense.gamification.domain.model.entities.UserAchievement;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AchievementResourceAssembler {

    public static List<Map<String, Object>> toResourceFromEntity(List<UserAchievement> userAchievements) {
        return userAchievements.stream().map(ua -> {
            Map<String, Object> achievementMap = Map.ofEntries(
                    Map.entry("id", ua.getAchievement().getId()),
                    Map.entry("code", ua.getAchievement().getCode()),
                    Map.entry("name", ua.getAchievement().getName()),
                    Map.entry("description", ua.getAchievement().getDescription()),
                    Map.entry("criteriaType", ua.getAchievement().getCriteriaType()),
                    Map.entry("criteriaValue", ua.getAchievement().getCriteriaValue()),
                    Map.entry("rewardPoints", ua.getAchievement().getRewardPoints()),
                    Map.entry("imageUrl", ua.getAchievement().getImageUrl()),
                    Map.entry("earnedAt", ua.getCreatedAt())
            );
            return achievementMap;
        }).collect(Collectors.toList());
    }
}
