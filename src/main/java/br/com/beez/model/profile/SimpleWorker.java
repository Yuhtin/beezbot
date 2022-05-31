package br.com.beez.model.profile;

import br.com.beez.model.workarea.ExtendedWorkArea;
import lombok.Getter;

@Getter
public class SimpleWorker extends ExtendedWorkArea {

    private final long userId;

    public SimpleWorker(long userId, int currentPoints, ExtendedWorkArea area) {
        super(currentPoints, area.getName(), area.getRoleId(), area.getMaxPoints());
        this.userId = userId;
    }

}
