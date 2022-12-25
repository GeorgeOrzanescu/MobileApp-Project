package eu.ase.ro.livescoringapp.async;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AsyncTaskRunner {
    // newCachedThreadPool creates an executor that knows how many threads we can run
    private final Executor executor = Executors.newCachedThreadPool();
    // Looper gets a reference of the main thread and checks if it's ok to send data to main thread
    private final Handler handler = new Handler(Looper.getMainLooper());

    public <T> void executeAsync(Callable<T> asyncTask,CallbackFunction<T> mainThreadTask) {
        try {
            executor.execute(new RunnableTask<T>(asyncTask, handler,mainThreadTask));
        }
        catch(Exception exception){
            Log.i("AsyncTaskRunner","failed to executeAsync" + exception.getMessage());
        }
    }
}
