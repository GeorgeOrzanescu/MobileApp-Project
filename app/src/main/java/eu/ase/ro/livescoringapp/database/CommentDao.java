package eu.ase.ro.livescoringapp.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import eu.ase.ro.livescoringapp.classes.Comment;

// every class u use as entity/ table in db must have a DATA ACCESS OBJECT interface
@Dao
public interface CommentDao {

    @Insert
    long insert(Comment comment);

    @Query("SELECT * FROM comments")
    List<Comment> getAll();

    @Delete
    int delete(Comment comment);
}
