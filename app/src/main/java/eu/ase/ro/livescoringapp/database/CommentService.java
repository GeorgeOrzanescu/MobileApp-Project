package eu.ase.ro.livescoringapp.database;

import android.content.Context;

import java.util.List;
import java.util.concurrent.Callable;

import eu.ase.ro.livescoringapp.async.AsyncTaskRunner;
import eu.ase.ro.livescoringapp.async.CallbackFunction;
import eu.ase.ro.livescoringapp.classes.Comment;

public class CommentService {

    private final CommentDao commentDao;
    private final AsyncTaskRunner asyncTaskRunner;

    public CommentService(Context context) {
        this.commentDao = DatabaseManager.getInstance(context).getCommentDao();
        this.asyncTaskRunner = new AsyncTaskRunner();
    }

    public void insert(Comment comment, CallbackFunction<Comment> activityThread) {
        Callable<Comment> insertOperation = new Callable<Comment>() {
            @Override
            public Comment call() throws Exception {
                // here the insert operation in db happens ( another thread )
                if(comment == null || comment.getId() > 0) {
                    return null;
                }
                long id = commentDao.insert(comment);
                if(id < 0) {
                    return null;
                }
                comment.setId(id);
                return comment;
            }
        };

        asyncTaskRunner.executeAsync(insertOperation,activityThread);
    }

    public void getAll(CallbackFunction<List<Comment>> activityThread) {
        Callable<List<Comment>> getAllOperation =  new Callable<List<Comment>>() {
            @Override
            public List<Comment> call() throws Exception {
                return commentDao.getAll();
            }
        };

        asyncTaskRunner.executeAsync(getAllOperation,activityThread);
    }

    public void delete(Comment comment, CallbackFunction<Integer> activityThread) {
        Callable<Integer> deleteOperation = new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                if(comment == null || comment.getId() < 0) {
                    return null;
                }
                int numberOfItemsDeleted = commentDao.delete(comment);

                return numberOfItemsDeleted;
            }
        };

        asyncTaskRunner.executeAsync(deleteOperation,activityThread);
    }
}
