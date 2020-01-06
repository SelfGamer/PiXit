package net.bmagnu.pixit.client;

import java.io.IOException;
import java.util.Optional;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import net.bmagnu.pixit.common.GameState;

public class Client extends Application {

	private ServerConnection connection;
	
	public ServerProxy proxy;
	
	public GUIController controller;
	
	public GameState state;
	
	public static Client instance;
	
	public static void main(String[] args) {
		launch(args);
	}
	
	private void initGUI(Stage stage) throws IOException {
		instance = this;
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("main_gui.fxml"));
		
		Parent root = loader.load();
	    
        Scene scene = new Scene(root, 1600, 900);
    
        stage.setTitle("FXML Welcome");
        stage.setScene(scene);
        stage.show();
        
        controller = loader.getController();
        
        scene.getWindow().addEventFilter(WindowEvent.WINDOW_HIDING, event -> {
    		connection.shutdownSocket();
    	});
        
        controller.initialize();
	}
	
	private void initGame(String serverIp) {
		connection = new ServerConnection(serverIp);
		proxy = new ServerProxy(connection);
		
		connection.start();
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		System.out.println("Registering at Server...");
		connection.playerId = proxy.registerPlayer();
		System.out.println("Player Id: " + connection.playerId);
	}

	
	@Override
	public void start(Stage primaryStage) throws Exception {
		initGUI(primaryStage);
		
		String ip = queryIp();
		
		if(ip.isEmpty()) {
			Platform.exit();
		}
		else {		
			initGame(ip);
		}
	}

	private String queryIp() {
		TextInputDialog dialog = new TextInputDialog("127.0.0.1");
		dialog.setTitle("Server IP");
		dialog.setHeaderText("Connecting to Server...");
		dialog.setContentText("Please enter the Server IP:");

		Optional<String> result = dialog.showAndWait();

		String ip = "";
		
		if (result.isPresent()){
		    ip = result.get();
		}
		return ip;
	}
}
