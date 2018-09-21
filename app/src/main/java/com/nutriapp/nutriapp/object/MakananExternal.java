package com.nutriapp.nutriapp.object;

public class MakananExternal {
    int jenis;
    double kalori;
    double karbohidrat;
    double protein;
    double urt;
    double lemak;
    String nama;
    int id;

    public MakananExternal(int jenis, double kalori, double karbohidrat, double protein, double urt, double lemak, String nama) {
        this.jenis = jenis;
        this.kalori = kalori;
        this.karbohidrat = karbohidrat;
        this.protein = protein;
        this.urt = urt;
        this.lemak = lemak;
        this.nama = nama;
    }

    public MakananExternal(int jenis, double kalori, double karbohidrat, double protein, double urt, double lemak, String nama, int id) {
        this.jenis = jenis;
        this.kalori = kalori;
        this.karbohidrat = karbohidrat;
        this.protein = protein;
        this.urt = urt;
        this.lemak = lemak;
        this.nama = nama;
        this.id = id;
    }

    @Override
    public String toString() {
        return this.nama;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public int getJenis() {
        return jenis;
    }

    public void setJenis(int jenis) {
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

    public double getUrt() {
        return urt;
    }

    public void setUrt(double urt) {
        this.urt = urt;
    }

    public double getLemak() { return lemak; }

    public void setLemak(double lemak) { this.lemak = lemak; }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
