/*
 * Functionality:
 * 		class for scoring, which awards points to players
 */
package main.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Scorer {

	//empty constructor. no atts to set.
	public Scorer() {}
	
	/*
	 * method to assess the value of any hand
	 * @param hand:
	 * 		the hand in question
	 * @param startCard:
	 * 		the startcard
	 */
	public int scoreHand(ArrayList<Card> hand, Card startCard) {
		ArrayList<Card> fullHand = new ArrayList<Card>(hand);
		fullHand.add(startCard); //add startcard to hand
		Collections.sort(fullHand); //sort for score checks
		
		int score = 0;
		
		// One for his knob (1 point)
		score += oneForHisKnob(hand, startCard);
		
		
		// Fifteen (2 points per combination)
		int fifteens = countFifteens(fullHand, 0, 0);
		score += (fifteens * 2);
		
		// count pair, 3 of a kind, 4 of a kind
		score += scoreCombinations(fullHand);
		
		// check for run, 1 point for each card in the run
		score += run(fullHand);
		
		//check for flush
		score += flush(startCard, fullHand);
		
		//return total
		return score;
	}
	
	/*
	 * method to check playstack for unique combinations worth points
	 * @param playStack:
	 * 		the stack to be analyzed.
	 */
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
		//check for royal pair
		else if (playstack_pairRoyal(playStack) == 6) {
			score += 6;
		}
		//check for other pairs
		else if (playstack_pair(playStack) == 2) {
			score += 2;
		}
		
		//check for runs
		score += playstack_Run(playStack);
		
		//return total
		return score;
	}
	
	/*
	 * check for oneForHisKnob scoring method
	 * @param hand:
	 * 		hand to analyze
	 * @param startCard:
	 * 		starting card
	 */
	public int oneForHisKnob(ArrayList<Card> hand, Card startCard) {
		for (Card c : hand) {
			if (c.getRank().equals(Rank.JACK) && c.getSuit().equals(startCard.getSuit())) { //one for his knob!
				return 1;
			}
		}
		return 0;
	}
	
	/*
	 * method to check for twoForHisHeels scoring method.
	 * @param startCard:
	 * 		starting card
	 */
	public int twoForHisHeels(Card startCard) {
		if (startCard.getRank().equals(Rank.JACK)) {
			return 2; //two for his heels!
		}
		return 0;
		
	}

	/*
	 * check for any combinations of cards equalling 15. recursive
	 * @param cards:
	 * 		cards to check
	 * @param index:
	 * 		index to start from
	 * @param currentSum:
	 * 		current sum of card values
	 */
	public int countFifteens(ArrayList<Card> cards, int index, int currentSum) {
		//points if true
		if (currentSum == 15) {
			return 1;
		}
		//base case
		if (index == cards.size() || currentSum > 15) {
			return 0; 
		}

		int curVal = cards.get(index).getValue();  // value of the current card

		// recursive call including the current card + excluding the current card
		return countFifteens(cards, index + 1, currentSum + curVal) +
		       countFifteens(cards, index + 1, currentSum);
	}
	
	/*
	 * method for scoring possible combinations of cards
	 * @param hand:
	 * 		hand to analyze
	 */
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
	
	/*
	 * method to check for runs in a hand
	 * @param hand:
	 * 		hand to analyze
	 */
	public int run(ArrayList<Card> hand) {
	    int score = 0;
	    int run = 1;
	    
	    for (int i = 0; i < hand.size()-1; i++) {
	    	if (hand.get(i + 1).getRank().getObjectiveValue() == hand.get(i).getRank().getObjectiveValue() + 1 ) { //increase run if next card is next ordinal
	    		run++;
	    	}
	    	else {
	    		run = 1; //reset run
	    	}
	    	
	    	if (run == 3 || run == 4 || run == 5) { //triple, quadruple, pentuple runs
	    		score = run;
	    	}
	    }
	    
	    //return points to award
	    return score;
	}
	
	/*
	 * method to check for a flush in a hand
	 * @param startCard:
	 * 		the starting card
	 * @param hand:
	 * 		hand to analyze
	 */
	public int flush(Card startCard, ArrayList<Card> hand) {
		int score = 0;
		
		//flush checks
		if (hand.get(0).getSuit() == hand.get(1).getSuit() &&
			hand.get(1).getSuit() == hand.get(2).getSuit() &&
			hand.get(2).getSuit() == hand.get(3).getSuit()) {
			score += 4;
			if (startCard.getSuit() == hand.get(0).getSuit()) {
				score += 1;
			}
		}
		
		//return points to award
		return score;
	}
	
	/*
	 * method to check for total sum of playStack
	 * @param playStack:
	 * 		playStack, the stack of cards in play
	 */
	public int playstack_sum(ArrayList<Card> playStack) {
		int total = 0;
		
		//sum all cards in stack
		for (Card c : playStack) {
			total += c.getValue();
		}
		
		//return total
		return total;
	}
	
	/*
	 * method to check for pair in playStack
	 * @param playStack:
	 * 		playStack to analyze
	 */
	public int playstack_pair(ArrayList<Card> playStack) {
		//check if impossible
		if (playStack.size() < 2) {
			return 0;
		}
		
		//check for pair
		if (playStack.get(playStack.size()-1).getRank() == playStack.get(playStack.size()-2).getRank()) {
			return 2;
		}
		
		//zero otherwise
		return 0;
	}
	
	/*
	 * method for checking if playstack contains a royal pair
	 * @param playStack:
	 * 		playStack to analyze
	 */
	public int playstack_pairRoyal(ArrayList<Card> playStack) {
		//check for impossible
		if (playStack.size() < 3) {
			return 0;
		}
		
		//check for pair
		if (playStack.get(playStack.size()-1).getRank() == playStack.get(playStack.size()-2).getRank() &&
			playStack.get(playStack.size()-2).getRank() == playStack.get(playStack.size()-3).getRank()) {
			return 6;
		}
		
		//zero otherwise
		return 0;
	}
	
	/*
	 * method for checking for double royal pair
	 * @param playStack:
	 * 		playStack to analyze
	 */
	public int playstack_pairDoubleRoyal(ArrayList<Card> playStack) {
		//check for impossible
		if (playStack.size() < 4) {
			return 0;
		}
		
		//check for pair
		if (playStack.get(playStack.size()-1).getRank() == playStack.get(playStack.size()-2).getRank() &&
			playStack.get(playStack.size()-2).getRank() == playStack.get(playStack.size()-3).getRank() &&
			playStack.get(playStack.size()-3).getRank() == playStack.get(playStack.size()-4).getRank()){
			return 12;
		}
		
		//zero points otherwise
		return 0;
	}
	
	/*
	 * method for checking for a run on the playStack
	 * @param playStack:
	 * 		playStack to analyze
	 */
	public int playstack_Run(ArrayList<Card> playStack) {
		//check for impossible
		if (playStack.size() < 3) {
			return 0;
		}
		
	    int maxRun = 0;

	    // check all runs starting at 3
	    for (int curRun = 3; curRun <= playStack.size(); curRun++) {
	        List<Card> subList = playStack.subList(playStack.size() - curRun, playStack.size());

	        //check if valid
	        if (playstack_RunHelper(subList)) {
	            maxRun = curRun;
	        }
	        else {
	        	return maxRun;
	        }
	    }

	    return maxRun;
	}
	
	/*
	 * method for checking validity of a run, eliminates duplicates
	 * @param cards:
	 * 		card list to analyze
	 */
	private boolean playstack_RunHelper(List<Card> cards) {
	    HashMap<Integer, Integer> map = new HashMap<>();
	    //check if run is in table
	    for (Card c : cards) {
	        int value = c.getRank().getObjectiveValue();
	        
	        if (!map.containsKey(value)) {
	        	map.put(value, 1);
	        }
	        else {
	        	map.put(value, map.get(value) + 1);
	        }
	        
	    }

	    // check for duplicates in the run
	    for (int count : map.values()) {
	        if (count > 1) {
	            return false;
	        }
	    }

	    // check for consecutive run
	    List<Integer> sortedValues = new ArrayList<>(map.keySet());
	    Collections.sort(sortedValues);

	    for (int i = 1; i < sortedValues.size(); i++) {
	        if (sortedValues.get(i) != sortedValues.get(i - 1) + 1) {
	            return false;
	        }
	    }

	    return true;
	}
	
}
