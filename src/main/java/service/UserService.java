package service;

import dao.UserDao;
import entity.User;

import java.time.LocalDateTime;

public class UserService {
    public void register(User user) {
        UserDao.register(user);
    }

    public User findUserByUsername(String username) {
        return UserDao.findUserByUsername(username);
    }

    public void updateLoginDate(String username, LocalDateTime now) {
        UserDao.updateLoginDate(username, now);
    }

    public boolean updateUser(User loginUser) {
        return UserDao.updateUser(loginUser);
    }
}
