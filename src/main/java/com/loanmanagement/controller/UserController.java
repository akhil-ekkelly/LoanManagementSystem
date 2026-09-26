package com.loanmanagement.controller;

import com.loanmanagement.model.User;
import com.loanmanagement.service.AuthService;
import com.loanmanagement.service.UserService;
import com.loanmanagement.service.impl.AuthServiceImpl;
import com.loanmanagement.service.impl.UserServiceImpl;

import java.util.Scanner;

public class UserController {

    private final UserService userService;
    private final AuthService authService;

    public UserController() {
        this.userService = new UserServiceImpl();
        this.authService = new AuthServiceImpl();
    }

    public void registerUser(Scanner scanner) {
        System.out.println("\n--- Register New User ---");
        System.out.print("Enter Username: ");
        String username = scanner.nextLine();

        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        System.out.print("Enter Role (CUSTOMER/ADMIN/LOAN_OFFICER): ");
        String role = scanner.nextLine().toUpperCase();

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(password);
        newUser.setRole(role);

        userService.addUser(newUser);
    }

    public boolean loginUser(Scanner scanner) {
        System.out.println("\n--- Login ---");
        System.out.print("Enter Username: ");
        String username = scanner.nextLine();

        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        return authService.login(username, password);
    }
}