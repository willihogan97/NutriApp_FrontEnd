package com.nutriapp.nutriapp.object;

public class Parenteral {
    String name;
    String volume;
    String carbohydrate;
    String protein;
    String fat;
    String electrolite;
    String calories;

    public Parenteral (String name, String volume, String carbohydrate, String protein, String fat, String electrolite, String calories){
        this.name = name;
        this.volume = volume;
        this.carbohydrate = carbohydrate;
        this.protein = protein;
        this.fat = fat;
        this.electrolite = electrolite;
        this.calories = calories;
    }

    public Parenteral (String name, String volume){
        this.name = name;
        this.volume = volume;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getCarbohydrate() {
        return carbohydrate;
    }

    public void setCarbohydrate(String carbohydrate) {
        this.carbohydrate = carbohydrate;
    }

    public String getProtein() {
        return protein;
    }

    public void setProtein(String protein) {
        this.protein = protein;
    }

    public String getFat() {
        return fat;
    }

    public void setFat(String fat) {
        this.fat = fat;
    }

    public String getElectrolite() {
        return electrolite;
    }

    public void setElectrolite(String electrolite) {
        this.electrolite = electrolite;
    }

    public String getCalories() {
        return calories;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }
}
