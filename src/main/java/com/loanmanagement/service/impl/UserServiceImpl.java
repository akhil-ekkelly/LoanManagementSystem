package com.loanmanagement.service.impl;

import com.loanmanagement.dao.UserDao;
import com.loanmanagement.dao.impl.UserDaoImpl;
import com.loanmanagement.model.User;
import com.loanmanagement.service.UserService;
import com.loanmanagement.util.PasswordUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserDao userDao;

    public UserServiceImpl() {
        this.userDao = new UserDaoImpl();
    }

    @Override
    public void addUser(User user) {
        if (user != null && user.getUsername() != null && user.getPassword() != null) {
            String hashedPassword = PasswordUtil.hashPassword(user.getPassword());
            user.setPassword(hashedPassword);
            userDao.addUser(user);
            logger.info("Service: Processed registration for username={}", user.getUsername());
        } else {
            logger.warn("Service Validation failed: Username and Password are required.");
        }
    }

    @Override
    public User getUserById(int userId) {
        if (userId > 0) {
            return userDao.getUserById(userId);
        }
        logger.warn("Service Validation failed: Invalid userId={}", userId);
        return null;
    }

    @Override
    public void updateUser(User user) {
        if (user != null && user.getUserId() > 0) {
            userDao.updateUser(user);
            logger.info("Service: Processed update for userId={}", user.getUserId());
        } else {
            logger.warn("Service Validation failed: Invalid user data provided for update.");
        }
    }

    @Override
    public void deleteUser(int userId) {
        if (userId > 0) {
            userDao.deleteUser(userId);
            logger.info("Service: Processed deletion for userId={}", userId);
        } else {
            logger.warn("Service Validation failed: Invalid userId={}", userId);
        }
    }
}