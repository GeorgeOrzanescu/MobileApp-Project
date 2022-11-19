package eu.ase.ro.livescoringapp.async;


public class HandlerResult<T> implements Runnable {

    // final because the we don't want the reference of the below to change in an instance of a handler
    private final T result;
    private final CallbackFunction<T> mainThreadTask;

    public HandlerResult(T result, CallbackFunction<T> mainThreadTask) {
        this.result = result;
        this.mainThreadTask = mainThreadTask;
    }

    @Override
    public void run() {
        mainThreadTask.runResultOnUiThread(result);
    }
}
