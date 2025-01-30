package com.union.brainrush.ui;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;

@Component
@Lazy
public class Question {
	Scene scene;
	private StackPane root;
	private StackPane questionPane;
	private StackPane answerPane;
	private StackPane qUpperLayout;
	private StackPane qBottomLayout;
	@Autowired
	public Question() {
		root = new StackPane();

		questionPane = new StackPane();
		qUpperLayout = new StackPane();
		qBottomLayout = new StackPane();
		qUpperLayout.setBackground(Background.fill(Color.BROWN));
		qBottomLayout.setBackground(Background.fill(Color.BLACK));
		// label.setFont(label_small_font);
		String text = "လက်တွေ့ရေးနိုင်ဖိုဆိုရင် လက်တွေ့ရေးကြည့်မှ ရပါမယ်၊ နောက်တစ်ခု အရေးကြီးတာက သူများရေးထားတဲ့ Code ကိုလေ့လာရပါမယ်ဗျ။";
		System.out.println(text.length());
		TextFlow textFlow = createTextFlow(text);
		textFlow.setStyle("-fx-background-color: white; " + "-fx-background-radius: 20; " + "-fx-padding: 10; "
				+ "-fx-text-wrap: true;-fx-text-alignment: center;");
		StackPane.setAlignment(textFlow, Pos.TOP_CENTER);
		qBottomLayout.getChildren().add(textFlow);
		StackPane.setAlignment(qUpperLayout, Pos.TOP_CENTER);
		StackPane.setAlignment(qBottomLayout, Pos.BOTTOM_CENTER);
		questionPane.getChildren().addAll(qUpperLayout,qBottomLayout);

		answerPane = new StackPane();

		StackPane.setAlignment(questionPane, Pos.CENTER_LEFT);
		StackPane.setAlignment(answerPane, Pos.CENTER_RIGHT);

		root.setBackground(Background.fill(Color.ORANGE));
		root.getChildren().addAll(questionPane, answerPane);
		scene = new Scene(root, 1024, 768);
		questionPane.maxWidthProperty().bind(scene.widthProperty().multiply(6).divide(10));
		answerPane.maxWidthProperty().bind(scene.widthProperty().multiply(4).divide(10));
		qUpperLayout.maxHeightProperty().bind(scene.heightProperty().multiply(3).divide(10));
		qBottomLayout.maxHeightProperty().bind(scene.heightProperty().multiply(7).divide(10));
		scene.getStylesheets().add("css/style.css");
	}

	private TextFlow createTextFlow(String text) {
		TextFlow textFlow = new TextFlow();
		textFlow.setMaxSize(text.length()+200, text.length());
		// Define regex patterns for detecting English and Burmese text
		Pattern englishPattern = Pattern.compile("[A-Za-z]");

		// Load Burmese font (replace with your actual font path)
		Font burmeseFont = Font.loadFont(getClass().getResourceAsStream(UiConstant.NOTO_REGULAR_PATH), 20);
		Font englishFont = Font.font("Arial", 20); // Arial for English

		// Split the input text into parts
		String[] parts = text.split("(?=[A-Za-z])|(?<=[A-Za-z])"); // Regex splits at word boundaries

		for (String part : parts) {
			Text textPart = new Text(part);

			// If the part contains English letters, set the font to Arial
			if (englishPattern.matcher(part).find()) {
				textPart.setFont(englishFont);
			} else {
				// Otherwise, set the font to the Burmese font
				textPart.setFont(burmeseFont);
			}

			// Add the text part to TextFlow
			textFlow.getChildren().add(textPart);
		}

		return textFlow;
	}

	public Scene getScene() {
		return scene;
	}
}
