package com.example.spring.springtheory.ch01.singleton_basic;

public class Settings {
    private static Settings settings = null;
    private String theme;
    private Settings() {}

    public static Settings getInstance() {
        if (settings == null)
            settings = new Settings();
        return settings;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }
}
