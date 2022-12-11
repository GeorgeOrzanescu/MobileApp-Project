package eu.ase.ro.livescoringapp.database;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

import eu.ase.ro.livescoringapp.classes.CategoryWithComments;

@Dao
public interface CommentCategoryDao {
    @Transaction
    @Query("SELECT * FROM comment_category")
    List<CategoryWithComments> getAllCategoryComments();
}
