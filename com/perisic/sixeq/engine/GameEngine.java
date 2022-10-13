package com.perisic.sixeq.engine;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;

import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleScriptContext;

/**
 * Main class where the games are coming from. 
 * Basic functionality
 */
public class GameEngine {
	String thePlayer = null;

	/**
	 * Each player has their own game engine.
	 * 
	 * @param player
	 */
	public GameEngine(String player) {
		thePlayer = player;
	
	}

	int counter = 0;
	int score = 0; 
	GameServer theGames = new GameServer(); 
	Game current = null; 
/*
 * Retrieves a game. This basic version only has two games that alternate.
 */
	public URL nextGame() {		
			try {
				current = theGames.getRandomGame();
				return current.getLocation(); 
			} catch (MalformedURLException e) {
				System.out.println("Something went wrong when trying to retrieve game "+counter+"!"); 
				e.printStackTrace();
				return null; 
				
			} 
		
	}

	/**
	 * Checks if the parameter i is a solution to the game URL. If so, score is increased by one. 
	 * @param game
	 * @param i
	 * @return true or false
	 * @throws ScriptException 
	 * @throws FileNotFoundException 
	 */
	public boolean checkSolution(URL game, int i) {

		if (i == current.getSolution()) {
			score++; 
			return true;
		} else {
			return false;
		}
	}


	/**
	 * Retrieves the score. 
	 * 
	 * @param player
	 * @return score
	 */
	public int getScore() {
		return score;
	}
	
	/**
	 * Retrieves the player. 
	 * 
	 * 
	 * @return player
	 */
	public String getPlayer() {
		
		return thePlayer;
	}

}
