package com.example.apiwork;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Base64;


public class Mask implements Parcelable {
    private int airline_id;
    private String airline_name;
    private String airline_website;
    private String image;

    public Mask(int ID, String airlineName, String airlineWebSite, String image) {
        this.airline_id = ID;
        airline_name = airlineName;
        airline_website = airlineWebSite;
        this.image = image;
    }

    protected Mask(Parcel in) {
        airline_id = in.readInt();
        airline_name = in.readString();
        airline_website = in.readString();
        image = in.readString();
    }

    public static final Creator<Mask> CREATOR = new Creator<Mask>() {
        @Override
        public Mask createFromParcel(Parcel in) {
            return new Mask(in);
        }

        @Override
        public Mask[] newArray(int size) {
            return new Mask[size];
        }
    };


    public void setAirline_id(int airline_id) {
        this.airline_id = airline_id;
    }

    public void setAirline_name(String airline_name) {
        this.airline_name = airline_name;
    }

    public void setAirline_website(String airline_website) {
        this.airline_website = airline_website;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(airline_id);
        parcel.writeString(airline_name);
        parcel.writeString(airline_website);
        parcel.writeString(image);
    }
    public Bitmap getAirlineImage(String encodedImg){
        byte[] bytes = Base64.decode(encodedImg, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes,0,bytes.length);
    }
    public String getAirline_name(){return airline_name;}
    public String getAirline_website(){return airline_website;}
    public String getImage(){return image;}
    public int getAirline_id() {
        return airline_id;
    }
}
