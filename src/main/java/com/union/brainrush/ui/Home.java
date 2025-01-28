package com.union.brainrush.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

@Component
public class Home {
	// Root and Scene
	StackPane root;
	Scene scene;

	// Create the VBox
	VBox vBox;

	// Create top slot (titleImage)
	ImageView titleImage;
	StackPane imagelayout;

	// Create second slot (Label)
	Label label;
	Font small_font;

	// Create third slots (for 3 Buttons)
	StackPane stackpane1;
	StackPane stackpane2;
	StackPane stackpane3;

	// 3 Buttons
	Button firstPlayerButton;
	Button secondPlayerButton;
	Button thirdPlayerButton;

	@Autowired
	public Home() {
		// Create the VBox
		vBox = new VBox();

		// Set the VBox's height ratios
		double[] proportions = { 3.0 / 10, 1.0 / 10, 2.0 / 10, 2.0 / 10, 2.0 / 10 };

		// Create title image
		titleImage = new ImageView(new Image("images/title/BrainRushTitle.png"));
		titleImage.setFitHeight(200);
		titleImage.setFitWidth(300);

		// Create top slot (titleImage)
		imagelayout = new StackPane();
		imagelayout.getChildren().add(titleImage);

		// Create second slot (Label)
		label = new Label("ဘယ်နှစ်ယောက်ကစားမှာလဲ..?");
		small_font = Font.loadFont(getClass().getResourceAsStream(UiConstant.NOTO_REGULAR_PATH), 25);
		label.setFont(small_font);
		label.setStyle("-fx-alignment: center;");
		label.setMaxWidth(Double.MAX_VALUE);
		label.setMaxHeight(Double.MAX_VALUE);

		// Create third slots (for 3 Buttons)
		stackpane1 = new StackPane();
		stackpane2 = new StackPane();
		stackpane3 = new StackPane();

		// 3 Buttons
		firstPlayerButton = new Button("တစ်ယောက်");
		secondPlayerButton = new Button("နှစ်ယောက်");
		thirdPlayerButton = new Button("သုံးယောက်");

		// Looping array
		StackPane[] stackpanes = { stackpane1, stackpane2, stackpane3 };
		Button[] buttons = { firstPlayerButton, secondPlayerButton, thirdPlayerButton };

		int index = 0;
		for (StackPane stackpane : stackpanes) {

			buttons[index].getStyleClass().add("bottom_format");
			stackpane.getChildren().add(buttons[index]);

			// Setting width and height of stackpane layout
			stackpane.setMaxWidth(Double.MAX_VALUE);
			stackpane.setMaxHeight(Double.MAX_VALUE);

			// Setting width and height of buttons
			buttons[index].prefWidthProperty().bind(stackpane.widthProperty().multiply(3).divide(5));
			buttons[index].prefHeightProperty().bind(stackpane.heightProperty().multiply(2).divide(5));

			index++;
		}

		// Add children to VBox
		vBox.getChildren().addAll(imagelayout, label, stackpane1, stackpane2, stackpane3);

		// Bind the height of each child to the proportions of the VBox
		// Bind heights to proportions
		for (int i = 0; i < vBox.getChildren().size(); i++) {
			if (vBox.getChildren().get(i) instanceof Region) { // Only bind for nodes that are Regions
				((Region) vBox.getChildren().get(i)).prefHeightProperty()
						.bind(vBox.heightProperty().multiply(proportions[i]));
			}
		}

		// Create the root StackPane
		root = new StackPane(vBox);
		scene = new Scene(root, UiConstant.WIDTH, UiConstant.HEIGHT);
		scene.getStylesheets().add("css/style.css");
		vBox.maxWidthProperty().bind(scene.widthProperty().divide(3));
		// Responsive
		responsive();
	}

	private void responsive() {
		scene.widthProperty().addListener((observable, oldValue, newValue) -> {
			double width = newValue.doubleValue();

			if (width > 1024 && width <= 1440) {
				// Medium Screen
				
				// TitleImage transform
				StackPane.setMargin(titleImage, new Insets(0, 60, 0, 0));
				titleImage.setFitHeight(250);
				titleImage.setFitWidth(350);
				
				// Label font size
				Font medium_font = Font.loadFont(getClass().getResourceAsStream(UiConstant.NOTO_REGULAR_PATH), 30);
				label.setFont(medium_font);
			} else if (width > 1440) {
				// Large Screen
				
				// TitleImage transform
				titleImage.setFitHeight(300);
				titleImage.setFitWidth(400);
			} else {
				// Small Screen
				
				// TitleImage transform
				titleImage.setFitHeight(200);
				titleImage.setFitWidth(300);
			}
		});
	}

	public Scene getScene() {
		return scene;
	}
}
