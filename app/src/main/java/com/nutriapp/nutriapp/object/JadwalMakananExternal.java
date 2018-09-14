package com.nutriapp.nutriapp.object;

import android.os.Parcel;
import android.os.Parcelable;

public class JadwalMakananExternal implements Parcelable {
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public JadwalMakananExternal createFromParcel(Parcel in) {
            return new JadwalMakananExternal(in);
        }

        public JadwalMakananExternal[] newArray(int size) {
            return new JadwalMakananExternal[size];
        }
    };
    
    String jam;
    double karbo;
    double protein;
    double lemak;
    double totalKalori;
    String cara;
    double volume;

    public JadwalMakananExternal() {
    }

    public JadwalMakananExternal(String jam, double karbo, double protein, double lemak, double totalKalori, String cara, double volume) {
        this.jam = jam;
        this.karbo = karbo;
        this.protein = protein;
        this.lemak = lemak;
        this.totalKalori = totalKalori;
        this.cara = cara;
        this.volume = volume;
    }

    public JadwalMakananExternal(double karbo, double protein, double lemak, double totalKalori, String cara, double volume) {
        this.karbo = karbo;
        this.protein = protein;
        this.lemak = lemak;
        this.totalKalori = totalKalori;
        this.cara = cara;
        this.volume = volume;
    }

    public JadwalMakananExternal(Parcel in) {
        this.jam = in.readString();
        this.karbo = in.readDouble();
        this.protein = in.readDouble();
        this.lemak = in.readDouble();
        this.totalKalori = in.readDouble();
        this.cara = in.readString();
        this.volume = in.readDouble();

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.jam);
        dest.writeDouble(this.karbo);
        dest.writeDouble(this.protein);
        dest.writeDouble(this.lemak);
        dest.writeDouble(this.totalKalori);
        dest.writeString(this.cara);
        dest.writeDouble(this.volume);
    }

    @Override
    public String toString() {
        return "JadwalMakananExternal{" +
                "jam='" + jam + '\'' +
                ", karbo='" + karbo + '\'' +
                ", protein='" + protein + '\'' +
                ", lemak='" + lemak + '\'' +
                ", totalKalori='" + totalKalori + '\'' +
                ", cara='" + cara + '\'' +
                ", volume='" + volume + '\'' +
                '}';
    }

    public String getJam() {
        return jam;
    }

    public void setJam(String jam) {
        this.jam = jam;
    }

    public double getKarbo() {
        return karbo;
    }

    public void setKarbo(double karbo) {
        this.karbo = karbo;
    }

    public double getProtein() {
        return protein;
    }

    public void setProtein(double protein) {
        this.protein = protein;
    }

    public double getLemak() {
        return lemak;
    }

    public void setLemak(double lemak) {
        this.lemak = lemak;
    }

    public double getTotalKalori() {
        return totalKalori;
    }

    public void setTotalKalori(double totalKalori) {
        this.totalKalori = totalKalori;
    }

    public String getCara() {
        return cara;
    }

    public void setCara(String cara) {
        this.cara = cara;
    }

    public double getVolume() { return volume; }

    public void setVolume(double volume) { this.volume = volume; }
}
