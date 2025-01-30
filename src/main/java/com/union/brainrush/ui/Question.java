package com.union.brainrush.ui;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
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

	// Player slot hBox
	private HBox hBox;

	// Player slot
	private ImageView[] playerSlot = { UiConstant.firstPlayer, UiConstant.secondPlayer, UiConstant.thirdPlayer };

	private TextFlow textFlow;

	// Buttons slot vBox and stackPane for each
	private VBox vBox;
	StackPane stackpane1;
	StackPane stackpane2;
	StackPane stackpane3;

	// 3 Buttons
	Button firstPlayerButton;
	Button secondPlayerButton;
	Button thirdPlayerButton;

	// Labels A,B,C
	Label A;
	Label B;
	Label C;
	Font burmeseFont = Font.loadFont(getClass().getResourceAsStream(UiConstant.NOTO_REGULAR_PATH), 20);
	Font englishFont = Font.font("Arial", 20);

	@Autowired
	public Question() {
		root = new StackPane();
		
		questionPane = new StackPane();
		qUpperLayout = new StackPane();
		qBottomLayout = new StackPane();
		// VBox for upperLayout
		hBox = new HBox();

		for (int i = 0; i < UiConstant.playerQuantity; i++) {
			hBox.getChildren().add(playerSlot[i]);
		}
		StackPane.setAlignment(hBox, Pos.BOTTOM_CENTER);
		qUpperLayout.getChildren().add(hBox);
		// label.setFont(label_small_font);
		String text = "လက်တွေ့ရေးနိုင်ဖိုဆိုရင် လက်တွေ့ရေးကြည့်မှ ရပါမယ်၊ နောက်တစ်ခု အရေးကြီးတာက သူများရေးထားတဲ့ Code ကိုလေ့လာရပါမယ်ဗျ။";
		textFlow = createTextFlow(text);
		textFlow.getStyleClass().add("question_text_flow");
		qBottomLayout.getChildren().add(textFlow);

		questionPane.getChildren().addAll(qUpperLayout, qBottomLayout);

		answerPane = new StackPane();
		vBox = new VBox(20);
		// Create third slots (for 3 ans)
		stackpane1 = new StackPane();
		stackpane2 = new StackPane();
		stackpane3 = new StackPane();
		
		
		A = new Label("A");
		B = new Label("B");
		C = new Label("C");
		Label[] ABC = { A, B, C };
		for (Label each : ABC) {
			each.getStyleClass().add("ABC");
			each.setFont(englishFont);
			each.setMaxSize(60, 60);
			each.setAlignment(Pos.CENTER);
			StackPane.setAlignment(each, Pos.CENTER_LEFT);
		}
		
		TextFlow ans1 = createTextFlow("တစ်လက်တွေ့ရေးနိုင်ဖိုဆိုရင်");
		TextFlow ans2 = createTextFlow("၁");
		TextFlow ans3 = createTextFlow("တစ်ယောက်C");
		TextFlow[] ans = {ans1,ans2,ans3};
		for(TextFlow each: ans) {
			StackPane.setAlignment(each, Pos.CENTER_LEFT);
			StackPane.setMargin(each, new Insets(0, 0, 0, 80));
			each.setMaxSize(250, 30);
			each.getStyleClass().add("answer");
		}
		
		stackpane1.getChildren().addAll(A, ans1);
		stackpane2.getChildren().addAll(B, ans2);
		stackpane3.getChildren().addAll(C, ans3);
		vBox.getChildren().addAll(stackpane1, stackpane2, stackpane3);
		
		answerPane.getChildren().add(vBox);
		
		root.setBackground(Background.fill(Color.ORANGE));
		root.getChildren().addAll(questionPane, answerPane);
		scene = new Scene(root,UiConstant.WIDTH,UiConstant.HEIGHT);
		System.out.println(scene.getWidth());
		questionPane.maxWidthProperty().bind(scene.widthProperty().multiply(6).divide(10));
		answerPane.maxWidthProperty().bind(scene.widthProperty().multiply(4).divide(10));
		qUpperLayout.maxHeightProperty().bind(scene.heightProperty().multiply(6).divide(20));
		qBottomLayout.maxHeightProperty().bind(scene.heightProperty().multiply(13).divide(20));
		answerPane.maxHeightProperty().bind(scene.heightProperty().multiply(13).divide(20));
		scene.getStylesheets().add("css/style.css");
		positionPane();
	}

	private void positionPane() {
		hBox.setAlignment(Pos.BOTTOM_CENTER);
		StackPane.setAlignment(textFlow, Pos.TOP_CENTER);
		StackPane.setAlignment(qUpperLayout, Pos.TOP_CENTER);
		StackPane.setAlignment(qBottomLayout, Pos.BOTTOM_CENTER);
		StackPane.setAlignment(questionPane, Pos.CENTER_LEFT);
		StackPane.setAlignment(answerPane, Pos.BOTTOM_RIGHT);
	}

	private TextFlow createTextFlow(String text) {
		TextFlow textFlow = new TextFlow();
		textFlow.setMaxSize(text.length() + 200, text.length());
		// Define regex patterns for detecting English and Burmese text
		Pattern englishPattern = Pattern.compile("[A-Za-z]");

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
