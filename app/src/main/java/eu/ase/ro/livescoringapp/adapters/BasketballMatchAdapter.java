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
import eu.ase.ro.livescoringapp.classes.BasketballMatch;

public class BasketballMatchAdapter extends RecyclerView.Adapter<BasketballMatchAdapter.MatchViewHolder>{
    private ArrayList<BasketballMatch> basketballMatches;
    Context context;

    public BasketballMatchAdapter(Context context,ArrayList<BasketballMatch> basketballMatches) {
        this.context = context;
        this.basketballMatches = basketballMatches;
    }

    @NonNull
    @Override
    public BasketballMatchAdapter.MatchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.football_row_design,parent,false); // link the layout

        // create row
        return new MatchViewHolder(view);
    }

    @Override // associate data from a position to a visual element of the holder
    public void onBindViewHolder(@NonNull BasketballMatchAdapter.MatchViewHolder holder, int position) {
        BasketballMatch basketballMatch = basketballMatches.get(position);
        holder.homeTeam.setText(basketballMatch.getHomeTeam());
        holder.awayTeam.setText(basketballMatch.getAwayTeam());
        holder.date.setText(basketballMatch.getDate());
        holder.favourite.setText(basketballMatch.getFavourite());
    }

    @Override
    public int getItemCount() { // how many elements need to be introduced in recycle view
        return basketballMatches.size();
    }

    // Need this class for the view holder
    public class MatchViewHolder extends RecyclerView.ViewHolder {
        // elements from our layout visual
        TextView homeTeam ,awayTeam,date,favourite ;
        public MatchViewHolder(@NonNull View itemView) {
            super(itemView);
            homeTeam = itemView.findViewById(R.id.homeTeam);
            awayTeam = itemView.findViewById(R.id.awayTeam);
            date = itemView.findViewById(R.id.match_date);
            favourite = itemView.findViewById(R.id.favourite);
        }
    }
}
