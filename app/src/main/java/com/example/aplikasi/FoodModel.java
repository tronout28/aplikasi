package com.example.aplikasi;

import android.os.Parcel;
import android.os.Parcelable;

public class FoodModel implements Parcelable {
    private String foodname;
    private String from;
    private String fooddesk;
    private String foodimage;

    public FoodModel() {
        // Konstruktor kosong
    }

    protected FoodModel(Parcel in) {
        foodname = in.readString();
        from = in.readString();
        fooddesk = in.readString();
        foodimage = in.readString();
    }

    public static final Creator<FoodModel> CREATOR = new Creator<FoodModel>() {
        @Override
        public FoodModel createFromParcel(Parcel in) {
            return new FoodModel(in);
        }

        @Override
        public FoodModel[] newArray(int size) {
            return new FoodModel[size];
        }
    };

    public String getFoodname() {
        return foodname;
    }

    public void setfoodname(String teamname) {
        this.foodname = teamname;
    }

    public String getFoodfrom() {
        return from;
    }

    public void setfoodfrom(String stadiun) {
        this.from = stadiun;
    }

    public String getFooddesk() {
        return fooddesk;
    }

    public void setfooddescription(String teamdescription) {
        this.fooddesk = teamdescription;
    }

    public String getFoodimage() {
        return foodimage;
    }

    public void setfoodimage(String strTeamBadge) {
        this.foodimage = strTeamBadge;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(foodname);
        dest.writeString(from);
        dest.writeString(fooddesk);
        dest.writeString(foodimage);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
