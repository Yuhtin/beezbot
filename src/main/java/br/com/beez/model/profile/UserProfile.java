package br.com.beez.model.profile;

import br.com.beez.dto.impl.UserRepository;
import br.com.beez.model.workarea.ExtendedWorkArea;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(fluent = true)
@Builder
public class UserProfile {

    private final long _id;

    private final long userId;

    private double polens;
    private long dailyResetTimestamp;


    @Builder.Default private List<ExtendedWorkArea> pointsPerWorkArea = new ArrayList<>();

    public void save() {
        UserRepository.instance().replace(this);
    }

}
