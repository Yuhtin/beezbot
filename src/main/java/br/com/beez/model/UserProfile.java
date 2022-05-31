package br.com.beez.model;

import lombok.Builder;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
@Builder
public class UserProfile {

    private double polem;
    private long dailyResetTimestamp;
    @Builder.Default private Map<WorkArea, Integer> pointsPerWorkArea = new HashMap<>();
    
}
