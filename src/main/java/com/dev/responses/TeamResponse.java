package com.dev.responses;

import com.dev.objects.Team;

import java.util.List;

public class TeamResponse extends BasicResponse {
    private List<Team> teams;

    public TeamResponse(boolean success, Integer errorCode, List<Team> teams) {
        super(success, errorCode);
        this.teams = teams;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }
}
