package com.erzbir.numeron.component;

/**
 * @author Erzbir
 * @Data: 2024/2/7 19:16
 */
public abstract class AbstractComponent implements Component {
    protected String name;
    protected String id;

    public AbstractComponent(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getId() {
        return id;
    }
}
