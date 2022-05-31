package br.com.beez.model.user;

import br.com.beez.model.workarea.ExtendedWorkArea;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
@Accessors(fluent = true)
public class Worker {

    private final long _id;

    private final List<ExtendedWorkArea> workAreas;

    public void addWorkArea(ExtendedWorkArea workArea) {
        workAreas.add(workArea);
    }

}
