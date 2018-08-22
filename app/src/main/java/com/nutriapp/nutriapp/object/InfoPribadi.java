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

    int berat;
    int tinggi;
    String skinFold;
    String lla;
    String bmi;
    String kalori;
    String sheerFactor;
    String kkalper;
    String totalKalori;
    String totalKaloriCair;
    String persentaseKarbo;
    String persentaseProtein;
    String persentaseLemak;

    public InfoPribadi(int berat, int tinggi, String skinFold, String lla, String bmi,
                       String kalori, String sheerFactor, String kkalper, String totalKalori,
                       String totalKaloriCair, String persentaseKarbo, String persentaseProtein, String persentaseLemak) {
        this.berat = berat;
        this.tinggi = tinggi;
        this.skinFold = skinFold;
        this.lla = lla;
        this.bmi = bmi;
        this.kalori = kalori;
        this.sheerFactor = sheerFactor;
        this.kkalper = kkalper;
        this.totalKalori = totalKalori;
        this.totalKaloriCair = totalKaloriCair;
        this.persentaseKarbo = persentaseKarbo;
        this.persentaseProtein = persentaseProtein;
        this.persentaseLemak = persentaseLemak;
    }

    public InfoPribadi(Parcel in) {
        this.berat = in.readInt();
        this.tinggi = in.readInt();
        this.skinFold = in.readString();
        this.lla = in.readString();
        this.bmi = in.readString();
        this.kalori = in.readString();
        this.sheerFactor = in.readString();
        this.kkalper = in.readString();
        this.totalKalori = in.readString();
        this.totalKaloriCair = in.readString();
        this.persentaseKarbo = in.readString();
        this.persentaseProtein = in.readString();
        this.persentaseLemak = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.berat);
        dest.writeInt(this.tinggi);
        dest.writeString(this.skinFold);
        dest.writeString(this.lla);
        dest.writeString(this.bmi);
        dest.writeString(this.kalori);
        dest.writeString(this.sheerFactor);
        dest.writeString(this.kkalper);
        dest.writeString(this.totalKalori);
        dest.writeString(this.totalKaloriCair);
        dest.writeString(this.persentaseKarbo);
        dest.writeString(this.persentaseProtein);
        dest.writeString(this.persentaseLemak);
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
                ", kkalper='" + kkalper + '\'' +
                ", totalKalori='" + totalKalori + '\'' +
                ", totalKaloriCair='" + totalKaloriCair + '\'' +
                ", persentaseKarbo='" + persentaseKarbo + '\'' +
                ", persentaseProtein='" + persentaseProtein + '\'' +
                ", persentaseLemak='" + persentaseLemak + '\'' +
                '}';
    }
}
