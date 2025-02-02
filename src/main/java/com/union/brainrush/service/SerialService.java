package com.union.brainrush.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

import com.union.brainrush.BrainRushApplication;
import com.union.brainrush.ui.Question;

import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import purejavacomm.CommPortIdentifier;
import purejavacomm.SerialPort;

public class SerialService extends Service<Void> {
	private SerialPort serialPort;
	private BufferedReader reader;
	private OutputStream outputStream;
	public static boolean running = true;

	@Override
	protected Task<Void> createTask() {
		return new Task<>() {
			@Override
			protected Void call() {
				try {
					// Replace with your actual port name
					String portName = "COM3";
					CommPortIdentifier portId = CommPortIdentifier.getPortIdentifier(portName);

					// Open the port
					serialPort = (SerialPort) portId.open("SerialReader", 2000);
					serialPort.setSerialPortParams(115200, SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
							SerialPort.PARITY_NONE);

					outputStream = serialPort.getOutputStream();
					reader = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));

					// 🔹 Step 1: Send "PC:{choice:True}" immediately when the service starts
					sendMessage("PC:{choice:True}");

					System.out.println("Listening for messages...");
					String line;

					while (running && (line = reader.readLine()) != null) {
						sendMessage("PC:{choice:True}");
                        System.out.println("Received: " + line);

						// Step 2: Process messages from Arduino
						if (line.startsWith("AR:")) {
							String fValue = extractValue(line, "f");
							String sValue = extractValue(line, "s");
							String tValue = extractValue(line, "t");

							if (!fValue.contains("0")) {
								String fAnswer = extractValue(line, "f");

								Player.fPlayerAns = fAnswer;

								running = false;
								Platform.runLater(() -> {
									Question.actionButton.fire();
								});
							}
						} else {
//                            System.out.println("Ignoring non-AR message.");
						}
					}
				} catch (Exception e) {
					System.err.println("Error: " + e.getMessage());
				}
				return null;
			}
		};
	}

	public void sendMessage(String message) {
		try {
			if (outputStream != null) {
				outputStream.write((message + "\n").getBytes()); // Send data to Arduino
				outputStream.flush();
//                System.out.println("Sent: " + message);
			}
		} catch (IOException e) {
			System.err.println("Error sending message: " + e.getMessage());
		}
	}

	public void stopService() {
		running = false;
		cancel();
		try {
			if (reader != null)
				reader.close();
			if (outputStream != null)
				outputStream.close();
			if (serialPort != null)
				serialPort.close();
		} catch (IOException e) {
			System.err.println("Error closing serial port: " + e.getMessage());
		}
	}

	private String extractValue(String line, String key) {
		String pattern = key + ":\\[(.*?)\\]";
		java.util.regex.Matcher matcher = java.util.regex.Pattern.compile(pattern).matcher(line);
		return matcher.find() ? matcher.group(1) : "";
	}
}
