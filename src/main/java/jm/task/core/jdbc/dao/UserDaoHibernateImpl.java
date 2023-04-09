package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Query;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {
    }

    Transaction transaction;


    @Override
    public void createUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            String sql = "CREATE TABLE IF NOT EXISTS users " +
                    "(id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                    "name VARCHAR(50) NOT NULL, lastName VARCHAR(50) NOT NULL, " +
                    "age INT NOT NULL)";
            Query query = session.createSQLQuery(sql).addEntity(User.class);
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void dropUsersTable() {
//        try (Session session = Util.getSessionFactory().openSession()) {
//            transaction = session.beginTransaction();
//            String sql = "DROP TABLE IF EXISTS users";
//            Query query = session.createSQLQuery(sql).addEntity(User.class);
//            query.executeUpdate();
//            transaction.commit();
//        } catch (Exception e) {
//            if (transaction != null) {
//                transaction.rollback();
//            }
//        }
        try (Session session = Util.getSessionFactory().openSession();) {
            transaction = session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS users").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            User user = new User(name, lastName, age);
            session.save(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }

    }

    @Override
    public void removeUserById(long id) {
        Session session = Util.getSessionFactory().openSession();
        transaction = session.beginTransaction();
        String del = "DELETE FROM User WHERE id =: id";
        Query query = session.createQuery(del);
        query.setParameter("id", id);
        query.executeUpdate();
        session.close();
    }

    @Override
    public List<User> getAllUsers() {
//        Session session = Util.getSessionFactory().openSession();
//        transaction = session.beginTransaction();
//        String sql = "From " + User.class.getSimpleName();
//        List<User> users = session.createQuery(sql).list();
//        session.close();
//        return users;
        List<User> users = new ArrayList<>();
        try {
            Session session = Util.getSessionFactory().openSession();
            session.beginTransaction();
            users = session.createQuery("FROM User").list();
            session.close();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        Session session = Util.getSessionFactory().openSession();
        session.beginTransaction();
        session.createQuery("DELETE FROM User").executeUpdate();
        session.getTransaction().commit();
        session.close();
    }
}
