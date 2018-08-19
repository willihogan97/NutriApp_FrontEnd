package com.nutriapp.nutriapp.object;

public class MakananExternal {
    String jenis;
    String kalori;
    String karbohidrat;
    String protein;
    String urt;

    public MakananExternal(String jenis, String kalori, String karbohidrat, String protein, String urt) {
        this.jenis = jenis;
        this.kalori = kalori;
        this.karbohidrat = karbohidrat;
        this.protein = protein;
        this.urt = urt;
    }

    public MakananExternal(String kalori, String karbohidrat, String protein, String urt) {
        this.kalori = kalori;
        this.karbohidrat = karbohidrat;
        this.protein = protein;
        this.urt = urt;
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
}
