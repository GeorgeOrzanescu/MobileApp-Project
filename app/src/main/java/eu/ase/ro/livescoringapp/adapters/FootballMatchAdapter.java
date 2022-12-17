package eu.ase.ro.livescoringapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import eu.ase.ro.livescoringapp.R;
import eu.ase.ro.livescoringapp.classes.FootballMatch;

public class FootballMatchAdapter extends RecyclerView.Adapter<FootballMatchAdapter.MatchViewHolder> {

    private ArrayList<FootballMatch> footballMatchList;
    Context context;

    public FootballMatchAdapter(Context context,ArrayList<FootballMatch> footballMatchList) {
        this.context = context;
        this.footballMatchList = footballMatchList;
    }

    @NonNull
    @Override
    public MatchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // link with the layout
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.football_row_design,parent,false); // link the layout

        // create row
        return new MatchViewHolder(view);
    }

    @Override // associate data from a position to a visual element of the holder
    public void onBindViewHolder(@NonNull MatchViewHolder holder, int position) {
        FootballMatch footballMatch = footballMatchList.get(position);
        holder.homeTeam.setText(footballMatch.getHomeTeam());
        holder.awayTeam.setText(footballMatch.getAwayTeam());
        holder.date.setText(footballMatch.getDate());
        holder.favourite.setText(footballMatch.getFavourite());
    }

    @Override
    public int getItemCount() { // how many elements need to be introduced in recycle view
        return footballMatchList.size();
    }

    // Need this class for the view holder
    public class MatchViewHolder extends RecyclerView.ViewHolder {
        // elements from our layout visual
        TextView homeTeam ,awayTeam,date,favourite;
        public MatchViewHolder(@NonNull View itemView) { // itemView is the row in an recycle view
            super(itemView);
            homeTeam = itemView.findViewById(R.id.homeTeam);
            awayTeam = itemView.findViewById(R.id.awayTeam);
            date = itemView.findViewById(R.id.match_date);
            favourite = itemView.findViewById(R.id.favourite);
        }
    }
}
