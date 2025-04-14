package main.controller;
/*
 * goals: recieve updates from the model, update the view. simple. 
 * use observer pattern would be good approach imo - Lucas
 */

import main.controller.Observer;
import main.model.Game;

public class Controller {
	private Game game;

	public Controller(Game g) {
		this.game = g;
	}
	
	public void addObserver(Observer observer) {
		game.registerObserver(observer);
	}

}
