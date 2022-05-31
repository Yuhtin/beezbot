package br.com.beez.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class WorkArea {

    private final String name;
    private final long roleId;
    private final int maxPoints;

}
