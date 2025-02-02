package com.union.brainrush.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.union.brainrush.model.QuestionFormat;
import com.union.brainrush.routing.SceneManager;
import com.union.brainrush.service.Player;
import com.union.brainrush.service.QuestionService;
import com.union.brainrush.service.SerialService;

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
	public static StackPane root;
	private StackPane questionPane;
	private StackPane answerPane;
	private StackPane qUpperLayout;
	private StackPane qBottomLayout;
	
	// Button
	public static Button actionButton;
	
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
	
	QuestionService questions;
	ArrayList<QuestionFormat> questionArray;
	QuestionFormat question;
	public static int questionPointer = 0;
	
	@Autowired
	@Lazy
	TransitionState transitionState;
	
	@Autowired
	SceneManager sceneManager;
	
	SerialService serialService;
	
	@Autowired
	public Question(QuestionService questions) {
		this.questions = questions;
	}
	public void questionState(boolean shuffle) {
		serialService = new SerialService();
		serialService.sendMessage("PC:{choice:True}");
		serialService.start();
		questionArray = questions.getQuestions();
		if(shuffle) {
			Collections.shuffle(questionArray);
		}
		
		question = questionArray.get(questionPointer);
		root = new StackPane();
		
		questionPane = new StackPane();
		qUpperLayout = new StackPane();
		qBottomLayout = new StackPane();
		
		// Button
		actionButton = new Button();
		actionButton.setOnAction(e->{
			nextQuestion();
		});
		StackPane.setAlignment(actionButton, Pos.TOP_LEFT);
		// VBox for upperLayout
		hBox = new HBox();

		for (int i = 0; i < UiConstant.playerQuantity; i++) {
			hBox.getChildren().add(playerSlot[i]);
		}
		StackPane.setAlignment(hBox, Pos.BOTTOM_CENTER);
		qUpperLayout.getChildren().addAll(hBox,actionButton);
		// label.setFont(label_small_font);
		Player.rightAns = question.getRightAns();
		String text = question.getQuestion();
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
		String a = question.getAns().get("A");
		TextFlow ans1 = createTextFlow(a);
		String b = question.getAns().get("B");
		TextFlow ans2 = createTextFlow(b);
		String c = question.getAns().get("C");
		TextFlow ans3 = createTextFlow(c);
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
	public void nextQuestion() {
		System.out.println("Answer extract : "+Player.fPlayerAns);
        System.out.println("Right answer : "+ Player.rightAns);
        
        if(Player.rightAns.equals(Player.fPlayerAns)) {
        	Player.fPlayerMark++;
        }
        System.out.println("Current mark : "+ Player.fPlayerMark);
		questionPointer++;
		if(questionPointer==10) {
			serialService.stopService();
			sceneManager.switchToResult();
		}else {
			serialService.stopService();
			transitionState.showTransitionState("နောက်မေးခွန်းလာပါတော့မယ်", root,false);
		}
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
