package eu.ase.ro.livescoringapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import eu.ase.ro.livescoringapp.R;
import eu.ase.ro.livescoringapp.classes.Match;

public class MatchAdapter extends RecyclerView.Adapter<MatchAdapter.MatchViewHolder> {
    // elements from our layout visual
     //TextView homeTeam ,awayTeam ;

    private ArrayList<Match> matchList;
    Context context;

    public MatchAdapter(Context context,ArrayList<Match> matchList) {
        this.context = context;
        this.matchList = matchList;
    }


    @NonNull
    @Override
    public MatchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // link with the layout
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.match_row_design,parent,false); // link the layout

        // create row
        return new MatchViewHolder(view);
    }

    @Override // associate data from a position to a visual element of the holder
    public void onBindViewHolder(@NonNull MatchViewHolder holder, int position) {
        Match match = matchList.get(position);
        holder.homeTeam.setText(match.getHomeTeam());
        holder.awayTeam.setText(match.getAwayTeam());
    }

    @Override
    public int getItemCount() { // how many elements need to be introduced in recycle view
        return matchList.size();
    }

    // Need this class for the view holder
    public class MatchViewHolder extends RecyclerView.ViewHolder {
        // elements from our layout visual
        TextView homeTeam ,awayTeam ;
        public MatchViewHolder(@NonNull View itemView) { // itemView is the row in an recycle view
            super(itemView);
            homeTeam = itemView.findViewById(R.id.homeTeam);
            awayTeam = itemView.findViewById(R.id.awayTeam);
        }
    }
}
