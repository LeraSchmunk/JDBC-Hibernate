package jm.task.core.jdbc.service;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;

import java.util.List;

public class UserServiceImpl implements UserService {

    private final UserDao user = new UserDaoJDBCImpl();
    private final UserDao user1 = new UserDaoHibernateImpl();

    public void createUsersTable() {
        user.createUsersTable();
        user1.createUsersTable();
    }

    public void dropUsersTable() {
        user.dropUsersTable();
        user1.dropUsersTable();
    }

    public void saveUser(String name, String lastName, byte age) {
        user.saveUser(name, lastName, age);
        user1.saveUser(name, lastName, age);
    }

    public void removeUserById(long id) {
        user.removeUserById(id);
        user1.removeUserById(id);
    }

    public List<User> getAllUsers() {
        List<User> allUsersList = user.getAllUsers();
        allUsersList.forEach(System.out::println);
        return allUsersList;
    }

    public void cleanUsersTable() {
        user.cleanUsersTable();
        user1.cleanUsersTable();
    }
}
