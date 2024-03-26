package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
//    private final Connection connection = Util.open();


    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        String SQL = "CREATE TABLE IF NOT EXISTS users (" +
                "id BIGSERIAL PRIMARY KEY NOT NULL, " +
                  "name VARCHAR(128), " +
                "lastname VARCHAR(128), " +
                "age INT);";
        try (Connection connection = Util.open();
             Statement statement = connection.createStatement()){
                statement.execute(SQL);
                System.out.println("Таблица создана (JDBC)");
        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
    }

    public void dropUsersTable() {
        String SQL = "DROP TABLE users";
        try (Connection connection = Util.open();
             Statement statement = connection.createStatement()){
            statement.execute(SQL);
            System.out.println("Таблица удалена (JDBC)");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO users (name, lastname, age) VALUES (?, ?, ?)";

        try(Connection connection = Util.open();
            PreparedStatement saveUsers = connection.prepareStatement(sql)) {

            saveUsers.setString(1, name);
            saveUsers.setString(2,lastName);
            saveUsers.setByte(3, age);

            saveUsers.executeUpdate();
            System.out.println("Пользователь " + name + " занесен (JDBC)");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        String sql = "DELETE FROM users WHERE id = ?";
        try(Connection connection = Util.open();
            PreparedStatement removeUser = connection.prepareStatement(sql)) {
            removeUser.setLong(1, id);
            removeUser.executeUpdate();
            System.out.println("Удален пользователь c id (JDBC) " + id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> allUsersList = new ArrayList<>();
        String SQL = "SELECT * FROM users";
                try(Connection connection = Util.open();
                    Statement statement = connection.createStatement()){
                    ResultSet resultSet = statement.executeQuery(SQL);
                    System.out.println("Лист всех пользователей (JDBC)");
                    while (resultSet.next()) {
                        User user = new User();
                        user.setId(resultSet.getLong("id"));
                        user.setName(resultSet.getString("name"));
                        user.setLastName(resultSet.getString("lastname"));
                        user.setAge(resultSet.getByte("age"));
                        allUsersList.add(user);
                    }
                } catch (SQLException e) {
                   throw new RuntimeException(e);
                }
        return allUsersList;
    }

    public void cleanUsersTable() {
        String SQL = "TRUNCATE TABLE users";
        try (Connection connection = Util.open();
             Statement statement = connection.createStatement()){
            statement.execute(SQL);
            System.out.println("Таблица очищена (JDBC)");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
