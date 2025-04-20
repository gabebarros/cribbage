package main.controller;

import main.model.*;
import main.view.View;

public class Controller {
    private Game game;
    private View view;
    private int cribCount = 0;

    public Controller(Game game, View view) {
        this.game = game;
        this.view = view;
        game.addObserver(view);
        view.setController(this);
    }

    public Game getGame() {
        return game;
    }

    public void showHands() {
        view.updatePlayerHand(game.getPlayer1(), view.getPlayer1Panel(), e -> {
            int index = Integer.parseInt(e.getActionCommand());
            if (cribCount < 2) {
                game.addToCrib(game.getPlayer1().playCard(index));
                cribCount++;
                showHands();
            }
        });

        view.updatePlayerHand(game.getPlayer2(), view.getPlayer2Panel(), e -> {
            int index = Integer.parseInt(e.getActionCommand());
            if (cribCount >= 2 && cribCount < 4) {
                game.addToCrib(game.getPlayer2().playCard(index));
                cribCount++;
                showHands();

                if (cribCount == 4) {
                    game.drawStarterCard(); // trigger observer to update starter card
                }
            }
        });
    }
    
    
}
