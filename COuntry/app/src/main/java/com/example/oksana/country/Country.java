package com.example.oksana.country;

public class Country {
    private String name;
    private String capital;
    private String subregion;
    private String population;
    private String area;
    private String alpha2Code;

    private String flagDrawable;

    public Country(String name, String capital, String subregion, String population, String area, String alpha2Code, String drawable) {
        this.name = name;
        this.capital = capital;
        this.subregion = subregion;
        this.population = population;
        this.area = area;
        this.alpha2Code = alpha2Code;
        this.flagDrawable = drawable;
    }

    public String getName() {
        return name;
    }

    public String getCapital() {
        return capital;
    }

    public String getSubregion() {
        return subregion;
    }

    public String getPopulation() {
        return population;
    }

    public String getArea() {
        return area;
    }

    public String getAlpha2Code() {
        return alpha2Code;
    }

    public String getFlagDrawable() {
        return flagDrawable;
    }

}
