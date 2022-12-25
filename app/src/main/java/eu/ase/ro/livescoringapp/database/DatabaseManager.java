package eu.ase.ro.livescoringapp.database;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.sql.Array;

import eu.ase.ro.livescoringapp.classes.Comment;
import eu.ase.ro.livescoringapp.classes.CommentCategory;

@Database(entities = {Comment.class,CommentCategory.class},exportSchema = false,version = 2)
public abstract class DatabaseManager extends RoomDatabase {
    public static final String LIVE_DB = "live_db";
    private static DatabaseManager databaseManager;

    public static DatabaseManager getInstance(Context context) {
        if(databaseManager == null) {
            // synchronize two incoming calls to getInstance() - put's one in queue
            synchronized (DatabaseManager.class) {
                // the second one must check again
                if(databaseManager == null) {
                    databaseManager = Room.databaseBuilder(context,DatabaseManager.class,LIVE_DB)
                            .fallbackToDestructiveMigration() // if we change a column type this will try to convert and if not successful remove the entry
                            .build();
                }
            }
        }
        return databaseManager;
    }

    public abstract CommentDao getCommentDao();

    public abstract CommentCategoryDao getCommentCategoryDao();
}
