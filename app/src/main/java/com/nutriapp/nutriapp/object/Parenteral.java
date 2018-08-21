package com.nutriapp.nutriapp.object;

import android.os.Parcel;
import android.os.Parcelable;

public class Parenteral implements Parcelable {
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Parenteral createFromParcel(Parcel in) {
            return new Parenteral(in);
        }

        public Parenteral[] newArray(int size) {
            return new Parenteral[size];
        }
    };
        
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

    public Parenteral(Parcel in) {
        this.calories = in.readString();
        this.carbohydrate = in.readString();
        this.electrolite = in.readString();
        this.fat = in.readString();
        this.name = in.readString();
        this.protein = in.readString();
        this.volume = in.readString();
    }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.name);
            dest.writeString(this.calories);
            dest.writeString(this.protein);
            dest.writeString(this.carbohydrate);
            dest.writeString(this.electrolite);
            dest.writeString(this.fat);
            dest.writeString(this.volume);
        }

        @Override
        public String toString() {
            return "MakananParenteral{" +
                    "nama='" + name + '\'' +
                    ", karbohidrat='" + carbohydrate + '\'' +
                    ", protein='" + protein + '\'' +
                    ", lemak='" + fat + '\'' +
                    ", elektrolit='" + electrolite + '\'' +
                    ", volume='" + volume + '\'' +
                    ", kalori='" + calories + '\'' +
                    '}';
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
