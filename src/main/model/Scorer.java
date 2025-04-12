package main.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Scorer {

	public Scorer() {
		
	}
	
	public int scoreHand(ArrayList<Card> hand, Card startCard) {
		ArrayList<Card> fullHand = new ArrayList<Card>();
		for (Card c : hand) {
			fullHand.add(c);
		}
		fullHand.add(startCard);
		Collections.sort(fullHand);
		
		int score = 0;
		
		// One for his knob (1 point)
		score += oneForHisKnob(hand, startCard);
		
		
		// Fifteen (2 points per combination)
		int fifteens = countFifteens(fullHand, 0, 0);
		score += (fifteens * 2);
		
		// count pair, 3 of a kind, 4 of a kind
		score += scoreCombinations(hand);
		
		
		return score;
	}
	
	public int oneForHisKnob(ArrayList<Card> hand, Card startCard) {
		for (Card c : hand) {
			if (c.getRank().equals(Rank.JACK) && c.getSuit().equals(startCard.getSuit())) {
				return 1;
			}
		}
		return 0;
	}

	public int countFifteens(ArrayList<Card> cards, int index, int currentSum) {
		if (currentSum == 15) {
			return 1;
		}
		if (index == cards.size() || currentSum > 15) {
			return 0;
		}

		int curVal = cards.get(index).getValue();  // value of the current card

		// recursive call including the current card + excluding the current card
		return countFifteens(cards, index + 1, currentSum + curVal) +
		       countFifteens(cards, index + 1, currentSum);
	}
	
	public int scoreCombinations(ArrayList<Card> hand) {
	    int score = 0;
	    HashMap<Rank, Integer> counts = new HashMap<>();

	    // count number of cards with each rank
	    for (Card c : hand) {
	        if (counts.containsKey(c.getRank())) {
	        	counts.put(c.getRank(), counts.get(c.getRank()) + 1);
	        }
	        else {
	        	counts.put(c.getRank(), 1);
	        }
	    }

	    for (int count : counts.values()) {
	        if (count == 2) {  // pair
	            score += 2; 
	        } 
	        else if (count == 3) { // 3 of a kind
	            score += 6; 
	        } 
	        else if (count == 4) {  // 4 of a kind
	            score += 12; 
	        }
	    }

	    return score;
	}
}
