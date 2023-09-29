package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Vitya", "Smirnov", (byte) 21);
        userService.saveUser("Petya", "Ivanov", (byte) 21);
        userService.saveUser("Kolya", "Petrov", (byte) 21);
        userService.cleanUsersTable();
        userService.saveUser("Roma", "Sidorov", (byte) 21);
        userService.saveUser("Mao", "Zedong", (byte) 21);
        userService.removeUserById(4);
        System.out.println(userService.getAllUsers());
    }

    static List<? super Integer> numbers(){
        User user = new User();
        return List.of(user);
    }

}
