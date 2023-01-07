package com.dev.responses;

import com.dev.objects.Game;

import java.util.List;

public class GameResponse extends BasicResponse {

    List<Game> allGames;

    public GameResponse(boolean success, Integer errorCode, List<Game> allGames) {
        super(success, errorCode);
        this.allGames = allGames;
    }

    public List<Game> getAllGames() {
        return allGames;
    }

    public void setAllGames(List<Game> allGames) {
        this.allGames = allGames;
    }
}
