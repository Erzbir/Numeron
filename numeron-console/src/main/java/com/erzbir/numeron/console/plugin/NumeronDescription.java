package com.erzbir.numeron.console.plugin;

/**
 * @author Erzbir
 * @Date: 2023/4/26 14:20
 */
public class NumeronDescription {
    private String id;
    private String name;
    private String desc;
    private String author;
    private String version;

    private NumeronDescription(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.desc = builder.desc;
        this.author = builder.author;
        this.version = builder.version;
    }

    public NumeronDescription(String id, String version) {
        this.id = id;
        this.version = version;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public static class Builder {
        private String id;
        private String name;
        private String desc;
        private String author;
        private String version;

        public Builder(String id, String version) {
            this.id = id;
            this.version = version;
        }


        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder desc(String desc) {
            this.desc = desc;
            return this;
        }

        public Builder author(String author) {
            this.author = author;
            return this;
        }

        public Builder version(String version) {
            this.version = version;
            return this;
        }

        public NumeronDescription build() {
            return new NumeronDescription(this);
        }
    }
}
