package com.nutriapp.nutriapp.object;

public class MakananExternal {
    String jenis;
    String kalori;
    String karbohidrat;
    String protein;
    String urt;
    String lemak;
    String nama;

    public MakananExternal(String jenis, String kalori, String karbohidrat, String protein, String urt, String lemak, String nama) {
        this.jenis = jenis;
        this.kalori = kalori;
        this.karbohidrat = karbohidrat;
        this.protein = protein;
        this.urt = urt;
        this.lemak = lemak;
        this.nama = nama;
    }

    public MakananExternal(String kalori, String karbohidrat, String protein, String urt, String lemak, String nama) {
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

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public String getKalori() {
        return kalori;
    }

    public void setKalori(String kalori) {
        this.kalori = kalori;
    }

    public String getKarbohidrat() {
        return karbohidrat;
    }

    public void setKarbohidrat(String karbohidrat) {
        this.karbohidrat = karbohidrat;
    }

    public String getProtein() {
        return protein;
    }

    public void setProtein(String protein) {
        this.protein = protein;
    }

    public String getUrt() {
        return urt;
    }

    public void setUrt(String urt) {
        this.urt = urt;
    }

    public String getLemak() { return lemak; }

    public void setLemak(String lemak) { this.lemak = lemak; }
}
