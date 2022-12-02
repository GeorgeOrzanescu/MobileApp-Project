package eu.ase.ro.livescoringapp.database;

import androidx.room.Dao;
import androidx.room.Insert;

import eu.ase.ro.livescoringapp.classes.FootballMatch;

// every class u use as entity/ table in db must have a DATA ACCESS OBJECT interface
@Dao
public interface FootballMatchDao {

    @Insert
    long insert(FootballMatch footballMatch);
}
