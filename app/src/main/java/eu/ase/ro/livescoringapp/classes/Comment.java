package eu.ase.ro.livescoringapp.classes;

import android.os.Parcel;
import android.os.Parcelable;

public class Comment implements Parcelable {
    String userName;
    String comment;
    String sportCategory;

    public Comment(String userName, String comment, String sportCategory) {
        this.userName = userName;
        this.comment = comment;
        this.sportCategory = sportCategory;
    }

    @Override
    public String toString() {
        return sportCategory+ " - " + userName + " said: " + comment;
    }

    // generated
    protected Comment(Parcel in) {
        userName = in.readString();
        comment = in.readString();
        sportCategory = in.readString();
    }
    // generated
    public static final Creator<Comment> CREATOR = new Creator<Comment>() {
        @Override
        public Comment createFromParcel(Parcel in) {
            return new Comment(in);
        }

        @Override
        public Comment[] newArray(int size) {
            return new Comment[size];
        }
    };

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getSportCategory() {
        return sportCategory;
    }

    public void setSportCategory(String sportCategory) {
        this.sportCategory = sportCategory;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    // Comment transforms to parcel
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(userName);
        parcel.writeString(comment);
        parcel.writeString(sportCategory);
    }
}
