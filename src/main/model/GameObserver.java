package main.model;

import java.util.ArrayList;

public interface GameObserver {
    void onCribUpdated();
    void onStarterCardDrawn(Card card);
    void onPlayStackUpdated(ArrayList<Card> playStack);
    void onScoreUpdated(int player1Score, int player2Score);
}
