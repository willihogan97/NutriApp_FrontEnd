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

    public TabelMakanan(String jenis, String spinner, String berat) {
        this.jenis = jenis;
        this.spinner = spinner;
        this.berat = berat;
    }

    public TabelMakanan(Parcel in) {
        this.jenis = in.readString();
        this.spinner = in.readString();
        this.berat = in.readString();
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
    }

    @Override
    public String toString() {
        return "TabelMakan{" +
                "jenis='" + jenis + '\'' +
                ", spinner='" + spinner + '\'' +
                ", berat='" + berat + '\'' +
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
}
