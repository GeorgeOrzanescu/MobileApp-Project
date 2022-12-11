package eu.ase.ro.livescoringapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import eu.ase.ro.livescoringapp.R;
import eu.ase.ro.livescoringapp.adapters.FootballResultAdapter;
import eu.ase.ro.livescoringapp.async.AsyncTaskRunner;
import eu.ase.ro.livescoringapp.async.CallbackFunction;
import eu.ase.ro.livescoringapp.classes.FootballResult;
import eu.ase.ro.livescoringapp.network.HttpManager;

public class SportResultsFragment extends Fragment {
    // No free API with multiple sports found
    private static final String FOOTBALL_BUNDESLIGA_RESULTS_URL =
            "https://app.sportdataapi.com/api/v1/soccer/matches?apikey=578921b0-7718-11ed-b28a-a57e51ef3f16&season_id=3194&date_from=2022-12-01";
 private static final String FOOTBALL_PREMIER_LEAGUE_RESULTS_URL =
            "https://app.sportdataapi.com/api/v1/soccer/matches?apikey=578921b0-7718-11ed-b28a-a57e51ef3f16&season_id=3161&date_from=2022-12-01";

    RecyclerView recyclerView;
    ArrayList<FootballResult> footballResults = new ArrayList<>();

    private final AsyncTaskRunner asyncTaskRunner = new AsyncTaskRunner();
    private Spinner footballLeagueSelectSpinner;


    public SportResultsFragment() {
        // Required empty public constructor
    }


    public static SportResultsFragment newInstance() {
        SportResultsFragment fragment = new SportResultsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
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
        View view =  inflater.inflate(R.layout.fragment_sport_results, container, false);

        recyclerView = view.findViewById(R.id.recycle_view_SportsResults);
        recyclerView.setAdapter(new FootballResultAdapter(footballResults, getContext()));
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);


        footballLeagueSelectSpinner = view.findViewById(R.id.spinner_select_sport_results);
        footballLeagueSelectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i == 0) // bundesliga
                {
                    getFootballResultsFromNetwork(FOOTBALL_BUNDESLIGA_RESULTS_URL);
                }
                if(i == 1) // premier league
                {
                    getFootballResultsFromNetwork(FOOTBALL_PREMIER_LEAGUE_RESULTS_URL);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return view;
    }

    private void getFootballResultsFromNetwork(String url) {
        Callable<String> asyncTask = new HttpManager(url);
        CallbackFunction<String> mainThreadTask = mainThreadTaskHttpJson();

        asyncTaskRunner.executeAsync(asyncTask,mainThreadTask);

    }

    private CallbackFunction<String> mainThreadTaskHttpJson() {
        return new CallbackFunction<String>() {
            @Override
            public void runResultOnUiThread(String result) {
                getFootballResultsFromJson(result);

                FootballResultAdapter footballResultAdapter = new FootballResultAdapter(footballResults, getContext());
                recyclerView.setAdapter(footballResultAdapter);
                // set how the items will be displayed in recycler view
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            }
        };
    }

    public void getFootballResultsFromJson(String json){
        footballResults.clear();
        if(json != null) {
            try {
                JSONObject jsonObject = new JSONObject(json);
                JSONArray jsonArray = jsonObject.getJSONArray("data");

                // we only get 10 results for now
                for (int i = 0; i < 20; i++) {
                    JSONObject currentJsonObject= jsonArray.getJSONObject(i);

                    // get home team
                    JSONObject homeTeamObject = currentJsonObject.getJSONObject("home_team");
                    String homeTeam = homeTeamObject.getString("name");

                    // get away team
                    JSONObject awayTeamObject = currentJsonObject.getJSONObject("away_team");
                    String awayTeam = awayTeamObject.getString("name");

                    // get home and away score
                    JSONObject scores = currentJsonObject.getJSONObject("stats");
                    Integer homeScore = scores.getInt("home_score");
                    Integer awayScore = scores.getInt("away_score");

                    FootballResult newFootballResult = new FootballResult(homeTeam,awayTeam,homeScore,awayScore);
                    Log.i("API result", newFootballResult.toString() );
                    footballResults.add(newFootballResult);
                }

            }catch(JSONException e) {
                e.printStackTrace();
            }
        }
    }
}