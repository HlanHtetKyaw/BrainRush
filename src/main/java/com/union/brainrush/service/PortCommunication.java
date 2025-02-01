package com.union.brainrush.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.union.brainrush.ui.Question;

import purejavacomm.CommPortIdentifier;
import purejavacomm.SerialPort;

public class PortCommunication implements Runnable {
	private boolean running = true; // Control flag

	public void stopPort() {
		running = false; // Stop reading data
	}

	@Override
	public void run() {
		// Get a list of all available ports
		Enumeration<CommPortIdentifier> portList = CommPortIdentifier.getPortIdentifiers();
//					String line = "AR:{f:[0],s:[A],t:[0]}";
////		            while ((line = reader.readLine()) != null)
//		                System.out.println("Received: " + line);
//
//		                // Step 1: Check if the message starts with "AR:"
//		                if (line.startsWith("AR:")) {
//		                    System.out.println("Valid AR message detected!");
//
//		                    // Step 2: Extract values from f[], s[], t[]
//		                    String fValue = extractValue(line, "f");
//		                    String sValue = extractValue(line, "s");
//		                    String tValue = extractValue(line, "t");
//
//		                    // Step 3: Check if any value contains "0"
//		                    boolean containsZero = fValue.contains("0") || sValue.contains("0") || tValue.contains("0");
//
//		                   System.out.println(fValue);
//		                   System.out.println(sValue);
//		                   System.out.println(tValue);
//		                } else {
//		                    System.out.println("Ignoring non-AR message.");
//		                }

		try {
			// Replace with your actual port name
			String portName = "COM3";
			CommPortIdentifier portId = CommPortIdentifier.getPortIdentifier(portName);

			// Open the port
			SerialPort serialPort = (SerialPort) portId.open("SerialReader", 2000);
			serialPort.setSerialPortParams(115200, SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
					SerialPort.PARITY_NONE);
			String message = "PC:{choice:" + "True" + "}\n";
			OutputStream outputStream = serialPort.getOutputStream();
			outputStream.write(message.getBytes()); // Send data to Arduino
			outputStream.flush();
			// Use BufferedReader to read complete lines
			BufferedReader reader = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));

			System.out.println("Listening for messages...");
			String line;
			while (running&&(line = reader.readLine()) != null) {
				System.out.println("Received: " + line);

				// Step 1: Check if the message starts with "AR:"
				if (line.startsWith("AR:")) {

					// Step 2: Extract values from f[], s[], t[]
					String fValue = extractValue(line, "f");
					String sValue = extractValue(line, "s");
					String tValue = extractValue(line, "t");

					// Step 3: Check if any value contains "0"
//			                    boolean containsZero = fValue.contains("0") || sValue.contains("0") || tValue.contains("0");

					if (!fValue.contains("0")) {
						String fAnswer = extractValue(line, "f");
						Player.fPlayerAns = fAnswer;
						Question.actionButton.fire();
					} else {

					}
				} else {
					System.out.println("Ignoring non-AR message.");
				}
			}
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}
	}

	// Extract value inside f:[], s:[], or t:[]
	private static String extractValue(String line, String key) {
		String pattern = key + ":\\[(.*?)\\]";
		Matcher matcher = Pattern.compile(pattern).matcher(line);
		return matcher.find() ? matcher.group(1) : "";
	}
}
