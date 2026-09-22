package com.loanmanagement.service.impl;

import com.loanmanagement.dao.UserDao;
import com.loanmanagement.dao.impl.UserDaoImpl;
import com.loanmanagement.model.User;
import com.loanmanagement.service.UserService;
import com.loanmanagement.util.PasswordUtil;

public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    public UserServiceImpl() {
        this.userDao = new UserDaoImpl();
    }

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void addUser(User user) {
        if (user != null && user.getUsername() != null && user.getPassword() != null) {
            // Hash the password before saving to the database
            String hashedPassword = PasswordUtil.hashPassword(user.getPassword());
            user.setPassword(hashedPassword);

            userDao.addUser(user);
        } else {
            System.out.println("Validation failed: Username and Password are required.");
        }
    }

    @Override
    public User getUserById(int userId) {
        if (userId > 0) {
            return userDao.getUserById(userId);
        }
        return null;
    }

    @Override
    public void updateUser(User user) {
        if (user != null && user.getUserId() > 0) {
            // If the user object contains a raw password that needs updating, hash it.
            // (In a real app, you'd check if the password was actually changed before rehashing).
            userDao.updateUser(user);
        }
    }

    @Override
    public void deleteUser(int userId) {
        if (userId > 0) {
            userDao.deleteUser(userId);
        }
    }
}