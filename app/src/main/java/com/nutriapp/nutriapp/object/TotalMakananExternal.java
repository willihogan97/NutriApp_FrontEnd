package com.nutriapp.nutriapp.object;

import android.os.Parcel;
import android.os.Parcelable;

public class TotalMakananExternal implements Parcelable {
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public TotalMakananExternal createFromParcel(Parcel in) {
            return new TotalMakananExternal(in);
        }

        public TotalMakananExternal[] newArray(int size) {
            return new TotalMakananExternal[size];
        }
    };

    String jenis;
    String totalKarbo;
    String totalProtein;
    String totalLemak;
    int totalKalori;

    public TotalMakananExternal() {
    }

    public TotalMakananExternal(String jenis, String totalKarbo, String totalProtein, String totalLemak, int totalKalori) {
        this.jenis = jenis;
        this.totalKarbo = totalKarbo;
        this.totalProtein = totalProtein;
        this.totalLemak = totalLemak;
        this.totalKalori = totalKalori;
    }

    public TotalMakananExternal(String karbo, String protein, String lemak, int totalKalori) {
        this.totalKarbo = karbo;
        this.totalProtein = protein;
        this.totalLemak = lemak;
        this.totalKalori = totalKalori;
    }

    public TotalMakananExternal(Parcel in) {
        this.jenis = in.readString();
        this.totalKarbo = in.readString();
        this.totalProtein = in.readString();
        this.totalLemak = in.readString();
        this.totalKalori = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.jenis);
        dest.writeString(this.totalKarbo);
        dest.writeString(this.totalProtein);
        dest.writeString(this.totalLemak);
        dest.writeInt(this.totalKalori);
    }

    @Override
    public String toString() {
        return "JadwalMakananExternal{" +
                "jam='" + jenis + '\'' +
                ", karbo='" + totalKarbo + '\'' +
                ", protein='" + totalProtein + '\'' +
                ", lemak='" + totalLemak + '\'' +
                ", totalKalori='" + totalKalori + '\'' +
                '}';
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public String getTotalKarbo() {
        return totalKarbo;
    }

    public void setTotalKarbo(String totalKarbo) {
        this.totalKarbo = totalKarbo;
    }

    public String getTotalProtein() {
        return totalProtein;
    }

    public void setTotalProtein(String totalProtein) {
        this.totalProtein = totalProtein;
    }

    public String getTotalLemak() {
        return totalLemak;
    }

    public void setTotalLemak(String totalLemak) {
        this.totalLemak = totalLemak;
    }

    public int getTotalKalori() {
        return totalKalori;
    }

    public void setTotalKalori(int totalKalori) {
        this.totalKalori = totalKalori;
    }
}
