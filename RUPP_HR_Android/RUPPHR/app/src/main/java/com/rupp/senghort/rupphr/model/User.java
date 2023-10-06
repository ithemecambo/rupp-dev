package com.rupp.senghort.rupphr.model;

/**
 * Created by KHEANG SENGHORT on 3/30/2018.
 */

public class User {

    private String icon;
    private String header;
    private String text;

    public User(String header, String text) {
        this.header = header;
        this.text = text;
    }

    public User(String icon, String header, String text) {
        this.icon = icon;
        this.header = header;
        this.text = text;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
