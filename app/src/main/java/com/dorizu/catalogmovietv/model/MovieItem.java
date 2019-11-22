package com.dorizu.catalogmovietv.model;

public class MovieItem {
    private String judul;
    private String tanggalRilis;
    private String overview;
    private String bahasa;
    private String thunail;
    private String banner;
    private String rate;
    private String rateCount;
    private boolean isMovie;
    private boolean isTv;


    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public boolean isMovie() {
        return isMovie;
    }

    public void setMovie(boolean movie) {
        isMovie = movie;
    }

    public boolean isTv() {
        return isTv;
    }

    public void setTv(boolean tv) {
        isTv = tv;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getTanggalRilis() {
        return tanggalRilis;
    }

    public void setTanggalRilis(String tanggalRilis) {
        this.tanggalRilis = tanggalRilis;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getBahasa() {
        return bahasa;
    }

    public void setBahasa(String bahasa) {
        this.bahasa = bahasa;
    }

    public String getThunail() {
        return thunail;
    }

    public void setThunail(String thunail) {
        this.thunail = thunail;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getRateCount() {
        return rateCount;
    }

    public void setRateCount(String rateCount) {
        this.rateCount = rateCount;
    }
}
