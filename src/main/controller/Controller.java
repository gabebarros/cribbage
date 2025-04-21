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
                    game.drawStarterCard();
                    game.setPlayer1OriginalHand(new ArrayList<Card>(game.getPlayer1().getHand()));
                    game.setPlayer2OriginalHand(new ArrayList<Card>(game.getPlayer2().getHand()));
                    playCardToStack();
                }
            }
        });
    }

    public void playCardToStack() {
        if (game.getPlayer1().getHand().isEmpty() && game.getPlayer2().getHand().isEmpty()) {
            showPhase();
            return;
        }

        if (isPlayer1Turn) {
            view.updatePlayerHand(game.getPlayer1(), view.getPlayer1Panel(), e -> {
                int index = Integer.parseInt(e.getActionCommand());
                Card playedCard = game.getPlayer1().playCard(index);
                game.playCard(playedCard, game.getPlayer1());
                isPlayer1Turn = false;
                updateHands();
            });

            view.updatePlayerHand(game.getPlayer2(), view.getPlayer2Panel(), e -> {});
        } else {
            view.updatePlayerHand(game.getPlayer2(), view.getPlayer2Panel(), e -> {
                int index = Integer.parseInt(e.getActionCommand());
                Card playedCard = game.getPlayer2().playCard(index);
                game.playCard(playedCard, game.getPlayer2());
                isPlayer1Turn = true;
                updateHands();
            });

            view.updatePlayerHand(game.getPlayer1(), view.getPlayer1Panel(), e -> {});
        }
    }

    private void updateHands() {
        view.updatePlayerHand(game.getPlayer1(), view.getPlayer1Panel(), e -> {});
        view.updatePlayerHand(game.getPlayer2(), view.getPlayer2Panel(), e -> {});
        playCardToStack();
    }

    public void startGame() {
        // Kick off the first round
        startRound();
    }

    private void startRound() {
        // Reset state
        cribCount = 0;
        isPlayer1Turn = true;
        game.resetForNewRound(); // method you need to implement to clear stack, crib, etc.
        game.dealHands();  // assumes this gives each player new cards

        // Start the crib selection phase
        JOptionPane.showMessageDialog(null,
                "Choose 2 of your cards to go to the crib",
                "Crib Phase",
                JOptionPane.INFORMATION_MESSAGE);
        
        playCrib();
    }

    public void showPhase() {
        Card startCard = game.getStartCard();

        // Score each player's hand using copies
        int p1Points = scorer.scoreHand(game.getPlayer1OriginalHand(), startCard);
        int p2Points = scorer.scoreHand(game.getPlayer2OriginalHand(), startCard);
        int cribPoints = scorer.scoreHand(game.getCrib(), startCard);

        game.getPlayer1().addScore(p1Points);
        game.getPlayer2().addScore(p2Points);
        game.getPlayer2().addScore(cribPoints); // assuming player 2 is dealer

        game.updateScore();

        JOptionPane.showMessageDialog(null,
            "Show Phase:\n\n" +
            game.getPlayer1().getName() + "'s hand: " + game.getPlayer1OriginalHand() + "\n" +
            "Starter: " + startCard + "\n" +
            "Points: " + p1Points + "\n\n" +

            game.getPlayer2().getName() + "'s hand: " + game.getPlayer2OriginalHand() + "\n" +
            "Starter: " + startCard + "\n" +
            "Points: " + p2Points + "\n\n" +

            "Crib: " + game.getCrib() + "\n" +
            "Starter: " + startCard + "\n" +
            "Crib Points: " + cribPoints,
            "Show Phase",
            JOptionPane.INFORMATION_MESSAGE
        );

        // Check win condition
        if (game.getPlayer1().getScore() >= 121 || game.getPlayer2().getScore() >= 121) {
            String winner = game.getPlayer1().getScore() >= 121 ? game.getPlayer1().getName() : game.getPlayer2().getName();
            JOptionPane.showMessageDialog(null,
                winner + " wins the game!",
                "Game Over",
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Start next round
        startRound();
    }
}
