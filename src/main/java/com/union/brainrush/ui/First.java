package com.union.brainrush.ui;

import java.util.Enumeration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import purejavacomm.*;
@Component
public class First {
	Scene scene;
	@Autowired
	First(){
		
		 StackPane root = new StackPane();
	        root.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, null, null)));
	        StackPane panel1 = new StackPane();
	        Label label = new Label("လက်တွေ့ရေးနိုင်ဖိုဆိုရင် လက်တွေ့ရေးကြည့်မှ ရပါမယ်၊ နောက်တစ်ခု အရေးကြီးတာက သူများရေးထားတဲ့ Code ကိုလေ့လာရပါမယ်ဗျ။ "
	        		);
	     // Enable text wrapping
	        label.setWrapText(true);
	        
	     // Set a maximum width for the Label so text wraps properly
	        label.setMaxWidth(300);
	        // Style the text
	        label.setFont(new Font("Arial", 16));
	        label.setStyle("-fx-text-fill: #333333;-fx-text-alignment: center; -fx-padding: 10;-fx-line-spacing: 5px;"); // Dark gray text color

	        // Add padding around the text
	        label.setPadding(new Insets(10));

	        // Add a background color
	        label.setStyle(label.getStyle() + "-fx-background-color: #f0f0f0; -fx-border-color: #cccccc; -fx-border-width: 1;");
	        panel1.setAlignment(Pos.CENTER);
	        panel1.getChildren().add(label);
	        panel1.setMaxSize(400, 400);
	        
	        StackPane.setAlignment(panel1,Pos.CENTER_LEFT);
	        Region panel2 = new Region();
	        panel2.setMaxSize(400, 400);
	        panel2.setBackground(Background.fill(Color.BLACK));
	        StackPane.setAlignment(panel2,Pos.CENTER_RIGHT);
	        root.getChildren().addAll(panel1,panel2);
	     
		scene = new Scene(root,1024,768);
		panel1.setBackground(Background.fill(Color.RED));
		// Set layout based on the screen width
        scene.widthProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.doubleValue() < 1024) {
                // Switch to small screen layout
            	panel1.setBackground(Background.fill(Color.RED));
            } else {
                // Switch to large screen layout
            	panel1.setBackground(Background.fill(Color.BLACK));
            }
        });
	}
	private Region createPanel(Color color) {
        Region panel = new Region();
        panel.setPrefSize(400, 400); // Set the panel size to 400x400
        panel.setBackground(new Background(new BackgroundFill(color, null, null)));
        return panel;
    }
	public Scene getScene() {
		return scene;
	}
}
