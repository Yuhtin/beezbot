package br.com.beez.model.workarea;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ExtendedWorkArea implements IWorkArea {

    private int currentPoints;

    private final String name;
    private final long roleId;
    private final int maxPoints;

    @Override
    public String name() {
        return name;
    }

    @Override
    public long roleId() {
        return roleId;
    }

    @Override
    public int maxPoints() {
        return maxPoints;
    }
}
