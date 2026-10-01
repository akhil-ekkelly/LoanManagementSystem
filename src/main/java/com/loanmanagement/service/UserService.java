package com.loanmanagement.service;

import com.loanmanagement.model.User;

public interface UserService {
    void addUser(User user);

    User getUserById(int userId);

    void updateUser(User user);

    void deleteUser(int userId);

    User getUserByUsername(String username);
}
