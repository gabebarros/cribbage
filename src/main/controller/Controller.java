package main.controller;

import java.util.ArrayList;
import java.util.Random;

import javax.swing.JOptionPane;

import main.model.*;
import main.view.View;

public class Controller {
    private Game game;
    private View view;
    private Scorer scorer;
    private int cribCount = 0;
    private boolean isPlayer1Turn = true;
    private GameMode gamemode;
   // private boolean cpuMode;
   // private boolean difficulty;

    public Controller(Game game, View view, GameMode gamemode) {
        this.game = game;
        this.view = view;
        this.scorer = new Scorer();
        game.addObserver(view);
        view.setController(this);
        this.gamemode = gamemode;
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
        }, gamemode);

        if (gamemode == GameMode.PVP) {
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
            }, gamemode);
        }
        else {
        	view.updatePlayerHand(game.getPlayer2(), view.getPlayer2Panel(), null, gamemode);
        	int index = new Random().nextInt(game.getPlayer2().getHand().size());
            if (cribCount >= 2 && cribCount < 4) {
            	new javax.swing.Timer(1000, e -> {
            		game.addToCrib(game.getPlayer2().playCard(index));
                    cribCount++;
                    playCrib();

                    if (cribCount == 4) {
                        game.drawStarterCard();
                        game.setPlayer1OriginalHand(new ArrayList<Card>(game.getPlayer1().getHand()));
                        game.setPlayer2OriginalHand(new ArrayList<Card>(game.getPlayer2().getHand()));
                        playCardToStack();
                    }
                    ((javax.swing.Timer) e.getSource()).stop();
            	}).start();
                
            }
        }
        
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
            }, gamemode);
            view.updatePlayerHand(game.getPlayer2(), view.getPlayer2Panel(), e -> {}, gamemode);
        } else {
            if (gamemode == GameMode.CPU_EASY || gamemode == GameMode.CPU_HARD) {
            	new javax.swing.Timer(1000, e -> {
            		//int index = new Random().nextInt(game.getPlayer2().getHand().size());
                	//Card playedCard = game.getPlayer2().playCard(index);
            		Card playedCard = game.getPlayer2().makeSmartMove(game.getPlayStack());
                    game.playCard(playedCard, game.getPlayer2());
                    isPlayer1Turn = true;
                    updateHands();
                    ((javax.swing.Timer) e.getSource()).stop();
            	}).start(); 
            } else {
                view.updatePlayerHand(game.getPlayer2(), view.getPlayer2Panel(), e -> {
                    int index = Integer.parseInt(e.getActionCommand());
                    Card playedCard = game.getPlayer2().playCard(index);
                    game.playCard(playedCard, game.getPlayer2());
                    isPlayer1Turn = true;
                    updateHands();
                }, gamemode);
                view.updatePlayerHand(game.getPlayer1(), view.getPlayer1Panel(), e -> {}, gamemode);
            }
        }
    }

    private void updateHands() {
        view.updatePlayerHand(game.getPlayer1(), view.getPlayer1Panel(), e -> {}, gamemode);
        view.updatePlayerHand(game.getPlayer2(), view.getPlayer2Panel(), e -> {}, gamemode);
        playCardToStack();
    }

    public void startGame() {
        // Kick off the first round
    	game.setDealer(game.getPlayer2());
    	view.updateDealerIndicator(game.getPlayer2());
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
        
        if (game.getDealer() == game.getPlayer2()) {
        	game.getPlayer2().addScore(cribPoints);
        }
        else {
        	game.getPlayer1().addScore(cribPoints);
        }
        

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
        
        game.resetForNewRound();
        view.onCribUpdated();
        view.updateStarterCard(null);

        // Check win condition
        if (game.getPlayer1().getScore() >= 121 || game.getPlayer2().getScore() >= 121) {
            String winner = game.getPlayer1().getScore() >= 121 ? game.getPlayer1().getName() : game.getPlayer2().getName();
            JOptionPane.showMessageDialog(null,
                winner + " wins the game!",
                "Game Over",
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if (game.getDealer() == game.getPlayer2()) {
        	game.setDealer(game.getPlayer1());
        	view.updateDealerIndicator(game.getPlayer1());
        }
        else {
        	game.setDealer(game.getPlayer2());
        	view.updateDealerIndicator(game.getPlayer2());
        }
        
        // Start next round
        startRound();
    }
}