package com.union.brainrush.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.union.brainrush.service.Player;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

@Component
@Lazy
public class Result {
	Scene scene;
	StackPane root;

	// Main slot
	VBox vBox;

	StackPane upEmptySlot;

	// First Slot
	HBox firstSlot;
	StackPane fTextSlot;
	Label fLabel;
	StackPane fImageSlot;
	ImageView fImage;
	StackPane fProgressSlot;
	Label fMark;
	ProgressBar firstProgressBar;

	// Second Slot
	HBox secondSlot;
	StackPane sTextSlot;
	Label sLabel;
	StackPane sImageSlot;
	ImageView sImage;
	StackPane sProgressSlot;
	Label sMark;
	ProgressBar secondProgressBar;

	// Third Slot
	HBox thirdSlot;
	StackPane tTextSlot;
	Label tLabel;
	StackPane tImageSlot;
	ImageView tImage;
	StackPane tProgressSlot;
	Label tMark;
	ProgressBar thirdProgressBar;

	StackPane downEmptySlot;

	int index1, index2, index3 = 0;
	int removedIndex = 0;
	String currentMark1, currentMark2, currentMark3 = "";
	Font label_small_font;
	String fth = "ပထမ";
	String snd = "ဒုတိယ";
	String trd = "တတိယ";
	String draw = "သရေ";

	List<Integer> players = new ArrayList<>(Arrays.asList(Player.fPlayerMark, Player.sPlayerMark, Player.tPlayerMark));

	Result() {
		Collections.sort(players);
		root = new StackPane();
		// Set the VBox's height ratios
		double[] vBoxProportions = { 0.2, 0.2, 0.2, 0.2, 0.2 };

		vBox = new VBox();

		upEmptySlot = new StackPane();

		firstSlot = new HBox();
		fTextSlot = new StackPane();
		fLabel = new Label();
		fTextSlot.getChildren().add(fLabel);
		fImageSlot = new StackPane();
		fImage = new ImageView(new Image("images/result/eaistein.png"));
		fImageSlot.getChildren().add(fImage);
		fProgressSlot = new StackPane();

		secondSlot = new HBox();
		sTextSlot = new StackPane();
		sLabel = new Label();
		sTextSlot.getChildren().add(sLabel);
		sImageSlot = new StackPane();
		sImage = new ImageView(new Image("images/result/newton.png"));
		sImageSlot.getChildren().add(sImage);
		sProgressSlot = new StackPane();

		thirdSlot = new HBox();
		tTextSlot = new StackPane();
		tLabel = new Label();
		tImageSlot = new StackPane();
		tTextSlot.getChildren().add(tLabel);
		tImage = new ImageView(new Image("images/result/leo.png"));
		tImageSlot.getChildren().add(tImage);
		tProgressSlot = new StackPane();

		fMark = new Label("0/10");
		sMark = new Label("0/10");
		tMark = new Label("0/10");

		firstProgressBar = new ProgressBar(0);
		secondProgressBar = new ProgressBar(0);
		thirdProgressBar = new ProgressBar(0);
		ProgressBar[] progressBar = { firstProgressBar, secondProgressBar, thirdProgressBar };
		Label[] marks = { fMark, sMark, tMark };
		progressBarLogic(progressBar, marks);

		fProgressSlot.getChildren().addAll(firstProgressBar, fMark);
		sProgressSlot.getChildren().addAll(secondProgressBar, sMark);
		tProgressSlot.getChildren().addAll(thirdProgressBar, tMark);

		responsive();

		firstSlot.getChildren().addAll(fTextSlot, fImageSlot, fProgressSlot);
		secondSlot.getChildren().addAll(sTextSlot, sImageSlot, sProgressSlot);
		thirdSlot.getChildren().addAll(tTextSlot, tImageSlot, tProgressSlot);

		downEmptySlot = new StackPane();

		vBox.getChildren().addAll(upEmptySlot, firstSlot, secondSlot, thirdSlot, downEmptySlot);

		root.setBackground(Background.fill(Color.GRAY));
		scene = new Scene(root, UiConstant.WIDTH, UiConstant.HEIGHT);
		root.getChildren().add(vBox);

		for (int i = 0; i < vBox.getChildren().size(); i++) {
			if (vBox.getChildren().get(i) instanceof Region) { // Only bind for nodes that are Regions
				((Region) vBox.getChildren().get(i)).prefHeightProperty()
						.bind(vBox.heightProperty().multiply(vBoxProportions[i]));
			}
		}

		vBox.maxWidthProperty().bind(scene.widthProperty().multiply(3).divide(5));

	}

	private void progressBarLogic(ProgressBar[] progressBar, Label[] mark) {
		ImageView fImagefinal = new ImageView(new Image("images/result/eaistein.png"));
		ImageView sImagefinal = new ImageView(new Image("images/result/newton.png"));
		ImageView tImagefinal = new ImageView(new Image("images/result/leo.png"));
		ImageView[] imageFinal = { fImagefinal, sImagefinal, tImagefinal };
		ImageView[] fimageViews = { fImage, sImage, tImage };
		ImageView fImages = new ImageView(new Image("images/result/eaistein.png"));
		ImageView sImages = new ImageView(new Image("images/result/newton.png"));
		ImageView tImages = new ImageView(new Image("images/result/leo.png"));
		ImageView[] simageViews = { tImages, sImages, fImages };
		ImageView fImaget = new ImageView(new Image("images/result/eaistein.png"));
		ImageView sImaget = new ImageView(new Image("images/result/newton.png"));
		ImageView tImaget = new ImageView(new Image("images/result/leo.png"));
		ImageView[] timageViews = { tImaget, fImaget, sImaget };

		HashMap<String, Integer> playerMarks = new HashMap<>();
		playerMarks.put("fPlayerMark", Player.fPlayerMark);
		playerMarks.put("sPlayerMark", Player.sPlayerMark);
		playerMarks.put("tPlayerMark", Player.tPlayerMark);

		List<Map.Entry<String, Integer>> list = new ArrayList<>(playerMarks.entrySet());

		list.sort((a, b) -> b.getValue().compareTo(a.getValue()));

		List<Integer> indices = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			indices.add(i);
		}
		String style = "-fx-accent: orange;";

		for (int i = 0; i < 3; i++) {
			progressBar[i].setStyle(style);

			Task<Void> task1 = new Task<>() {
				@Override
				protected Void call() throws InterruptedException {
					for (int i = 0; i <= players.get(2) * 10; i++) {
						updateProgress(i, 100);
						currentMark1 = String.valueOf(i / 10);
						// Calculate the current index based on the progress (0, 1, 2)
//                         int currentIndex = (int)(i / 33.33);  // Dividing by 33.33 to divide progress into 3 steps
						if (index1 == 2) {
							index1 = 0;
						} else {
							index1++;
						}

						// Update the StackPane with the corresponding ImageView
						Platform.runLater(() -> {
							fMark.setText(currentMark1 + "/10");
							fImageSlot.getChildren().clear();
							fImageSlot.getChildren().add(fimageViews[index1]);
						});
						Thread.sleep(50); // Simulate work
					}
					Platform.runLater(() -> {
						labelText(fLabel);
						fLabel.setText(fth);
						if(list.get(0).getValue()==list.get(1).getValue() || list.get(0).getValue()==list.get(2).getValue()) {
							fLabel.setText(draw);
						}
//						labelText(fLabel, players, fImageSlot, "F_S_Slot");
						fImageSlot.getChildren().clear();
						fImageSlot.getChildren().add(imageFinal[Player.playerMark().get(list.get(0).getKey())]);

					});
					return null;
				}
			};
			Task<Void> task2 = new Task<>() {
				@Override
				protected Void call() throws InterruptedException {
					for (int i = 0; i <= players.get(1) * 10; i++) {
						updateProgress(i, 100);
						currentMark2 = String.valueOf(i / 10);
						// Calculate the current index based on the progress (0, 1, 2)
//                            	int currentIndex = (int)(i / 33.33);  // Dividing by 33.33 to divide progress into 3 steps
						if (index2 == 2) {
							index2 = 0;
						} else {
							index2++;
						}
						// Update the StackPane with the corresponding ImageView
						Platform.runLater(() -> {
							sMark.setText(currentMark2 + "/10");
							sImageSlot.getChildren().clear();
							sImageSlot.getChildren().add(simageViews[index2]);
						});

						Thread.sleep(50); // Simulate work
					}
					Platform.runLater(() -> {
						labelText(sLabel);
						sLabel.setText(snd);
						if(list.get(1).getValue()==list.get(0).getValue() || list.get(1).getValue()==list.get(2).getValue()) {
							sLabel.setText(draw);
						}
						sImageSlot.getChildren().clear();
						sImageSlot.getChildren().add(imageFinal[Player.playerMark().get(list.get(1).getKey())]);
					});
					return null;
				}
			};
			Task<Void> task3 = new Task<>() {
				@Override
				protected Void call() throws InterruptedException {
					for (int i = 0; i <= players.get(0) * 10; i++) {
						updateProgress(i, 100);
						currentMark3 = String.valueOf(i / 10);
						if (index3 == 2) {
							index3 = 0;
						} else {
							index3++;
						}
						// Update the StackPane with the corresponding ImageView
						Platform.runLater(() -> {
							tMark.setText(currentMark3 + "/10");
							tImageSlot.getChildren().clear();
							tImageSlot.getChildren().add(timageViews[index3]);
						});
						Thread.sleep(50); // Simulate work
					}
					Platform.runLater(() -> {
						labelText(tLabel);
						tLabel.setText(trd);
						if(list.get(2).getValue()==list.get(0).getValue() || list.get(2).getValue()==list.get(1).getValue()) {
							tLabel.setText(draw);
						}
						tImageSlot.getChildren().clear();
						tImageSlot.getChildren().add(imageFinal[Player.playerMark().get(list.get(2).getKey())]);
					});
					return null;
				}
			};
			Task[] task = { task1, task2, task3 };
			// Bind the progress bar to the task's progress
			progressBar[i].progressProperty().bind(task[i].progressProperty());
			// Start the task in a new thread
			Thread thread = new Thread(task[i]);
			thread.setDaemon(true);
			thread.start();
			StackPane.setAlignment(mark[i], Pos.TOP_CENTER);
			mark[i].setFont(Font.font(40));
			mark[i].setStyle("-fx-font-weight:bold;");
			progressBar[i].prefWidthProperty().bind(fProgressSlot.widthProperty().multiply(8).divide(10));
			progressBar[i].prefHeightProperty().bind(fProgressSlot.heightProperty().multiply(1).divide(5));
		}
	}

	private void labelText(Label label) {
		label_small_font = Font.loadFont(getClass().getResourceAsStream(UiConstant.NOTO_REGULAR_PATH), 25);
		label.setFont(label_small_font);
		label.setTextFill(Color.WHITE);
	}

	private void responsive() {
		fTextSlot.prefWidthProperty().bind(firstSlot.widthProperty().divide(5));
		fImageSlot.prefWidthProperty().bind(firstSlot.widthProperty().divide(5));
		fProgressSlot.prefWidthProperty().bind(firstSlot.widthProperty().multiply(3).divide(5));

		sTextSlot.prefWidthProperty().bind(firstSlot.widthProperty().divide(5));
		sImageSlot.prefWidthProperty().bind(firstSlot.widthProperty().divide(5));
		sProgressSlot.prefWidthProperty().bind(firstSlot.widthProperty().multiply(3).divide(5));

		tTextSlot.prefWidthProperty().bind(firstSlot.widthProperty().divide(5));
		tImageSlot.prefWidthProperty().bind(firstSlot.widthProperty().divide(5));
		tProgressSlot.prefWidthProperty().bind(firstSlot.widthProperty().multiply(3).divide(5));
	}

	public Scene getScene() {
		return scene;
	}
}
