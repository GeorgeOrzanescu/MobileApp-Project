package eu.ase.ro.livescoringapp.database;

import android.content.Context;

import java.util.List;
import java.util.concurrent.Callable;

import eu.ase.ro.livescoringapp.async.AsyncTaskRunner;
import eu.ase.ro.livescoringapp.async.CallbackFunction;
import eu.ase.ro.livescoringapp.classes.CategoryWithComments;

public class CategoryWithCommentsService {

    private final CommentCategoryDao commentCategoryDao;
    private final AsyncTaskRunner asyncTaskRunner;

    public CategoryWithCommentsService(Context context) {
        this.commentCategoryDao =  DatabaseManager.getInstance(context).getCommentCategoryDao();
        this.asyncTaskRunner = new AsyncTaskRunner();;
    }

    public void getAll(CallbackFunction<List<CategoryWithComments>> activityThread) {
        Callable<List<CategoryWithComments>> getAllOperation = new Callable<List<CategoryWithComments>>() {
            @Override
            public List<CategoryWithComments> call() throws Exception {
                return commentCategoryDao.getAllCategoryComments();
            }
        };

        asyncTaskRunner.executeAsync(getAllOperation,activityThread);
    }
}
