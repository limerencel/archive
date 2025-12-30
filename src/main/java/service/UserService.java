package service;

import dao.userDao;
import entity.User;

import java.time.LocalDateTime;

public class UserService {
    public void register(User user) {
        userDao.register(user);
    }

    public User findUserByUsername(String username) {
        return userDao.findUserByUsername(username);
    }

    public void updateLoginDate(String username, LocalDateTime now) {
        userDao.updateLoginDate(username, now);
    }
}
