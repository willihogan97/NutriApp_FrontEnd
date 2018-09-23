package com.nutriapp.nutriapp.object;

import android.os.Parcel;
import android.os.Parcelable;

public class TabelMakanan implements Parcelable {
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public TabelMakanan createFromParcel(Parcel in) {
            return new TabelMakanan(in);
        }

        public TabelMakanan[] newArray(int size) {
            return new TabelMakanan[size];
        }
    };

    String jenis;
    String spinner;
    String berat;
    String totalKalori;
    String jam;
    int position;

    public TabelMakanan(String jenis, String spinner, String berat, String totalKalori) {
        this.jenis = jenis;
        this.spinner = spinner;
        this.berat = berat;
        this.totalKalori = totalKalori;
    }

    public TabelMakanan(String jam, String spinner, String berat) {
        this.spinner = spinner;
        this.berat = berat;
        this.jam = jam;
    }

    public TabelMakanan(String jenis, String spinner, String berat, String totalKalori, String jam) {
        this.jenis = jenis;
        this.spinner = spinner;
        this.berat = berat;
        this.totalKalori = totalKalori;
        this.jam = jam;
    }

    public TabelMakanan(Parcel in) {
        this.jenis = in.readString();
        this.spinner = in.readString();
        this.berat = in.readString();
        this.totalKalori = in.readString();
        this.jam = in.readString();
        this.position = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.jenis);
        dest.writeString(this.spinner);
        dest.writeString(this.berat);
        dest.writeString(this.totalKalori);
        dest.writeString(this.jam);
        dest.writeInt(this.position);
    }

    @Override
    public String toString() {
        return "TabelMakan{" +
                "jenis='" + jenis + '\'' +
                ", spinner='" + spinner + '\'' +
                ", berat='" + berat + '\'' +
                ", totalKalori='" + totalKalori + '\'' +
                ", jam='" + jam + '\'' +
                '}';
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public String getSpinner() {
        return spinner;
    }

    public void setSpinner(String spinner) {
        this.spinner = spinner;
    }

    public String getBerat() {
        return berat;
    }

    public void setBerat(String berat) {
        this.berat = berat;
    }

    public String getTotalKalori() {
        return totalKalori;
    }

    public void setTotalKalori(String totalKalori) {
        this.totalKalori = totalKalori;
    }

    public String getJam() {
        return jam;
    }

    public void setJam(String jam) {
        this.jam = jam;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
