package main.controller;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import main.model.*;
import main.view.View;

public class Controller {
    private Game game;
    private View view;
    private Scorer scorer;
    private int cribCount = 0;
    private boolean isPlayer1Turn = true;

    public Controller(Game game, View view) {
        this.game = game;
        this.view = view;
        this.scorer = new Scorer();
        game.addObserver(view);
        view.setController(this);
    }

    public Game getGame() {
        return game;
    }

    public void playCrib() {
        view.updatePlayerHand(game.getPlayer1(), view.getPlayer1Panel(), e -> {
            int index = Integer.parseInt(e.getActionCommand());
            if (cribCount < 2) {
                game.addToCrib(game.getPlayer1().playCard(index));
                cribCount++;
                playCrib();
            }
        });

        view.updatePlayerHand(game.getPlayer2(), view.getPlayer2Panel(), e -> {
            int index = Integer.parseInt(e.getActionCommand());
            if (cribCount >= 2 && cribCount < 4) {
                game.addToCrib(game.getPlayer2().playCard(index));
                cribCount++;
                playCrib();

                if (cribCount == 4) {
                    game.drawStarterCard(); // trigger observer to update starter card
                    game.setPlayer1OriginalHand(new ArrayList<Card>(game.getPlayer1().getHand()));
                    game.setPlayer2OriginalHand(new ArrayList<Card>(game.getPlayer2().getHand()));
                    playCardToStack(); // begin card play phase
                }
            }
        });
    }
    
    public void playCardToStack() {
    	if (game.getPlayer1().getHand().isEmpty() && game.getPlayer2().getHand().isEmpty()) {
            showPhase();  // Show phase happens once play phase is done
            return;
        }
    	
        if (isPlayer1Turn) {
            view.updatePlayerHand(game.getPlayer1(), view.getPlayer1Panel(), e -> {
                int index = Integer.parseInt(e.getActionCommand());
                Card playedCard = game.getPlayer1().playCard(index);
                game.playCard(playedCard, game.getPlayer1());
                isPlayer1Turn = false;
                updateHands();  // Just update display instead of recursive call
            });

            view.updatePlayerHand(game.getPlayer2(), view.getPlayer2Panel(), e -> {}); // Disable clicks
        } else {
            view.updatePlayerHand(game.getPlayer2(), view.getPlayer2Panel(), e -> {
                int index = Integer.parseInt(e.getActionCommand());
                Card playedCard = game.getPlayer2().playCard(index);
                game.playCard(playedCard, game.getPlayer2());
                isPlayer1Turn = true;
                updateHands();
            });

            view.updatePlayerHand(game.getPlayer1(), view.getPlayer1Panel(), e -> {}); // Disable clicks
        }
    }

    private void updateHands() {
        // Just refreshes the view to show updated hands after a play
        view.updatePlayerHand(game.getPlayer1(), view.getPlayer1Panel(), e -> {});
        view.updatePlayerHand(game.getPlayer2(), view.getPlayer2Panel(), e -> {});

        // Now assign listeners again to allow the current player to click
        playCardToStack();  // Safe, as it doesn't recurse into itself after click
    }
    
    public void showPhase() {
        Card startCard = game.getStartCard();

        // Score each player's hand
        int p1Points = scorer.scoreHand(game.getPlayer1OriginalHand(), startCard);
        int p2Points = scorer.scoreHand(game.getPlayer2OriginalHand(), startCard);

        game.getPlayer1().addScore(p1Points);
        game.getPlayer2().addScore(p2Points);

        // Score the crib (assume player2 is dealer here)
        int cribPoints = scorer.scoreHand(game.getCrib(), startCard);
        game.getPlayer2().addScore(cribPoints);

        game.updateScore();

        JOptionPane.showMessageDialog(null,
        	    "Show Phase:\n\n" +
        	    game.getPlayer1().getName() + "'s hand: " + game.getPlayer1OriginalHand() + "\n" +
        	    "Starter: " + game.getStartCard() + "\n" +
        	    "Points: " + p1Points + "\n\n" +

        	    game.getPlayer2().getName() + "'s hand: " + game.getPlayer2OriginalHand() + "\n" +
        	    "Starter: " + game.getStartCard() + "\n" +
        	    "Points: " + p2Points + "\n\n" +

        	    "Crib: " + game.getCrib() + "\n" +
        	    "Starter: " + game.getStartCard() + "\n" +
        	    "Crib Points: " + cribPoints,
        	    
        	    "Show Phase",
        	    JOptionPane.INFORMATION_MESSAGE
        	);

    }

    
    
}
