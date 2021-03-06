package net.bmagnu.pixit.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javafx.application.Platform;

public class ServerConnection extends Thread {
	
	private String serverIp;
	private int port;
	private volatile boolean shouldClose = false;
	private volatile boolean requireResponse = false;
	private Socket clientSocket;
	private PrintWriter out;
	private BufferedReader in;
	private ServerHandler handler = new ServerHandler();
	public int playerId = -1;
	
	public BlockingDeque<String> messages = new LinkedBlockingDeque<>();
	
	public ServerConnection(String serverIp, int port) {
		this.serverIp = serverIp;
		this.port = port;
	}
	
	public void shutdownSocket() {
		shouldClose = true;
		try {
			if(clientSocket != null)
				clientSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public synchronized void send(String toSend) {
		System.out.println("Sent message to Server");
		out.println(toSend);
	}
	
	public synchronized JsonObject sendWaitForResponse(String toSend) throws InterruptedException {
		requireResponse = true;
		send(toSend);
		String response;
		
		boolean badResponse = true;
		JsonObject json;
		
		do {
			response = messages.takeFirst();
			json = (JsonObject) JsonParser.parseString(response);
			
			if(json.has("id") && json.has("data")) {
				handler.handleRecieveMessage(json);
			}
			else
				badResponse = false;
			
		} while(badResponse);
		
		System.out.println("Got Response");
		requireResponse = false;
		return json;
	}
	
	private void handleMessages() {
		String message;
		while((message = messages.pollFirst()) != null) {
			JsonObject jsonIn = (JsonObject) JsonParser.parseString(message);
			handler.handleRecieveMessage(jsonIn);
		}
	}
	
	@Override
	public void run() {
		try {
			System.out.println("Opening Socket");
			
			clientSocket = new Socket(serverIp, port);
			
			System.out.println("Socket Open");
			
			out = new PrintWriter(clientSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			
			String lineIn;
			while(!shouldClose && (lineIn = in.readLine()) != null) {
				System.out.println("Got message from Server");
				
				messages.putLast(lineIn);
				
				if(!requireResponse)
					Platform.runLater(() -> handleMessages());
			}
			
			in.close();
	        out.close();
	        clientSocket.close();
		} catch (IOException | InterruptedException e) {
			if(!shouldClose)
				e.printStackTrace();
			else {
				System.out.println("Shut down Socket");
			}
		}
	}
}