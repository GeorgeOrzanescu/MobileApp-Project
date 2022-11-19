package eu.ase.ro.livescoringapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import eu.ase.ro.livescoringapp.R;
import eu.ase.ro.livescoringapp.adapters.FootballMatchAdapter;
import eu.ase.ro.livescoringapp.async.AsyncTaskRunner;
import eu.ase.ro.livescoringapp.async.CallbackFunction;
import eu.ase.ro.livescoringapp.classes.FootballMatch;
import eu.ase.ro.livescoringapp.network.HttpManager;

public class SportsFragment extends Fragment {
    private static final String SPORTS_URL = "https://www.jsonkeeper.com/b/LP9I";

    RecyclerView recyclerView;
    ArrayList<FootballMatch> matches = new ArrayList<>();
    private final AsyncTaskRunner asyncTaskRunner = new AsyncTaskRunner();

    public SportsFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static SportsFragment newInstance() {
        SportsFragment fragment = new SportsFragment();
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
        recyclerView.setAdapter(new FootballMatchAdapter(getContext(),matches));
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        // we need an ArrayAdapter or a CustomAdapter for the Recycle View
        getSportEventsFromNetwork();

        return view;
    }


    private void getSportEventsFromNetwork() {
        Callable<String> asyncTask = new HttpManager(SPORTS_URL);
        CallbackFunction<String> mainThreadTask = mainThreadTaskHttpJson();

        asyncTaskRunner.executeAsync(asyncTask,mainThreadTask);

    }

    private CallbackFunction<String> mainThreadTaskHttpJson() {
        return new CallbackFunction<String>() {
            @Override
            public void runResultOnUiThread(String result) {
                getMatchesFromJson(result);
                FootballMatchAdapter footballMatchAdapter =  new FootballMatchAdapter(getContext(),matches);

                recyclerView.setAdapter(footballMatchAdapter);
                // set how the items will be displayed in recycler view
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                Log.i("Data",matches.toString());
            }
        };
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

                    FootballMatch newMatch = new FootballMatch(id,homeTeam,awayTeam);
                    matches.add(newMatch);
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