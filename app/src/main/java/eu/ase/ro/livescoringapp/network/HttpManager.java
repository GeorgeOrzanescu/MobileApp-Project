package eu.ase.ro.livescoringapp.network;

import androidx.annotation.NonNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Callable;

public class HttpManager implements Callable<String> {

    private HttpURLConnection connection;
    private InputStream inputStream;
    private InputStreamReader inputStreamReader;
    private BufferedReader bufferedReader;
    private URL url;


    private final String dataURL;

    public HttpManager(String dataURL) {
        this.dataURL = dataURL;
    }

    @Override
    public String call() throws Exception {
        try{
            return getResultFromHttpResource();
        }
        catch (MalformedURLException error){
            error.printStackTrace();
        }
        catch(IOException error){
            error.printStackTrace();
        }
        finally {
            closeConnections();
        }
        return null;
    }

    @NonNull
    private String getResultFromHttpResource() throws IOException {
        url = new URL(dataURL);
        connection = (HttpURLConnection) url.openConnection();
        inputStream = connection.getInputStream();
        inputStreamReader = new InputStreamReader(inputStream);
        bufferedReader = new BufferedReader(inputStreamReader);
        StringBuilder result = new StringBuilder();
        String line;
        while((line = bufferedReader.readLine()) != null) {
            result.append(line);
        }
        return result.toString();
    }

    private void closeConnections() {

        try {
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            inputStreamReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        connection.disconnect();
    }
}
