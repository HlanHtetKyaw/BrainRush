package com.union.brainrush.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.union.brainrush.routing.SceneManager;

import jakarta.annotation.PostConstruct;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

@Component
@Lazy
public class TransitionState {
	// Root Pane
	private StackPane overlayPane;

	// VBox for three children
	private VBox vBox;

	// Three layout for vBox
	private StackPane upperLayout;
	private StackPane middleLayout;
	private StackPane underLayout;

	// Upper child
	private HBox hBox;

	// Player slot
	private ImageView[] playerSlot = { UiConstant.firstPlayer, UiConstant.secondPlayer, UiConstant.thirdPlayer };
	// Counter
	private Label counter;
	private Font counter_small_font;
	private Timeline timeline;
	private int remain_counter;

	// Under child
	private Label announcedLabel;
	private Font label_small_font;

	@Autowired
	private SceneManager sceneManager;
	
	@Autowired
	private Question questionState;

	public void showTransitionState(String announcedString, StackPane root, boolean question) {
		remain_counter = 1;
		// Set the VBox's height ratios
		double[] proportions = { 0.325, 0.35, 0.325 };

		// Create VBox
		vBox = new VBox();

		upperLayout = new StackPane();

		middleLayout = new StackPane();

		underLayout = new StackPane();

		// VBox for upperLayout
		hBox = new HBox();
		hBox.setAlignment(Pos.BOTTOM_CENTER);
		for (int i = 0; i < UiConstant.playerQuantity; i++) {
			hBox.getChildren().add(playerSlot[i]);
		}
		StackPane.setAlignment(hBox, Pos.BOTTOM_CENTER);
		upperLayout.getChildren().add(hBox);
		// Counter label
		counter = new Label("၃");
		counter_small_font = Font.loadFont(getClass().getResourceAsStream(UiConstant.NOTO_REGULAR_PATH), 40);
		counter.setFont(counter_small_font);
		counter.setAlignment(Pos.CENTER);
		counter.setStyle("-fx-background-color: white;-fx-background-radius:50%;");
		counter.setMaxSize(200, 200);

		// Countdown logic
		String[] counterText = { "၁", "၂" };
		timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
			System.out.println(remain_counter);
			if (remain_counter == -1) {
				UiConstant.WIDTH = (int) overlayPane.getWidth();
				UiConstant.HEIGHT = (int) overlayPane.getHeight();
				if (question) {
					questionState.questionState(true);
					sceneManager.switchToQuestion();
				} else {
					questionState.questionState(false);
					sceneManager.switchToQuestion();
				}
			} else {
				counter.setText(counterText[remain_counter]);
				remain_counter--;
			}
		}));
		timeline.setCycleCount(3);
		timeline.play();
		middleLayout.getChildren().add(counter);

		// Announced Text
		announcedLabel = new Label();
		announcedLabel.setText(announcedString);
		announcedLabel.setTextFill(Color.WHITE);
		label_small_font = Font.loadFont(getClass().getResourceAsStream(UiConstant.NOTO_REGULAR_PATH), 25);
		announcedLabel.setFont(label_small_font);
		StackPane.setAlignment(announcedLabel, Pos.TOP_CENTER);

		underLayout.getChildren().add(announcedLabel);

		vBox.getChildren().addAll(upperLayout, middleLayout, underLayout);

		// Bind the height of each child to the proportions of the VBox
		// Bind heights to proportions
		for (int i = 0; i < vBox.getChildren().size(); i++) {
			if (vBox.getChildren().get(i) instanceof Region) { // Only bind for nodes that are Regions
				((Region) vBox.getChildren().get(i)).prefHeightProperty()
						.bind(vBox.heightProperty().multiply(proportions[i]));
			}
		}

		// Overlay StackPane
		overlayPane = new StackPane();

		// Setting background black transparent
		overlayPane.setBackground(Background.fill(Color.rgb(0, 0, 0, 0.8)));

		overlayPane.getChildren().add(vBox);
		vBox.maxWidthProperty().bind(overlayPane.widthProperty().divide(3));
		root.getChildren().add(overlayPane);
		responsive();
	}

	private void responsive() {
		overlayPane.widthProperty().addListener((observable, oldValue, newValue) -> {
			double width = newValue.doubleValue();

			if (width > 1024 && width <= 1440) {
				// Medium Screen
			} else if (width > 1440) {
				// Large Screen
				label_small_font = Font.loadFont(getClass().getResourceAsStream(UiConstant.NOTO_REGULAR_PATH), 30);
				announcedLabel.setFont(label_small_font);
				counter.setMaxSize(300, 300);
			} else {
				// Small Screen
				label_small_font = Font.loadFont(getClass().getResourceAsStream(UiConstant.NOTO_REGULAR_PATH), 25);
				announcedLabel.setFont(label_small_font);
				counter.setMaxSize(200, 200);
			}
		});
	}
}
