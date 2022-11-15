package eu.ase.ro.livescoringapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import eu.ase.ro.livescoringapp.R;

public class UpcomingFragment extends Fragment {

    public UpcomingFragment() {
        // Required empty public constructor
    }

    public static UpcomingFragment newInstance() {
        UpcomingFragment fragment = new UpcomingFragment();
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
        return inflater.inflate(R.layout.fragment_upcoming, container, false);
    }
}