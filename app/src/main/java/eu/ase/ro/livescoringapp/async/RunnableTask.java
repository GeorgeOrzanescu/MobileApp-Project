package eu.ase.ro.livescoringapp.async;

import android.os.Handler;
import android.util.Log;

import java.util.concurrent.Callable;

public class RunnableTask<T> implements Runnable {

    private final Callable<T> asyncTask;
    private final Handler handler;
    private final CallbackFunction<T> mainThreadTask;

    public RunnableTask(Callable<T> asyncTask, Handler handler, CallbackFunction<T> mainThreadTask) {
        this.asyncTask = asyncTask;
        this.handler = handler;
        this.mainThreadTask = mainThreadTask;
    }

    @Override
    public void run() {
        try {
            final T result = asyncTask.call();
            handler.post(new HandlerResult<>(result,mainThreadTask));
        } catch (Exception e) {
            Log.i("Runnable task","Failed to call runnable task");
        }
    }
}
