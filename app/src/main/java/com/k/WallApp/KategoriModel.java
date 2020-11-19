package com.k.WallApp;

public class KategoriModel {
    private int resimUrl;
    private String kategoriAdi;
    public KategoriModel(){}

    public KategoriModel(int resimUrl, String kategoriAdi) {
        this.resimUrl = resimUrl;
        this.kategoriAdi = kategoriAdi;
    }

    public int getResimUrl() {
        return resimUrl;
    }

    public void setResimUrl(int resimUrl) {
        this.resimUrl = resimUrl;
    }

    public String getKategoriAdi() {
        return kategoriAdi;
    }

    public void setKategoriAdi(String kategoriAdi) {
        this.kategoriAdi = kategoriAdi;
    }
}
