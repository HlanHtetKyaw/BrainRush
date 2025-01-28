package com.union.brainrush.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

@Component
public class Home {
	Scene scene;

	@Autowired
	public Home() {
		// StackPane root = new StackPane();
		// Create the VBox
		VBox vBox = new VBox();

		// Set the VBox's height ratios
		double[] proportions = { 0, 1.0 / 10, 2.0 / 10, 2.0 / 10, 2.0 / 10 };

		// Create top slot (ImageView)
		ImageView imageView = new ImageView(new Image("images/title/BrainRushTitle.png")); // Placeholder image
		imageView.setPreserveRatio(false);
		imageView.setFitHeight(200);
		imageView.setFitWidth(300);

		// Create second slot (Label)
		Label label = new Label("ဘယ်နှစ်ယောက်ကစားမှာလဲ..?");
		label.setStyle("-fx-alignment: center;");
		label.setMaxWidth(Double.MAX_VALUE);
		label.setMaxHeight(Double.MAX_VALUE);

		// Create bottom slots (3 Labels)
		StackPane stackpane1 = new StackPane();
		StackPane stackpane2 = new StackPane();
		StackPane stackpane3 = new StackPane();

		Button firstPlayerButton = new Button("First");
		Button secondPlayerButton = new Button("Second");
		Button thirdPlayerButton = new Button("Third");

		StackPane[] stackpanes = { stackpane1, stackpane2, stackpane3 };
		Button[] buttons = { firstPlayerButton, secondPlayerButton, thirdPlayerButton };
		int index = 0;
		for (StackPane stackpane : stackpanes) {
			stackpane.getChildren().add(buttons[index]);
			stackpane.setMaxWidth(Double.MAX_VALUE);
			stackpane.setMaxHeight(Double.MAX_VALUE);
			buttons[index].prefWidthProperty().bind(stackpane.widthProperty().multiply(3).divide(5));
			buttons[index].prefHeightProperty().bind(stackpane.heightProperty().multiply(2).divide(5));
			index++;
		}

		// Add children to VBox
		vBox.getChildren().addAll(imageView, label, stackpane1, stackpane2, stackpane3);

		// Bind the height of each child to the proportions of the VBox
		// Bind heights to proportions
		for (int i = 0; i < vBox.getChildren().size(); i++) {
			if (vBox.getChildren().get(i) instanceof Region) { // Only bind for nodes that are Regions
				((Region) vBox.getChildren().get(i)).prefHeightProperty()
						.bind(vBox.heightProperty().multiply(proportions[i]));
			}
		}

		// Create the root StackPane
		StackPane root = new StackPane(vBox);
		scene = new Scene(root, UiConstant.WIDTH.getValue(), UiConstant.HEIGHT.getValue());
		vBox.maxWidthProperty().bind(scene.widthProperty().divide(3));
//		VBox middlelayout = new VBox();
//		ImageView BrainRushTitle = new ImageView(new Image("images/title/BrainRushTitle.png"));
//		middlelayout.setSpacing(10);
//		Label chooseLabel = new Label("ဘယ်နှစ်ယောက်ကစားမှာလဲ..?");
//		Button firstPlayerButton = new Button("First");
//		Button secondPlayerButton = new Button("Second");
//		Button thirdPlayerButton = new Button("Third");
//		middlelayout.setBackground(Background.fill(Color.RED));
//		middlelayout.setAlignment(Pos.CENTER);
//		middlelayout.setMaxWidth(scene.getWidth()/3);
//		middlelayout.maxWidthProperty().bind(scene.widthProperty().divide(3));
//		middlelayout.layoutBoundsProperty().addListener((observable, oldValue, newValue) -> {
//            System.out.println("Width: " + newValue.getWidth());
//            System.out.println("Height: " + newValue.getHeight());
//        });
//		VBox.setVgrow(chooseLabel, Priority.NEVER);
//		VBox.setVgrow(firstPlayerButton, Priority.ALWAYS);
//		VBox.setVgrow(secondPlayerButton, Priority.ALWAYS);
//		VBox.setVgrow(thirdPlayerButton, Priority.ALWAYS);
//		
//		middlelayout.getChildren().addAll(chooseLabel,firstPlayerButton,secondPlayerButton,thirdPlayerButton);
//		StackPane.setAlignment(middlelayout, Pos.CENTER);
//		root.getChildren().addAll(BrainRushTitle,middlelayout);

	}

	public Scene getScene() {
		return scene;
	}
}
