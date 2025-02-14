package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    Configuration configuration = new Configuration().addAnnotatedClass(User.class);
    SessionFactory sessionFactory = configuration.buildSessionFactory();
    Session session = sessionFactory.getCurrentSession();
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {

        try (Session session = sessionFactory.openSession();){
            session.beginTransaction();
            String sql = "CREATE TABLE IF NOT EXISTS usersHib (" +
                    "id BIGINT PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY, " +
                    "name VARCHAR(128), " +
                    "lastname VARCHAR(128), " +
                    "age INT);";
            session.createSQLQuery(sql)
                    .addEntity(User.class)
                    .executeUpdate();
            session.getTransaction().commit();
            System.out.println("Таблица создана");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void dropUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            String sql = "DROP TABLE IF EXISTS usershib";
            Query query = session.createSQLQuery(sql)
                    .addEntity(User.class);
            query.executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try ( Session session = sessionFactory.openSession();){
            session.beginTransaction();
            User user = new User(name, lastName, age);
            session.save(user);
            session.getTransaction().commit();
            System.out.println("Добавлен пользователь " + user.getName() + " " + user.getLastName());
        }catch (HibernateException e) {
            throw new RuntimeException(e);
    }
    }

    @Override
    public void removeUserById(long id) {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            session.getTransaction().commit();
            System.out.println("Удаление пользователя с id " + id);
        }catch (HibernateException e) {
            throw new RuntimeException(e);
    }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = sessionFactory.openSession()) {
            System.out.println("Все пользователи: ");
            return session.createQuery("FROM User", User.class).list();
        }catch (HibernateException e) {
            throw new RuntimeException(e);
    }}

    @Override
    public void cleanUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            String sql = "TRUNCATE TABLE usershib";
            Query query = session.createSQLQuery(sql)
                    .addEntity(User.class);
            query.executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            throw new RuntimeException(e);
        }
    }
}



