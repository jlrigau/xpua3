package com.android.xpua.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Location implements Parcelable {

    public int latitude;
    public int longitude;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(latitude);
        dest.writeInt(longitude);
    }

    public static final Parcelable.Creator<Location> CREATOR = new Parcelable.Creator<Location>() {
        @Override
        public Location createFromParcel(Parcel source) {
            return new Location(source);
        }

        @Override
        public Location[] newArray(int size) {
            return new Location[size];
        }
    };

    public Location(){
    }

    public Location(Parcel source) {
        this.latitude = source.readInt();
        this.longitude = source.readInt();
    }
}
