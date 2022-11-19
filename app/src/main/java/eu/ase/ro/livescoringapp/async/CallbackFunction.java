package eu.ase.ro.livescoringapp.async;

public interface CallbackFunction<T> {
    void runResultOnUiThread(T result);
}
