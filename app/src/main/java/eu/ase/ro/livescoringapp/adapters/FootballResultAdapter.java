package eu.ase.ro.livescoringapp.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import eu.ase.ro.livescoringapp.R;
import eu.ase.ro.livescoringapp.classes.FootballResult;

public class FootballResultAdapter extends RecyclerView.Adapter<FootballResultAdapter.FootballResultViewHolder> {

    private ArrayList<FootballResult> footballResults;
    Context context;

    public FootballResultAdapter(ArrayList<FootballResult> footballResults, Context context) {
        this.footballResults = footballResults;
        this.context = context;
    }

    @NonNull
    @Override
    public FootballResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_football_result_row_design,parent,false); // link the layout

        // create row
        return new FootballResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FootballResultViewHolder holder, int position) {
        FootballResult footballResult = footballResults.get(position);
        holder.homeTeam.setText(footballResult.getHomeTeam());
        holder.awayTeam.setText(footballResult.getAwayTeam());
        holder.homeScore.setText( String.valueOf(footballResult.getHomeScore()));
        holder.awayScore.setText(String.valueOf(footballResult.getAwayScore()));
    }

    @Override
    public int getItemCount() {
        return footballResults.size();
    }

    // Need this class for the view holder
    public class FootballResultViewHolder extends RecyclerView.ViewHolder {
        // elements from our layout visual
        TextView homeTeam, awayTeam, homeScore, awayScore;

        public FootballResultViewHolder(@NonNull View itemView) {
            super(itemView);
            homeTeam = itemView.findViewById(R.id.result_homeTeam);
            awayTeam = itemView.findViewById(R.id.result_awayTeam);
            homeScore = itemView.findViewById(R.id.result_home_score);
            awayScore = itemView.findViewById(R.id.result_away_score);
        }
    }
}
