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
    String karbo;
    String protein;
    String lemak;
    int totalKalori;

    public JadwalMakananExternal() {
    }

    public JadwalMakananExternal(String jam, String karbo, String protein, String lemak, int totalKalori) {
        this.jam = jam;
        this.karbo = karbo;
        this.protein = protein;
        this.lemak = lemak;
        this.totalKalori = totalKalori;
    }

    public JadwalMakananExternal(String karbo, String protein, String lemak, int totalKalori) {
        this.karbo = karbo;
        this.protein = protein;
        this.lemak = lemak;
        this.totalKalori = totalKalori;
    }

    public JadwalMakananExternal(Parcel in) {
        this.jam = in.readString();
        this.karbo = in.readString();
        this.protein = in.readString();
        this.lemak = in.readString();
        this.totalKalori = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.jam);
        dest.writeString(this.karbo);
        dest.writeString(this.protein);
        dest.writeString(this.lemak);
        dest.writeInt(this.totalKalori);
    }

    @Override
    public String toString() {
        return "JadwalMakananExternal{" +
                "jam='" + jam + '\'' +
                ", karbo='" + karbo + '\'' +
                ", protein='" + protein + '\'' +
                ", lemak='" + lemak + '\'' +
                ", totalKalori='" + totalKalori + '\'' +
                '}';
    }

    public String getJam() {
        return jam;
    }

    public void setJam(String jam) {
        this.jam = jam;
    }

    public String getKarbo() {
        return karbo;
    }

    public void setKarbo(String karbo) {
        this.karbo = karbo;
    }

    public String getProtein() {
        return protein;
    }

    public void setProtein(String protein) {
        this.protein = protein;
    }

    public String getLemak() {
        return lemak;
    }

    public void setLemak(String lemak) {
        this.lemak = lemak;
    }

    public int getTotalKalori() {
        return totalKalori;
    }

    public void setTotalKalori(int totalKalori) {
        this.totalKalori = totalKalori;
    }
}
