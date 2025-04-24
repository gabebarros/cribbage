/*
 * Functionality:
 * 		interface for observers
 */

package main.model;

import java.util.ArrayList;

//interface def
public interface GameObserver {
	
	// 4 abstract methods
    void onCribUpdated();
    void onStarterCardDrawn(Card card);
    void onPlayStackUpdated(ArrayList<Card> playStack);
    void onScoreUpdated(int player1Score, int player2Score);
}
