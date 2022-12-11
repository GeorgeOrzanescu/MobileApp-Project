package eu.ase.ro.livescoringapp.classes;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "comment_category")
public class CommentCategory {
    @PrimaryKey(autoGenerate = true)
    long comment_category_id;

    @ColumnInfo(name = "categoryName")
    String categoryName;

    public CommentCategory(long comment_category_id, String categoryName) {
        this.comment_category_id = comment_category_id;
        this.categoryName = categoryName;
    }

    public long getComment_category_id() {
        return comment_category_id;
    }

    public void setComment_category_id(long comment_category_id) {
        this.comment_category_id = comment_category_id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public String toString() {
        return "CommentCategory{" +
                "comment_category_id=" + comment_category_id +
                ", categoryName='" + categoryName + '\'' +
                '}';
    }
}
