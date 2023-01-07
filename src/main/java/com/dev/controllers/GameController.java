package com.dev.controllers;

import com.dev.objects.Game;
import com.dev.objects.Team;
import com.dev.persists.GamePersist;
import com.dev.persists.TeamPersist;
import com.dev.persists.UserPersist;
import com.dev.responses.GameResponse;
import com.dev.responses.TeamResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.dev.responses.BasicResponse;

import java.util.List;

import static com.dev.utils.Errors.*;

@RestController
public class GameController {

    @Autowired
    private GamePersist gamePersist;

    @Autowired
    private UserPersist userPersist;
    @Autowired
    private TeamPersist teamPersist;


    @RequestMapping(value = "get-all-games", method = RequestMethod.GET)
    public GameResponse getAllGames() {
        List<Game> allGames = gamePersist.getAllGames();

        return new GameResponse(true, null, allGames);
    }

    @RequestMapping(value = "get-teams", method = RequestMethod.GET)
    public BasicResponse getTeams() {
        List<Team> allTeams = teamPersist.getAllTeams();
        BasicResponse teamResponse = new TeamResponse(true, null, allTeams);

        return teamResponse;
    }

    //To Do
     @RequestMapping(value = "update-game", method = RequestMethod.POST)
     public GameResponse updateGame(Game game, String token) {
        int succsess = 0;
        int existingGameId = -1;
        int userId = -1;
        
        if (token != null) {
            int id = userPersist.getUserIdByToken(token);
            if (id >= 0) {
                 userId = id;
            }
        }
        if (userId < 0) {
            return new GameResponse(false, 2, null);
        }
        else {
            game.setUserId(userId);
        }



        List<Game> allGames = gamePersist.getAllGames();

         for (Game existingGame : allGames) {
                if (existingGame.isInLive()) {
                    if (existingGame.getId() == game.getId()) {

                        existingGameId = game.getId();
                        break;
                    }
                }
         }

         if (existingGameId < 0) {
             return new GameResponse(false, 7, gamePersist.getAllGames());
         }

         for (Game existingGame : allGames) {
             if (existingGame.isInLive()) {
                 if (existingGameId != existingGame.getId()) {
                     if (
                               game.getHomeTeam().equals(existingGame.getHomeTeam())        ||
                               game.getHomeTeam().equals(existingGame.getForeignTeam())     ||
                               game.getForeignTeam().equals(existingGame.getForeignTeam())  ||
                               game.getForeignTeam().equals(existingGame.getHomeTeam())   

                     ) {
                         succsess = 6;
                         break;

                     }
                 }
             }
         }

         if (succsess==0) {
             gamePersist.updateGame(game, existingGameId);
         }

         return new GameResponse(succsess==0, succsess, gamePersist.getAllGames());
     }

    @RequestMapping(value = "add-game", method = RequestMethod.POST)
    public BasicResponse addGame(Game game, String token) {
        int errorCode = 0;
        int userId = -1;
        if (token != null) {
            int id = userPersist.getUserIdByToken(token);
            if (id >= 0) {
                userId = id;
            }
        }
        if (userId < 0) {
            return new BasicResponse(false, 2);
        }
        else {
            game.setUserId(userId);
        }

        List<Game> allGames = gamePersist.getAllGames();

        for (Game existingGame: allGames) {
            if (existingGame.isInLive() && (
                    existingGame.getForeignTeam().equals(game.getForeignTeam()) ||
                            existingGame.getForeignTeam().equals(game.getHomeTeam()) ||
                            existingGame.getHomeTeam().equals(game.getForeignTeam()) ||
                            existingGame.getHomeTeam().equals(game.getHomeTeam())
            )) {
                errorCode=1;
                break;
            }
        }
        if (errorCode==0) {
            gamePersist.saveGame(game);
        }

        return new BasicResponse(errorCode == 0, errorCode);
    }

}
