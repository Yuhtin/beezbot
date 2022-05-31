package br.com.beez.model.user;

import br.com.beez.dto.impl.UserRepository;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true)
@Builder
public class Profile {

    private final long _id;

    private final long userId;

    private double xp;
    private int level;
    private long verified;

    public void save() {
        UserRepository.instance().replace(this);
    }

}
