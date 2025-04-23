/*
 * Functionality:
 * 		Player object, for 1 of 2 players in game
 */

package main.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Player implements CpuStrategy{ //player can be cpu
	
	private final String name; //name cannot change
	private ArrayList<Card> hand;
	private int score;

	/*
	 * The constructor. Sets basic values like name and score.
	 * @param name:
	 * 		name of player
	 */
	public Player(String name) {
		this.name = name;
		this.score = 0;
	}
	
	/*
	 * method for increasing a player's score
	 * @param addedScore:
	 * 		amount to add to current score
	 */
	public void addScore(int addedScore) {
		this.score += addedScore;
	}
	
	/*
	 * method for establishing a player's hand
	 * @param cards:
	 * 		hand to give to player
	 */
	public void setHand(ArrayList<Card> cards) {
		this.hand = new ArrayList<Card>();
		
		for (Card c : cards) { //add all cards to hand
			this.hand.add(c);
		}
		return;
    }

	//Standard getter, reference handled
    public ArrayList<Card> getHand() {
         return new ArrayList<Card>(hand); //return copy to avoid escaping reference
    }
    
    /*
     * method for playing a card from a hand
     * @param index:
     * 		index of card to play
     */
    public Card playCard(int index) {
    	Card c = this.hand.remove(index); //remove card
    	
    	return c; //return played card
    }
    
    //Standard getter
    public int getScore() {
        return score + 0; //no ref
    }
    
    //reset score to zero
    public void resetScore() {
        this.score = 0;
    }

    //return player name
    public String getName() {
        return name; //string immutable
    }
    
    //basic sorter for hand
    public void sortHand() {
    	Collections.sort(hand);
    }

	@Override
	/*
	 * method for easy cpu move *strategy*
	 * overriding interface method def
	 */
	public Card makeRandomMove() {
		int index = new Random().nextInt(this.getHand().size()); //random card index
    	return this.playCard(index); //play card and remove from hand
		
	}

	@Override
	/*
	 * override for makeSmartMove from interface for hard CPU
	 * @param playStack:
	 * 		the cards in the playStack, used for determining optimal move
	 */
	public Card makeSmartMove(ArrayList<Card> playStack) {
		Scorer s = new Scorer();
		int optimalScore = 0;
		int optimalIndex = new Random().nextInt(this.getHand().size());
		
		//search for optimal move
		for (int i = 0; i < this.hand.size(); i++) {
			playStack.add(hand.get(i));
			if (s.scorePlayStack(playStack) > optimalScore) {
				optimalIndex = i;
			}
			playStack.remove(playStack.size() - 1);
		}
		
		//plays the card based on optimal index
		return this.playCard(optimalIndex);
		
	}

}
