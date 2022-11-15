package eu.ase.ro.livescoringapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import eu.ase.ro.livescoringapp.R;
import eu.ase.ro.livescoringapp.adapter.MatchAdapter;
import eu.ase.ro.livescoringapp.classes.Match;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<Match> matches = new ArrayList<>();

    public HomeFragment() {
        // Required empty public constructor
    }


    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = view.findViewById(R.id.recycle_view_homeFragment); // link with element in layout
        Thread jsonParser = new Thread(new JSONParser());
        jsonParser.start();

        return view;
    }

    public class JSONParser implements Runnable {

        @Override
        public void run() {
            try {
                URL url = new URL("https://www.jsonkeeper.com/b/LP9I");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = connection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                String line = bufferedReader.readLine();
                bufferedReader.close();
                inputStreamReader.close();
                inputStream.close();
                connection.disconnect();

                getMatchesFromJson(line);

                Handler threadHandler = new Handler(Looper.getMainLooper());
                threadHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        // UI logic
                        MatchAdapter matchAdapter = new MatchAdapter(getContext(),matches);
                        recyclerView.setAdapter(matchAdapter);
                        // set how the items will be displayed in recycler view
                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    }
                });

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    public void getMatchesFromJson(String json) {
        matches.clear();  // remove items before another fetch
        // TODO don't fetch if data is already present
        if(json != null){
            try {
                JSONObject jsonObject = new JSONObject(json);

                JSONArray jsonArray = jsonObject.getJSONArray("matches");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject matchJson = jsonArray.getJSONObject(i);
                    Integer id = matchJson.getInt("id");
                    String homeTeam = matchJson.getString("home");
                    String awayTeam = matchJson.getString("away");

                    Match newMatch = new Match(id,homeTeam,awayTeam);
                    matches.add(newMatch);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}