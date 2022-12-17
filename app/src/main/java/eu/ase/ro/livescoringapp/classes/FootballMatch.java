package eu.ase.ro.livescoringapp.classes;

public class FootballMatch {

    private Integer id;
    private String homeTeam;
    private String awayTeam;
    private String date;
    private String favourite;

    private String favouriteAppend = "Favourite is : ";

    public FootballMatch(Integer id, String homeTeam, String awayTeam, String date, String favourite) {
        this.id = id;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.date = date;
        this.setFavourite(favourite);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFavourite() {
        return favourite;
    }

    public void setFavourite(String favourite) {
        this.favourite = this.favouriteAppend + favourite;
    }

    @Override
    public String toString() {
        return "FootballMatch{" +
                "id=" + id +
                ", homeTeam='" + homeTeam + '\'' +
                ", awayTeam='" + awayTeam + '\'' +
                ", date='" + date + '\'' +
                ", favourite='" + favourite + '\'' +
                '}';
    }
}
