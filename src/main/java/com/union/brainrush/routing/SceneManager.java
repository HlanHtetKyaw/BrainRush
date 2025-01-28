package com.union.brainrush.routing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.union.brainrush.ui.First;
import com.union.brainrush.ui.Home;
import com.union.brainrush.ui.Test;

import javafx.stage.Stage;

@Component
public class SceneManager {
	private static Stage stage;
	private Home home;
	private First first;
	@Autowired
	SceneManager(Home home,First first) {
		this.home = home;
		this.first = first;
	}

	public static void initialize(Stage primaryStage) {
		stage = primaryStage;
	}

	public void switchToHome() {
		stage.setScene(home.getScene());
	}
	public void switchToFirst() {
		stage.setScene(first.getScene());
	}
}

