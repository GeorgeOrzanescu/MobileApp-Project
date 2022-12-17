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
import eu.ase.ro.livescoringapp.adapters.BasketballMatchAdapter;
import eu.ase.ro.livescoringapp.adapters.FootballMatchAdapter;
import eu.ase.ro.livescoringapp.async.AsyncTaskRunner;
import eu.ase.ro.livescoringapp.async.CallbackFunction;
import eu.ase.ro.livescoringapp.classes.BasketballMatch;
import eu.ase.ro.livescoringapp.classes.FootballMatch;
import eu.ase.ro.livescoringapp.network.HttpManager;

public class SportFixturesFragment extends Fragment {
    private static final String SPORTS_URL = "https://api.npoint.io/e8ee44e671a077b556ce";
    private static final String FOOTBALL_URL_KEY = "football";
    private static final String BASKETBALL_URL_KEY = "basketball";


    RecyclerView recyclerView;
    ArrayList<FootballMatch> footballMatches = new ArrayList<>();
    ArrayList<BasketballMatch> basketballMatches = new ArrayList<>();
    private final AsyncTaskRunner asyncTaskRunner = new AsyncTaskRunner();
    private Spinner sportSelectSpinner;

    public SportFixturesFragment() {
        // Required empty public constructor
    }

    public static SportFixturesFragment newInstance() {
        SportFixturesFragment fragment = new SportFixturesFragment();
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
        View view =  inflater.inflate(R.layout.fragment_sports, container, false);

        recyclerView = view.findViewById(R.id.recycle_view_SportsFragment); // link with element in layout
        recyclerView.setAdapter(new FootballMatchAdapter(getContext(), footballMatches));
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);

        sportSelectSpinner = view.findViewById(R.id.spinner_select_sport);
        sportSelectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i == 0) {// football
                    getSportEventsFromNetwork(FOOTBALL_URL_KEY);
                }
                if(i == 1){
                    getSportEventsFromNetwork(BASKETBALL_URL_KEY);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        return view;
    }


    private void getSportEventsFromNetwork(String sportKey) {
        Callable<String> asyncTask = new HttpManager(SPORTS_URL);
        CallbackFunction<String> mainThreadTask = mainThreadTaskHttpJson(sportKey);

        asyncTaskRunner.executeAsync(asyncTask,mainThreadTask);

    }

    private CallbackFunction<String> mainThreadTaskHttpJson(String sportKey) {
        return new CallbackFunction<String>() {
            @Override
            public void runResultOnUiThread(String result) {
                getMatchesFromJson(result,sportKey);
                if(sportKey == FOOTBALL_URL_KEY){
                    FootballMatchAdapter footballMatchAdapter = new FootballMatchAdapter(getContext(), footballMatches);

                    recyclerView.setAdapter(footballMatchAdapter);
                    // set how the items will be displayed in recycler view
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    Log.i("Data", footballMatches.toString());
                }
                if(sportKey == BASKETBALL_URL_KEY){
                    BasketballMatchAdapter basketballMatchAdapter = new BasketballMatchAdapter(getContext(), basketballMatches);

                    recyclerView.setAdapter(basketballMatchAdapter);
                    // set how the items will be displayed in recycler view
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    Log.i("Data", basketballMatches.toString());
                }

            }
        };
    }


    public void getMatchesFromJson(String json,String sportKey) {
        footballMatches.clear();  // remove items before another fetch
        basketballMatches.clear();  // remove items before another fetch
        // TODO don't fetch if data is already present
        if(json != null){
            try {
                JSONObject jsonObject = new JSONObject(json);

                JSONArray jsonArray = jsonObject.getJSONArray(sportKey);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject matchJson = jsonArray.getJSONObject(i);
                    Integer id = matchJson.getInt("id");
                    String homeTeam = matchJson.getString("home");
                    String awayTeam = matchJson.getString("away");
                    JSONObject metaObject = matchJson.getJSONObject("metadata");
                    String date = metaObject.getString("date");
                    String favourite = metaObject.getString("favourite");
                    if(sportKey == FOOTBALL_URL_KEY){
                        FootballMatch newMatch = new FootballMatch(id,homeTeam,awayTeam,date,favourite);
                        footballMatches.add(newMatch);
                    }
                    if(sportKey == BASKETBALL_URL_KEY){
                        BasketballMatch newMatch = new BasketballMatch(id,homeTeam,awayTeam,date,favourite);
                        basketballMatches.add(newMatch);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

//   NOT Needed for now as we just get data from an API
//    // this is being called to notify the adapter when changes occur
//    public void notifyAdapter() {
//        //!! because CommendAdapter inherits from ArrayAdapter there is no problem using it here
//        RecyclerView.Adapter adapter = recyclerView.getAdapter();
//        adapter.notifyDataSetChanged();
//    }
}