package service;

import dao.userDao;
import entity.User;

public class UserService {
    public void register(User user) {
        userDao.register(user);
    }
}
