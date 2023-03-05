package com.erzbir.mirai.numeron.plugins.cardSearch;

import java.io.Serializable;

/**
 * @author Erzbir
 * @Date: 2023/3/5 15:50
 */

// https://cdn.233.momobako.com/ygopro/pics/89631139.jpg
// https://ygocdb.com/api/v0/?search=
public class Card implements Serializable {
    private int cid;
    private long id;
    private String cn_name;
    private String sc_name;
    private String md_name;
    private String cnocg_n;
    private String jp_ruby;
    private String jp_name;
    private String en_name;
    private CardText text;
    private CardData data;
    private int weight;

    @Override
    public String toString() {
        return "id: " + id + '\n' +
                "cn_name: " + cn_name + '\n' +
                "sc_name: " + sc_name + '\n' +
                "md_name: " + md_name + '\n' +
                "cnocg_n: " + cnocg_n + '\n' +
                "jp_ruby: " + jp_ruby + '\n' +
                "jp_name: " + jp_name + '\n' +
                "en_name: " + en_name + '\n' +
                text + '\n';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCn_name() {
        return cn_name;
    }

    public void setCn_name(String cn_name) {
        this.cn_name = cn_name;
    }

    public String getSc_name() {
        return sc_name;
    }

    public void setSc_name(String sc_name) {
        this.sc_name = sc_name;
    }

    public String getMd_name() {
        return md_name;
    }

    public void setMd_name(String md_name) {
        this.md_name = md_name;
    }

    public String getCnocg_n() {
        return cnocg_n;
    }

    public void setCnocg_n(String cnocg_n) {
        this.cnocg_n = cnocg_n;
    }

    public String getJp_ruby() {
        return jp_ruby;
    }

    public void setJp_ruby(String jp_ruby) {
        this.jp_ruby = jp_ruby;
    }

    public String getJp_name() {
        return jp_name;
    }

    public void setJp_name(String jp_name) {
        this.jp_name = jp_name;
    }

    public String getEn_name() {
        return en_name;
    }

    public void setEn_name(String en_name) {
        this.en_name = en_name;
    }

    public CardText getText() {
        return text;
    }

    public void setText(CardText cardText) {
        this.text = cardText;
    }

    public CardData getData() {
        return data;
    }

    public void setData(CardData cardData) {
        this.data = cardData;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    static class CardText implements Serializable {
        private String types;
        private String pdesc;
        private String desc;

        @Override
        public String toString() {
            return types + '\n' +
                    "pdesc: " + pdesc + '\n' +
                    "desc: " + desc + '\n';
        }

        public String getTypes() {
            return types;
        }

        public void setTypes(String types) {
            this.types = types;
        }

        public String getPdesc() {
            return pdesc;
        }

        public void setPdesc(String pdesc) {
            this.pdesc = pdesc;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }

    static class CardData implements Serializable {
        private int atk;
        private int def;
        private int level;

        public int getAtk() {
            return atk;
        }

        public void setAtk(int atk) {
            this.atk = atk;
        }

        public int getDef() {
            return def;
        }

        public void setDef(int def) {
            this.def = def;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }
    }
}
