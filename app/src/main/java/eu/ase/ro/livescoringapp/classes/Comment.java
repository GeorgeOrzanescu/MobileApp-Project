package eu.ase.ro.livescoringapp.classes;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "comments")   // this let's ROOM now about this class to create a table for it
public class Comment implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id;

    @ColumnInfo(name = "category_id")
    private long category_id;

    @ColumnInfo(name = "userName")
    String userName;

    @ColumnInfo(name = "comment")
    String comment;

    @ColumnInfo(name = "sportCategory")
    String sportCategory;

    // IMPORTANT: if u have multiple constructors, the one u don't want to be used by ROOM
    // must use @Ignore on it

    @Ignore
    public Comment(String userName, String comment, String sportCategory, long category_id) {
        this.userName = userName;
        this.comment = comment;
        this.sportCategory = sportCategory;
        this.category_id = category_id;
    }

    // ROOM needs a constructor with all parameters
    public Comment(long id, String userName, String comment, String sportCategory,long category_id) {
        this.id = id;
        this.userName = userName;
        this.comment = comment;
        this.sportCategory = sportCategory;
        this.category_id = category_id;
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
        category_id = in.readLong();
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

    public long getCategory_id() {
        return category_id;
    }

    public void setCategory_id(long category_id) {
        this.category_id = category_id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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
        parcel.writeLong(category_id);
    }
}
