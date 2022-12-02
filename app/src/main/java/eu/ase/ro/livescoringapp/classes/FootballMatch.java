package eu.ase.ro.livescoringapp.classes;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "footballMatches")   // this let's ROOM now about this class to create a table for it
public class FootballMatch {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id;
    @ColumnInfo(name = "homeTeam")
    private String homeTeam;
    @ColumnInfo(name = "awayTeam")
    private String awayTeam;

    // IMPORTANT: if u have multiple constructors, the one u don't want to be used by ROOM
    // must use @Ignore on it

    // ROOM needs a constructor with all parameters
    public FootballMatch(Integer id, String homeTeam, String awayTeam) {
        this.id = id;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(String awayTeam) {
        this.awayTeam = awayTeam;
    }

    @Override
    public String toString() {
        return "Match: " + id + " " + getHomeTeam() + " : " + getAwayTeam();
    }
}
