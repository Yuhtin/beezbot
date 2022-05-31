package br.com.beez.model.workarea.impl;

import br.com.beez.model.workarea.ExtendedWorkArea;

public class DeveloperArea extends ExtendedWorkArea {

    @Override
    public String name() {
        return "Desenvolvedor";
    }

    @Override
    public long roleId() {
        return 978156134433161256L;
    }

    @Override
    public int maxPoints() {
        return 1000;
    }
}
