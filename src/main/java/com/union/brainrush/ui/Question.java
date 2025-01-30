package com.union.brainrush.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;

@Component
@Lazy
public class Question {
	Scene scene;
	StackPane root;
	@Autowired
	public Question() {
		root = new StackPane();
		scene = new Scene(root);
	}
	public Scene getScene() {
		return scene;
	}
}
