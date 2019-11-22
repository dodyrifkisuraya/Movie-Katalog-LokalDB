package com.dorizu.catalogmovietv.model;

import android.os.Parcel;
import android.os.Parcelable;

public class FavoritItem implements Parcelable {
    private String judul, overview, thumnail;

    private int id;

    public FavoritItem(String judul, String overview, String thumnail, int id) {
        this.judul = judul;
        this.overview = overview;
        this.thumnail = thumnail;
        this.id = id;
    }

    public FavoritItem() {
    }

    protected FavoritItem(Parcel in){
        judul = in.readString();
        overview = in.readString();
        thumnail = in.readString();
        id = in.readInt();
    }

    public static final Creator<FavoritItem> CREATOR = new Creator<FavoritItem>() {
        @Override
        public FavoritItem createFromParcel(Parcel in) {
            return new FavoritItem(in);
        }

        @Override
        public FavoritItem[] newArray(int size) {
            return new FavoritItem[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getThumnail() {
        return thumnail;
    }

    public void setThumnail(String thumnail) {
        this.thumnail = thumnail;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(judul);
        parcel.writeString(overview);
        parcel.writeString(thumnail);
        parcel.writeInt(id);
    }
}
