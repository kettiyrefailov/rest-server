package com.dev.persists;


import com.dev.objects.User;
import com.dev.utils.Utils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class UserPersist {

    private final SessionFactory sessionFactory;


    private Utils utils = new Utils();

    @Autowired
    public UserPersist (SessionFactory sf) {

        this.sessionFactory = sf;
        addDefaultUserIfNotExist();
    }


    public void saveUser (User user) {
        Session session = sessionFactory.openSession();
        session.save(user);
        session.close();
    }

    public int getUserIdByToken(String token) {
        int userId = -1;
        Session session = sessionFactory.openSession();
        String hql = "FROM User WHERE token = :token";
        Query query = session.createQuery(hql);
        query.setParameter("token", token);
        User user = (User) query.uniqueResult();
        if (user != null) {
            userId = user.getId();
        }

        session.close();

        return userId;
    }


    public User getUserByUsernameAndToken (String username, String token) {
        User found;
        Session session = sessionFactory.openSession();
        found = (User) session.createQuery("FROM User WHERE username = :username " +
                        "AND token = :token")
                .setParameter("username", username)
                .setParameter("token", token)
                .uniqueResult();
        session.close();
        return found;
    }

    private void addDefaultUserIfNotExist() {
        Session session = sessionFactory.openSession();
        List<User> allUsers = session.createCriteria(User.class).list();
        if (allUsers.size()==0) {
            String password = "1234";
            String username = "Admin";
            String checksum = utils.createHash(username, password);
            User defaultUser = new User(username, checksum);
            saveUser(defaultUser);
        }
        session.close();
    }
}
