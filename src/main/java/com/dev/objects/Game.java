package com.dev.objects;

import javax.persistence.*;

@Entity
@Table(name = "my_games")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;

    @Column
    private int userId;


    @Column
    private boolean inLive;

    @Column
    private String homeTeam;

    @Column
    private String foreignTeam;


    @Column
    private int goalsToHome;

    @Column
    private int goalsToForeign;



    public Game() {
    }


    public Game(int userId, boolean inLive, String homeTeam, String foreignTeam, int goalsToHome, int goalsToForeign) {
        this.userId = userId;
        this.inLive = inLive;
        this.homeTeam = homeTeam;
        this.foreignTeam = foreignTeam;
        this.goalsToHome = goalsToHome;
        this.goalsToForeign = goalsToForeign;
    }

    public int getGoalsToHome() {
        return goalsToHome;
    }

    public void setGoalsToHome(int goalsToHome) {
        this.goalsToHome = goalsToHome;
    }

    public int getGoalsToForeign() {
        return goalsToForeign;
    }

    public void setGoalsToForeign(int goalsToForeign) {
        this.goalsToForeign = goalsToForeign;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isInLive() {
        return inLive;
    }

    public void setInLive(boolean inLive) {
        this.inLive = inLive;
    }


    public String getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }

    public String getForeignTeam() {
        return foreignTeam;
    }

    public void setForeignTeam(String foreignTeam) {
        this.foreignTeam = foreignTeam;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

}
