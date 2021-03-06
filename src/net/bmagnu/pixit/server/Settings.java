package net.bmagnu.pixit.server;

import java.nio.file.Files;
import java.nio.file.Paths;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Settings {
	
	private static boolean loadFromFile = true;
	
	public static final boolean ALLOW_EMPTY_THEME;
	public static final int PORT_SERVER;
	public static final short NUM_PLAYERS_TO_START;
	
	public static final short POINTS_CORRECT_GUESS;
	public static final short POINTS_GOOD_CZAR;
	public static final short POINTS_GUESSED;
	
	public static final short MIN_CZAR_DELTA;
	public static final short IMAGE_COUNT;
	
	public static final short POST_ROUND_WAIT;
	
	public static final boolean OPEN_PORT_UPNP;
	
	public static final boolean PLAY_FULL_ROUNDS;
	public static final int MAX_ROUNDS;
	
	static {
		//Load Config from File
		
		boolean ALLOW_EMPTY_THEME_Loc = false;
		int PORT_SERVER_Loc = 43415;
		short NUM_PLAYERS_TO_START_Loc = 4;
		
		short POINTS_CORRECT_GUESS_Loc = 3;
		short POINTS_GOOD_CZAR_Loc = 2;
		short POINTS_GUESSED_Loc = 1;
		
		short MIN_CZAR_DELTA_Loc = 1;
		short IMAGE_COUNT_Loc = 7;
		
		short POST_ROUND_WAIT_Loc = 5000;
		
		boolean OPEN_PORT_UPNP_Loc = false;
		
		boolean PLAY_FULL_ROUNDS_Loc = false;
		int MAX_ROUNDS_Loc = Integer.MAX_VALUE;
		
		if(loadFromFile) {
			try {
				String data = new String(Files.readAllBytes(Paths.get("./config.json")));
				
				JsonObject jsonIn = (JsonObject) JsonParser.parseString(data);
				
				ALLOW_EMPTY_THEME_Loc = jsonIn.get("allowEmptyTheme").getAsBoolean();
				PORT_SERVER_Loc = jsonIn.get("portServer").getAsInt();
				NUM_PLAYERS_TO_START_Loc = jsonIn.get("numPlayers").getAsShort();
				
				POINTS_CORRECT_GUESS_Loc = jsonIn.get("pointsCorrectGuess").getAsShort();
				POINTS_GOOD_CZAR_Loc = jsonIn.get("pointsGoodCzar").getAsShort();
				POINTS_GUESSED_Loc = jsonIn.get("pointsGotGuessed").getAsShort();
				
				MIN_CZAR_DELTA_Loc = jsonIn.get("minimumCzarDelta").getAsShort();
				IMAGE_COUNT_Loc = jsonIn.get("imagesPerPlayer").getAsShort();
				
				POST_ROUND_WAIT_Loc = jsonIn.get("postRoundWait").getAsShort();
				
				OPEN_PORT_UPNP_Loc = jsonIn.get("openPortUPnP").getAsBoolean();
				
				PLAY_FULL_ROUNDS_Loc = jsonIn.get("playFullRounds").getAsBoolean();
				MAX_ROUNDS_Loc = jsonIn.get("maxRounds").getAsInt();
			} catch(Exception e) {
				System.out.println("Could not Load Config File, assuming Defaults");
			}
		}
		
		ALLOW_EMPTY_THEME = ALLOW_EMPTY_THEME_Loc;
		PORT_SERVER = PORT_SERVER_Loc;
		NUM_PLAYERS_TO_START = NUM_PLAYERS_TO_START_Loc;
			
		POINTS_CORRECT_GUESS = POINTS_CORRECT_GUESS_Loc;
		POINTS_GOOD_CZAR = POINTS_GOOD_CZAR_Loc;
		POINTS_GUESSED = POINTS_GUESSED_Loc;
		
		MIN_CZAR_DELTA = MIN_CZAR_DELTA_Loc;
		IMAGE_COUNT = IMAGE_COUNT_Loc; 
		
		POST_ROUND_WAIT = POST_ROUND_WAIT_Loc;
		
		OPEN_PORT_UPNP = OPEN_PORT_UPNP_Loc;
		
		PLAY_FULL_ROUNDS = PLAY_FULL_ROUNDS_Loc;
		MAX_ROUNDS = MAX_ROUNDS_Loc;
	}
	
	
}
