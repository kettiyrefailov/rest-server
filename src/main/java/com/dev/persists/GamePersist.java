package com.dev.persists;

import com.dev.objects.Game;
import com.dev.objects.Team;
import com.dev.objects.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GamePersist {
    private final SessionFactory sessionFactory;

    @Autowired
    public GamePersist(SessionFactory sf) {

        this.sessionFactory = sf;
    }


    public void updateGame(Game game, int id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Game gameToUpdate = session.get(Game.class, id);

        gameToUpdate.setForeignTeam(game.getForeignTeam());
        gameToUpdate.setHomeTeam(game.getHomeTeam());
        gameToUpdate.setGoalsToForeign(game.getGoalsToForeign());
        gameToUpdate.setGoalsToHome(game.getGoalsToHome());
        gameToUpdate.setInLive(game.isInLive());

        session.saveOrUpdate(gameToUpdate);

        transaction.commit();
        session.close();

    }


    public void saveGame(Game game) {
        Session session = sessionFactory.openSession();
        session.save(game);
        session.close();
    }

    public List<Game> getAllGames() {
        Session session = sessionFactory.openSession();
        List<Game> allGames =  session.createCriteria(Game.class).list();
        session.close();

        return allGames;
    }

}