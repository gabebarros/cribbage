/*
 * Ways to score:
 * 
 */

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
<<<<<<< Updated upstream
		score += scoreCombinations(hand);
=======
		score += scoreCombinations(fullHand);
		
		// check for run, 1 point for each card in the run
		score += run(fullHand);
		
		//check for flush
		score += flush(startCard, fullHand);
		
		
		return score;
	}
	
	public int scorePlayStack(ArrayList<Card> playStack) {
		int score = 0;
		
		// check if the stack's value is equal to 15
		if (playstack_sum(playStack) == 15) {
			score += 2;
		}
		
		// check if the stack's value is equal to 31
		if (playstack_sum(playStack) == 31) {
			score += 2;
		}
		
		// check pair/3 of a kind/ 4 of a kind
		if (playstack_pairDoubleRoyal(playStack) == 12) {
			score += 12;
		}
		else if (playstack_pairRoyal(playStack) == 6) {
			score += 6;
		}
		else if (playstack_pair(playStack) == 2) {
			score += 2;
		}
		
		score += playstack_Run(playStack);
>>>>>>> Stashed changes
		
		
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
	
	// FYI hand must be sorted
	// Also, currently does not count face cards in sequences 
	public int run(ArrayList<Card> hand) {
	    int score = 0;
	    int run = 1;
	    
	    for (int i = 0; i < hand.size()-1; i++) {
	    	if (hand.get(i + 1).getValue() == hand.get(i).getValue() + 1 ) {
	    		run++;
	    	}
	    	else {
	    		run = 1;
	    	}
	    	
	    	if (run == 3 || run == 4 || run == 5) {
	    		score = run;
	    	}
	    }
	    
	    return score;
	}
}
