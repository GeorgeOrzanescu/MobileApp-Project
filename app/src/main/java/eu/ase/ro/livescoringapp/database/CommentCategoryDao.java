package eu.ase.ro.livescoringapp.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

import eu.ase.ro.livescoringapp.classes.CategoryWithComments;
import eu.ase.ro.livescoringapp.classes.CommentCategory;

@Dao
public interface CommentCategoryDao {
    @Transaction
    @Query("SELECT * FROM comment_category")
    List<CategoryWithComments> getAllCategoryComments();

    @Insert
    long insert(CommentCategory commentCategory);
}
