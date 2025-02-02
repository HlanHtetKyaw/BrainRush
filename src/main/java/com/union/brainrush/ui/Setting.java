package com.union.brainrush.ui;

import java.util.ArrayList;
import java.util.Enumeration;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import purejavacomm.CommPortIdentifier;

@Component
@Lazy
public class Setting {
	Scene root;
	Stage popup;
	StackPane mainlayout;
	VBox vBox;
	
	Label label;
	public static ComboBox<String> comboBox;
	
	public void showSetting(StackPane mainroot) {
		popup = new Stage();
		mainlayout = new StackPane();
		root = new Scene(mainlayout,300,100);
		vBox = new VBox(10);
		vBox.setAlignment(Pos.CENTER);
		label = new Label("Choose Port");
		
		comboBox = new ComboBox<>();
		ObservableList<String> ports = getPortList();
		comboBox.setItems(ports);
		if (!comboBox.getItems().isEmpty()) {
		    comboBox.setValue(comboBox.getItems().get(0));
		}
		vBox.getChildren().addAll(label,comboBox);
		mainlayout.getChildren().add(vBox);
		
		popup.setResizable(false);
		popup.setOnCloseRequest(e->{
			comboBox.setValue(comboBox.getValue());
		});
		popup.initModality(Modality.WINDOW_MODAL);
        popup.initOwner(mainroot.getScene().getWindow());
        popup.setTitle("Set Up");
		popup.setScene(root);
		popup.showAndWait();
	}
	private ObservableList<String> getPortList() {
		ObservableList<String> ports = FXCollections.observableArrayList();
		try {
            Enumeration<CommPortIdentifier> portList = CommPortIdentifier.getPortIdentifiers();
            
            while (portList.hasMoreElements()) {
                CommPortIdentifier portId = portList.nextElement();
                System.out.println("Port Name: " + portId.getName());
                ports.add(portId.getName());
                if (portId.isCurrentlyOwned()) {
                    System.out.println("Currently Owned By: " + portId.getCurrentOwner());
                } else {
                    System.out.println("Currently Available");
                }
                
                System.out.println("----------------------");
            }
        } catch (Exception e) {
            System.out.println("Error listing ports: " + e.getMessage());
        }
		return ports;
	}
}
