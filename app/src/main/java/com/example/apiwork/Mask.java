package com.example.apiwork;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Base64;


public class Mask implements Parcelable {
    private int ID;
    private String AirlineName;
    private String AirlineWebSite;
    private String Image;

    public Mask(int ID, String airlineName, String airlineWebSite, String image) {
        this.ID = ID;
        AirlineName = airlineName;
        AirlineWebSite = airlineWebSite;
        Image = image;
    }

    protected Mask(Parcel in) {
        ID = in.readInt();
        AirlineName = in.readString();
        AirlineWebSite = in.readString();
        Image = in.readString();
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


    public void setID(int ID) {
        this.ID = ID;
    }

    //public void setProductTypeID(String productTypeID) {
    //    ProductTypeID = productTypeID;
    //}

    public void setAirlineName(String airlineName) {
        AirlineName = airlineName;
    }

    public void setAirlineWebSite(String airlineWebSite) {
        AirlineWebSite = airlineWebSite;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(ID);
        parcel.writeString(AirlineName);
        parcel.writeString(AirlineWebSite);
        parcel.writeString(Image);
    }
    public Bitmap getAirlineImage(String encodedImg){
        byte[] bytes = Base64.decode(encodedImg, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes,0,bytes.length);
    }
    public String getAirlineName(){return AirlineName;}
    public String getAirlineWebSite(){return AirlineWebSite;}
    public String getImage(){return Image;}
    public int getID() {
        return ID;
    }
}
