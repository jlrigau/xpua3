package com.android.xpua.model;


import android.os.Parcel;
import android.os.Parcelable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Artist implements Parcelable{

    public String name;
    public String firstName;
    public Location location;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(firstName);
        dest.writeParcelable(location, flags);
    }

    public static final Parcelable.Creator<Artist> CREATOR = new Parcelable.Creator<Artist>() {
        @Override
        public Artist createFromParcel(Parcel source) {
            return new Artist(source);
        }

        @Override
        public Artist[] newArray(int size) {
            return new Artist[size];
        }
    };

    public Artist(){
    }

    public Artist(Parcel source) {
        this.name = source.readString();
        this.firstName = source.readString();
        this.location = source.readParcelable(getClass().getClassLoader());
    }
}
