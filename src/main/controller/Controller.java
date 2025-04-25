/*
 * Functionality: This class serves as a "middleman" between the model and the view, recieving updates
 * 		from the model, and in turn, updating the view.
 * 
 * AI USED
 */

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
    private boolean gameOver = false;

    /*
     * The constructor, which sets the instances of game and view as well as selecting the gamemode, then
     * 		adds the observer and sets the view's controller to this class instance. 
     *  @param game:
     *  	a game instance, representing the data of the game being played
     *  @param view:
     *  	a view instance, representing the frontend of the game being played
     *  @param gamemode:
     *  	an enum value of either PVP, CPU_EASY, or CPU_Hard, used for game logic
     *  
     */
    public Controller(Game game, View view, GameMode gamemode) {
        this.game = game;
        this.view = view;
        this.scorer = new Scorer();
        game.addObserver(view); //middleman functionality, View class serves as an observer
        view.setController(this); 
        this.gamemode = gamemode;
    }

    // Standard Getter method
    public Game getGame() {
        return game;
    }
    
    /*
     * Method for playing to the crib, with different logic based on the gamemode. Performs actions on the model and view. 
     */
    public void playCrib() {
    	if (gameOver) return; //if game is over, then nothing should happen
    	
    	//update player1 hand no matter what, because there must be at least 1 player
        view.updatePlayerHand(game.getPlayer1(), view.getPlayer1Panel(), e -> {
            int index = Integer.parseInt(e.getActionCommand());
            //logic to ensure player plays 2 cards to crib
            if (cribCount < 2) {
                game.addToCrib(game.getPlayer1().playCard(index));
                cribCount++;
                playCrib();
            }
        }, gamemode);

        // if pvp, then player 2 has the same logic as player 1
        if (gamemode == GameMode.PVP) {
        	//update player hand
        	view.updatePlayerHand(game.getPlayer2(), view.getPlayer2Panel(), e -> {
                int index = Integer.parseInt(e.getActionCommand());
                if (cribCount >= 2 && cribCount < 4) { //checks that enough cards have been played
                    game.addToCrib(game.getPlayer2().playCard(index));
                    cribCount++;
                    //call again if another card must be played 
                    playCrib();

                    if (cribCount == 4) {
                        game.drawStarterCard();
                        
                        //two for his heels (2 points)
                    	game.getDealer().addScore(scorer.twoForHisHeels(game.getStartCard()));
                    	game.updateScore(); 
                        
                        game.setPlayer1OriginalHand(new ArrayList<Card>(game.getPlayer1().getHand()));
                        game.setPlayer2OriginalHand(new ArrayList<Card>(game.getPlayer2().getHand()));
                        playCardToStack();
                    }
                }
            }, gamemode);
        }
        else {
        	// pve modes, with the same logic because difficulty is irrelevant here
        	view.updatePlayerHand(game.getPlayer2(), view.getPlayer2Panel(), null, gamemode);
        	int index = new Random().nextInt(game.getPlayer2().getHand().size());
            if (cribCount >= 2 && cribCount < 4) {
            	new javax.swing.Timer(1000, e -> { //timer for cpu to make moves
            		game.addToCrib(game.getPlayer2().playCard(index)); //add random card to crib
                    cribCount++;
                    //play again if necessary
                    playCrib();

                    if (cribCount == 4) {
                    	//after crib plays
                        game.drawStarterCard();
                        game.setPlayer1OriginalHand(new ArrayList<Card>(game.getPlayer1().getHand()));
                        game.setPlayer2OriginalHand(new ArrayList<Card>(game.getPlayer2().getHand()));
                        playCardToStack();
                    }
                    ((javax.swing.Timer) e.getSource()).stop(); //timer stop
            	}).start();
                
            }
        }
        
    }

    public void playCardToStack() {
    	if (gameOver) return;
        if (game.getPlayer1().getHand().isEmpty() && game.getPlayer2().getHand().isEmpty()) {
            showPhase();
            return;
        }

        if (isPlayer1Turn) {
        	view.player1Turn();
            view.updatePlayerHand(game.getPlayer1(), view.getPlayer1Panel(), e -> {
                int index = Integer.parseInt(e.getActionCommand());
                Card playedCard = game.getPlayer1().playCard(index);
                game.playCard(playedCard, game.getPlayer1());
                isPlayer1Turn = false;
                updateHands();
            }, gamemode);
            view.updatePlayerHand(game.getPlayer2(), view.getPlayer2Panel(), e -> {}, gamemode);
        } else {
        	view.player2Turn();
            if (gamemode == GameMode.CPU_EASY || gamemode == GameMode.CPU_HARD) {
            	new javax.swing.Timer(1000, e -> {
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

    /*
     * method for updating the hands of both players. Gamemode irrelevant
     */
    private void updateHands() {
        view.updatePlayerHand(game.getPlayer1(), view.getPlayer1Panel(), e -> {}, gamemode);
        view.updatePlayerHand(game.getPlayer2(), view.getPlayer2Panel(), e -> {}, gamemode);
        //after hand update, it becomes time for a player to make a move
        playCardToStack();
    }

    /*
     * method to start the game, gamemode irrelevant
     */
    public void startGame() {
        // Kick off the first round
    	game.setDealer(game.getPlayer2()); // player2 is dealer by default
    	view.updateDealerIndicator(game.getPlayer2());
    	setGameOver(false); //obviously, the game isn't over when it starts
        startRound();
    }

    /*
     * method to start a round, gamemode irrlevant
     */
    private void startRound() {
    	if (gameOver) return;
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

    //method for updating scores and displaying round summary
    public void showPhase() {
        Card startCard = game.getStartCard();

        // Score each player's hand using copies
        int p1Points = scorer.scoreHand(game.getPlayer1OriginalHand(), startCard);
        int p2Points = scorer.scoreHand(game.getPlayer2OriginalHand(), startCard);
        int cribPoints = scorer.scoreHand(game.getCrib(), startCard);
        int heelsPoints = scorer.twoForHisHeels(game.getStartCard());
        
        if (heelsPoints > 0) {
        	game.getDealer().addScore(heelsPoints);
        	view.showScoreMessage(game.getDealer().getName() + " scores " + heelsPoints + " for His Heels!");
        }
        //update scores in model
        game.getPlayer1().addScore(p1Points);
        game.getPlayer2().addScore(p2Points);
        
        if (game.getDealer() == game.getPlayer2()) {
        	game.getPlayer2().addScore(cribPoints);
        }
        else {
        	game.getPlayer1().addScore(cribPoints);
        }

        //finish updates
        game.updateScore();
        
        if (gameOver) return;

        // end of round summary
        view.showSummary(startCard, p1Points, p2Points, cribPoints);
        
        // reset
        game.resetForNewRound();
        view.onCribUpdated();
        view.updateStarterCard(null);

        //switch dealers
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
    
    //Standard getter
    public boolean isGameOver() {
        return gameOver;
    }

    //Standard setter
    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

}