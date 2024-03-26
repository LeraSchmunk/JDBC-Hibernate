package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;

import static jm.task.core.jdbc.util.Util.connection;

public class Main {
    public static void main(String[] args) {
//        Util util = new Util();
//        System.out.println("Подключена база " + util.open());

        UserService users = new UserServiceImpl();
        users.createUsersTable();
        System.out.println("-------------------");

        users.saveUser("Alex", "Ivanov", (byte) 20);
        users.saveUser("Ivan", "Petrov", (byte) 8);
        users.saveUser("Petr", "Konstantinov", (byte) 90);
        users.saveUser("Konstantin", "Alexandrov", (byte) 54);
        System.out.println("-------------------");
        users.getAllUsers();
        System.out.println("-------------------");
        users.removeUserById(3);
        System.out.println("-------------------");
        users.getAllUsers();
//        System.out.println("-------------------");
//        users.cleanUsersTable();
//        System.out.println("-------------------");
//        users.dropUsersTable();

    }
}
