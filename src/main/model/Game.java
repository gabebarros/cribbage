package main.model;

import java.util.ArrayList;
import java.util.List;

public class Game {
	//players
    private final Player player1;
    private final Player player2;
    
    //deck
    private Deck deck;
    
    //card stacks
    private ArrayList<Card> crib;
    private ArrayList<Card> playStack;
    private ArrayList<Card> player1OriginalHand;
    private ArrayList<Card> player2OriginalHand;
    
    //start card
    private Card startCard;
    
    //scorer instance
    private Scorer scorer;
    
    //which player is dealer
    private Player dealer;
    
    //wins trackers
    private int player1Wins = 0;
    private int player2Wins = 0;
    
    //model observers --observer design pattern
    private List<GameObserver> observers = new ArrayList<>();

    /*
     * The constructor. Establishes the data of the game.
     * @param p1Name:
     * 		player 1 name
     * @param p2Name:
     * 		player 2 name
     */
    public Game(String p1Name, String p2Name) {
    	//all fresh instances
        this.player1 = new Player(p1Name);
        this.player2 = new Player(p2Name);
        this.deck = new Deck();
        this.crib = new ArrayList<>();
        this.playStack = new ArrayList<>();
        this.scorer = new Scorer();
    }

    // method for "dealing" the hands of both players
    public void dealHands() {
        deck.shuffle(); //shuffle deck before hand
        //6 cards each
        player1.setHand(deck.getCards(6));
        player2.setHand(deck.getCards(6));
        //organize hands for neatness
        player1.sortHand();
        player2.sortHand();
    }

    //Standard getter
    public Player getPlayer1() { 
    	return player1;
    }
    
    //Standard getter
    public Player getPlayer2() { 
    	return player2;
    }
    
    //Standard getter
    public ArrayList<Card> getCrib() {
    	return crib;
    }
    
    //Standard getter
    public Card getStartCard() {
    	return startCard; 
    }

    /*
     * method for adding a card to the crib
     * @param card:
     * 		card to add
     */
    public void addToCrib(Card card) {
        crib.add(card);
        notifyCribUpdated(); //notify observers (view)
    }

    //method for drawing starter card
    public void drawStarterCard() {
        this.startCard = deck.draw(); //draw a single card
        notifyStarterCardDrawn(); //notify view through controller
    }
    
    //Standard setter
    public void setPlayer1OriginalHand(ArrayList<Card> hand) {
        this.player1OriginalHand = hand;
    }

    //Standard setter
    public void setPlayer2OriginalHand(ArrayList<Card> hand) {
        this.player2OriginalHand = hand;
    }

    //standard getter
    public ArrayList<Card> getPlayer1OriginalHand() {
        return player1OriginalHand;
    }

    //standard getter
    public ArrayList<Card> getPlayer2OriginalHand() {
        return player2OriginalHand;
    }

    //add observer method, standard for observer dp
    public void addObserver(GameObserver observer) {
        observers.add(observer);
    }

    //notifier
    private void notifyCribUpdated() {
        for (GameObserver o : observers) {
            o.onCribUpdated();
        }
    }

    //notifier
    private void notifyStarterCardDrawn() {
        for (GameObserver o : observers) {
            o.onStarterCardDrawn(startCard);
        }
    }
    
    //notifier
    private void notifyPlayStackUpdated() {
        for (GameObserver o : observers) {
            o.onPlayStackUpdated(playStack);
        }
    }
    
    /*
     * method for playing a card
     * @param card:
     * 		card to be played
     * @param player:
     * 		player to play card
     */
    public void playCard(Card card, Player player) {
    	//verify stack total doesn't exceed 31
    	if (scorer.playstack_sum(playStack) + card.getValue() > 31 && scorer.playstack_sum(playStack) != 31) {
    		playStack.clear();
    		
    		//check player
    		if (player == player1) {
    			player2.addScore(1);
    		}
    		else {
    			player1.addScore(1);
    		}
    	}
    	
    	//clear if == 31
    	if (scorer.playstack_sum(playStack) == 31) {
    		playStack.clear();
    	}
    	
    	//now add card and update score
        playStack.add(card);
        int score = scorer.scorePlayStack(playStack);
        player.addScore(score);
        
        //notify/update
        updateScore();
        notifyPlayStackUpdated();
    }
    
    //notifier
    public void updateScore() {
    	for (GameObserver o : observers) {
            o.onScoreUpdated(player1.getScore(), player2.getScore());  // Pass updated scores to observers
        }
    }
    
    //reset, clears all necessary fields
    public void resetForNewRound() {
        this.deck = new Deck();
        this.crib.clear();
        this.playStack.clear();
        this.startCard = null;
        
        //notify for visual update
        notifyPlayStackUpdated();
    }
    
    //full reset, clears ALL fields
    public void resetForNewGame() {
        this.deck = new Deck();
        this.crib.clear();
        this.playStack.clear();
        this.startCard = null;
        this.player1.resetScore();
        this.player2.resetScore();
        
        //notify/update
        updateScore();
        notifyPlayStackUpdated();
    }
    
    //Standard setter
    public void setDealer(Player p) {
    	this.dealer = p;
    }
    
    //Standard getter
    public Player getDealer() {
    	return this.dealer;
    }

    //Standard getter
    public ArrayList<Card> getPlayStack() {
    	return new ArrayList<Card>(playStack); //no escaping reference
    }
    
    //Standard getter
    public int getPlayer1Wins() { 
    	return player1Wins + 0; //no reference
    }
    
    //Standard getter
    public int getPlayer2Wins() { 
    	return player2Wins + 0; //no reference
    }
    
    //increment wins on the model
    public void incrementPlayer1Wins() {
        player1Wins++;
    }
    
    //increment wins on the model
    public void incrementPlayer2Wins() {
        player2Wins++;
    }

}
