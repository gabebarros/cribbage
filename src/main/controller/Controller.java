package main.controller;

import main.model.*;
import main.view.View;

public class Controller {
    private Game game;
    private View view;
    private int cribCount = 0;
    private boolean isPlayer1Turn = true;

    public Controller(Game game, View view) {
        this.game = game;
        this.view = view;
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
                    playCardToStack(); // begin card play phase
                }
            }
        });
    }
    
    public void playCardToStack() {
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
    
    
}
