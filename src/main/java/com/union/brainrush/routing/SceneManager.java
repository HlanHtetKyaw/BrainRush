package com.union.brainrush.routing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.union.brainrush.ui.First;
import com.union.brainrush.ui.Home;
import com.union.brainrush.ui.Question;

import javafx.stage.Stage;

@Component
public class SceneManager {
	private static Stage stage;
	private Home home;
	private First first;
	private Question question;
	@Autowired
	SceneManager(Home home,First first,Question question) {
		this.home = home;
		this.first = first;
		this.question = question;
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
	public void switchToQuestion() {
		stage.setScene(question.getScene());
	}
}

