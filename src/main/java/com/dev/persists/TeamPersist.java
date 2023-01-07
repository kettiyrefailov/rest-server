package com.dev.persists;

import com.dev.objects.Team;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class TeamPersist {
    private final SessionFactory sessionFactory;

    @Autowired
    public TeamPersist (SessionFactory sf) {

        this.sessionFactory = sf;
        addDefaultTeamsIfNotExist();
    }

    private void addDefaultTeamsIfNotExist() {
        Session session = sessionFactory.openSession();

        List<Team> allTeams = session.createCriteria(Team.class).list();
        if (allTeams.size()==0) {
            String[] defaultTeamNames = {
                    "The Justice League",
                    "Lightning Legends",
                    "Mister Maniacs",
                    "Born to Win",
                    "Ninja Bros",
                    "The Elite Team",
                    "Dominatrix",
                    "Big Shots",
                    "Unstoppable Force",
                    "Crew X",
                    "Rule Breakers",
                    "The Squad",
                    "United Army"
            };
            for (String teamName : defaultTeamNames) {
                Team team = new Team(teamName);
                session.save(team);
            }
        }
        session.close();
    }

    public List<Team> getAllTeams() {
        Session session = sessionFactory.openSession();
        List<Team> allTeams = session.createCriteria(Team.class).list();
        session.close();

        return allTeams;
    }
}
