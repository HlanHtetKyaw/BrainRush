package com.union.brainrush.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.union.brainrush.model.User;
import com.union.brainrush.routing.SceneManager;
import com.union.brainrush.service.UserService;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
@Component
public class Test{
	private Scene scene;
	
	UserService userService;
	@Autowired
	public Test(UserService userService,SceneManager sceneManager){
		// Get the Spring-managed UserService

        // UI Elements
        ListView<User> userListView = new ListView<>();
        userListView.getItems().addAll(userService.getUsers());

        TextField nameField = new TextField();
        nameField.setPromptText("Name");

        TextField emailField = new TextField();
        emailField.setPromptText("Email");

        Button addButton = new Button("Add User");
        addButton.setOnAction(event -> {
//            String name = nameField.getText();
//            String email = emailField.getText();
//            if (!name.isEmpty() && !email.isEmpty()) {
//                User newUser = new User();
//                newUser.setName(name);
//                newUser.setEmail(email);
//                userService.addUser(newUser);
//                userListView.getItems().add(newUser);
//
//                nameField.clear();
//                emailField.clear();
//            }
        	sceneManager.switchToFirst();
        });

        // Layout
        VBox layout = new VBox(10, userListView, nameField, emailField, addButton);
        scene = new Scene(layout, 400, 400);
	}
	public Scene getScene() {
		return scene;
	}
}
