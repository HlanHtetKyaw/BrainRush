package com.union.brainrush.service;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.union.brainrush.model.User;
@Component
public class UserService {
    private final List<User> users = new ArrayList<>();

    public UserService() {
        loadUsers();
    }

    // Load users from the JSON file
    private void loadUsers() {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("data/data.json")) {
            ObjectMapper mapper = new ObjectMapper();
            List<User> loadedUsers = mapper.readValue(inputStream, new TypeReference<List<User>>() {});
            users.addAll(loadedUsers);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Get all users
    public List<User> getUsers() {
        return users;
    }

    // Add a new user
    public void addUser(User user) {
        users.add(user);
    }
}
