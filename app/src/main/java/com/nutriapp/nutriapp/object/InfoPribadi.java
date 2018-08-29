package com.nutriapp.nutriapp.object;

import android.os.Parcel;
import android.os.Parcelable;

public class InfoPribadi implements Parcelable{
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public InfoPribadi createFromParcel(Parcel in) {
            return new InfoPribadi(in);
        }

        public InfoPribadi[] newArray(int size) {
            return new InfoPribadi[size];
        }
    };

    double berat;
    double tinggi;
    double skinFold;
    double lla;
    double bmi;
    double sheerFactor;
    double totalKalori;
    double totalKaloriCair;
    double persentaseKarbo;
    double persentaseProtein;
    double persentaseLemak;

    public InfoPribadi() {

    }

    public InfoPribadi(double berat, double tinggi, double skinFold, double lla, double bmi, double sheerFactor,
                       double totalKalori, double totalKaloriCair, double persentaseKarbo,
                       double persentaseProtein, double persentaseLemak) {
        this.berat = berat;
        this.tinggi = tinggi;
        this.skinFold = skinFold;
        this.lla = lla;
        this.bmi = bmi;
        this.sheerFactor = sheerFactor;
        this.totalKalori = totalKalori;
        this.totalKaloriCair = totalKaloriCair;
        this.persentaseKarbo = persentaseKarbo;
        this.persentaseProtein = persentaseProtein;
        this.persentaseLemak = persentaseLemak;
    }

    public InfoPribadi(Parcel in) {
        this.berat = Double.parseDouble(in.readString());
        this.tinggi = Double.parseDouble(in.readString());
        this.skinFold = Double.parseDouble(in.readString());
        this.lla = Double.parseDouble(in.readString());
        this.bmi = Double.parseDouble(in.readString());
        this.sheerFactor = Double.parseDouble(in.readString());
        this.totalKalori = Double.parseDouble(in.readString());
        this.totalKaloriCair = Double.parseDouble(in.readString());
        this.persentaseKarbo = Double.parseDouble(in.readString());
        this.persentaseProtein = Double.parseDouble(in.readString());
        this.persentaseLemak = Double.parseDouble(in.readString());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(this.berat);
        dest.writeDouble(this.tinggi);
        dest.writeDouble(this.skinFold);
        dest.writeDouble(this.lla);
        dest.writeDouble(this.bmi);
        dest.writeDouble(this.sheerFactor);
        dest.writeDouble(this.totalKalori);
        dest.writeDouble(this.totalKaloriCair);
        dest.writeDouble(this.persentaseKarbo);
        dest.writeDouble(this.persentaseProtein);
        dest.writeDouble(this.persentaseLemak);
    }

    @Override
    public String toString() {
        return "JadwalMakananExternal{" +
                "berat='" + berat + '\'' +
                ", tinggi='" + tinggi + '\'' +
                ", skinFold='" + skinFold + '\'' +
                ", lla='" + lla + '\'' +
                ", bmi='" + bmi + '\'' +
                ", sheerFactor='" + sheerFactor + '\'' +
                ", totalKalori='" + totalKalori + '\'' +
                ", totalKaloriCair='" + totalKaloriCair + '\'' +
                ", persentaseKarbo='" + persentaseKarbo + '\'' +
                ", persentaseProtein='" + persentaseProtein + '\'' +
                ", persentaseLemak='" + persentaseLemak + '\'' +
                '}';
    }

    public double getBerat() {
        return berat;
    }

    public void setBerat(double berat) {
        this.berat = berat;
    }

    public double getTinggi() {
        return tinggi;
    }

    public void setTinggi(double tinggi) {
        this.tinggi = tinggi;
    }

    public double getSkinFold() {
        return skinFold;
    }

    public void setSkinFold(double skinFold) {
        this.skinFold = skinFold;
    }

    public double getLla() {
        return lla;
    }

    public void setLla(double lla) {
        this.lla = lla;
    }

    public double getBmi() {
        return bmi;
    }

    public void setBmi(double bmi) {
        this.bmi = bmi;
    }

    public double getSheerFactor() {
        return sheerFactor;
    }

    public void setSheerFactor(double sheerFactor) {
        this.sheerFactor = sheerFactor;
    }

    public double getTotalKalori() {
        return totalKalori;
    }

    public void setTotalKalori(double totalKalori) {
        this.totalKalori = totalKalori;
    }

    public double getTotalKaloriCair() {
        return totalKaloriCair;
    }

    public void setTotalKaloriCair(double totalKaloriCair) {
        this.totalKaloriCair = totalKaloriCair;
    }

    public double getPersentaseKarbo() {
        return persentaseKarbo;
    }

    public void setPersentaseKarbo(double persentaseKarbo) {
        this.persentaseKarbo = persentaseKarbo;
    }

    public double getPersentaseProtein() {
        return persentaseProtein;
    }

    public void setPersentaseProtein(double persentaseProtein) {
        this.persentaseProtein = persentaseProtein;
    }

    public double getPersentaseLemak() {
        return persentaseLemak;
    }

    public void setPersentaseLemak(double persentaseLemak) {
        this.persentaseLemak = persentaseLemak;
    }
}
