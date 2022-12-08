package eu.ase.ro.livescoringapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import eu.ase.ro.livescoringapp.R;
import eu.ase.ro.livescoringapp.async.AsyncTaskRunner;

public class SportResultsFragment extends Fragment {
    // No free API with multiple sports found
    private static final String FOOTBALL_RESULTS_URL =
            "https://app.sportdataapi.com/api/v1/soccer/matches?apikey=578921b0-7718-11ed-b28a-a57e51ef3f16&season_id=3194&date_from=2022-11-26";

    RecyclerView recyclerView;
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
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_sport_results, container, false);

        recyclerView = view.findViewById(R.id.recycle_view_SportsResults);

        return view;
    }
}