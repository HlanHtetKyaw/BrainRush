package com.union.brainrush;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.union.brainrush.routing.SceneManager;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

@SpringBootApplication
public class BrainRushApplication extends Application {

	public ApplicationContext springContext;

	@Override
	public void init() {
		// Initialize Spring context
		springContext = SpringApplication.run(BrainRushApplication.class);
	}

	@Override
	public void stop() {
		// Close the Spring context
		((AnnotationConfigApplicationContext) springContext).close();
	}

	@Override
	public void start(Stage primaryStage) {
		// Initialize stage for SceneManager
		SceneManager.initialize(primaryStage);

		// Getting SceneManager class from Spring MVC
		springContext.getBean(SceneManager.class).switchToHome();

		// Title
		primaryStage.setTitle("Brain Rush");
		primaryStage.show();
	}

	public static void main(String[] args) {
		// Launch JavaFX application
		launch(args);
	}
}
