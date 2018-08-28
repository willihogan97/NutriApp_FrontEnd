package com.nutriapp.nutriapp.object;

public class MakananExternal {
    double jenis;
    double kalori;
    double karbohidrat;
    double protein;
    String urt;
    double lemak;
    String nama;

    public MakananExternal(double jenis, double kalori, double karbohidrat, double protein, String urt, double lemak, String nama) {
        this.jenis = jenis;
        this.kalori = kalori;
        this.karbohidrat = karbohidrat;
        this.protein = protein;
        this.urt = urt;
        this.lemak = lemak;
        this.nama = nama;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public double getJenis() {
        return jenis;
    }

    public void setJenis(double jenis) {
        this.jenis = jenis;
    }

    public double getKalori() {
        return kalori;
    }

    public void setKalori(double kalori) {
        this.kalori = kalori;
    }

    public double getKarbohidrat() {
        return karbohidrat;
    }

    public void setKarbohidrat(double karbohidrat) {
        this.karbohidrat = karbohidrat;
    }

    public double getProtein() {
        return protein;
    }

    public void setProtein(double protein) {
        this.protein = protein;
    }

    public String getUrt() {
        return urt;
    }

    public void setUrt(String urt) {
        this.urt = urt;
    }

    public double getLemak() { return lemak; }

    public void setLemak(double lemak) { this.lemak = lemak; }
}
