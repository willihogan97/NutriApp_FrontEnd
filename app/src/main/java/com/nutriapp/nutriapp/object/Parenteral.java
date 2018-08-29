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
    double volume;
    double carbohydrate;
    double protein;
    double fat;
    double electrolite;
    double calories;

    public Parenteral (String name, double volume, double carbohydrate, double protein, double fat, double electrolite, double calories){
        this.name = name;
        this.volume = volume;
        this.carbohydrate = carbohydrate;
        this.protein = protein;
        this.fat = fat;
        this.electrolite = electrolite;
        this.calories = calories;
    }

    public Parenteral(Parcel in) {
        this.name = in.readString();
        this.volume = Double.parseDouble(in.readString());
        this.carbohydrate = Double.parseDouble(in.readString());
        this.protein = Double.parseDouble(in.readString());
        this.fat = Double.parseDouble(in.readString());
        this.electrolite = Double.parseDouble(in.readString());
        this.calories = Double.parseDouble(in.readString());
    }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.name);
            dest.writeString(String.valueOf(this.calories));
            dest.writeString(String.valueOf(this.protein));
            dest.writeString(String.valueOf(this.carbohydrate));
            dest.writeString(String.valueOf(this.electrolite));
            dest.writeString(String.valueOf(this.fat));
            dest.writeString(String.valueOf(this.volume));
        }

//        @Override
//        public String toString() {
//            return "MakananParenteral{" +
//                    "nama='" + name + '\'' +
//                    ", karbohidrat='" + carbohydrate + '\'' +
//                    ", protein='" + protein + '\'' +
//                    ", lemak='" + fat + '\'' +
//                    ", elektrolit='" + electrolite + '\'' +
//                    ", volume='" + volume + '\'' +
//                    ", kalori='" + calories + '\'' +
//                    '}';
//        }
        @Override
        public String toString() {
            return this.name;
        }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public double getCarbohydrate() {
        return carbohydrate;
    }

    public void setCarbohydrate(double carbohydrate) {
        this.carbohydrate = carbohydrate;
    }

    public double getProtein() {
        return protein;
    }

    public void setProtein(double protein) {
        this.protein = protein;
    }

    public double getFat() {
        return fat;
    }

    public void setFat(double fat) {
        this.fat = fat;
    }

    public double getElectrolite() {
        return electrolite;
    }

    public void setElectrolite(double electrolite) {
        this.electrolite = electrolite;
    }

    public double getCalories() {
        return calories;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }
}
