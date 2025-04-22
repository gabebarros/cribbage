package main.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Player implements CpuStrategy{
	
	private final String name;
	private ArrayList<Card> hand;
	private int score;

	public Player(String name) {
		this.name = name;
		this.score = 0;
	}
	
	public void addScore(int addedScore) {
		this.score += addedScore;
	}
	
	public void setHand(ArrayList<Card> cards) {
		this.hand = new ArrayList<Card>();
		
		for (Card c : cards) {
			this.hand.add(c);
		}
    }

    public ArrayList<Card> getHand() {
        ArrayList<Card> copyHand = new ArrayList<Card>();
        
        for (Card c : hand) {
        	copyHand.add(c);
        }
        
        return copyHand;
    }
    
    public Card playCard(int index) {
    	Card c = this.hand.remove(index);
    	
    	return c;
    }
    
    public int getScore() {
        return score;
    }

    public String getName() {
        return name;
    }
    
    public void sortHand() {
    	Collections.sort(hand);
    }

	@Override
	public Card makeRandomMove() {
		int index = new Random().nextInt(this.getHand().size());
    	return this.playCard(index);
		
	}

	@Override
	public Card makeSmartMove(ArrayList<Card> playStack) {
		Scorer s = new Scorer();
		int optimalScore = 0;
		int optimalIndex = new Random().nextInt(this.getHand().size());
		
		for (int i = 0; i < this.hand.size(); i++) {
			playStack.add(hand.get(i));
			if (s.scorePlayStack(playStack) > optimalScore) {
				optimalIndex = i;
			}
			playStack.remove(playStack.size() - 1);
		}
		
		return this.playCard(optimalIndex);
		
	}

}
