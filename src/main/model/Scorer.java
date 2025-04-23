package main.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

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
		score += scoreCombinations(fullHand);
		
		// check for run, 1 point for each card in the run
		score += run(fullHand);
		
		
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
	
	public int twoForHisHeels(Card startCard) {
		if (startCard.getRank().equals(Rank.JACK)) {
			return 2;
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
	
	// hand must be sorted before calling
	public int run(ArrayList<Card> hand) {
	    int score = 0;
	    int run = 1;
	  
	    for (int i = 0; i < hand.size()-1; i++) {
	    	if (hand.get(i + 1).getRank().getObjectiveValue() == hand.get(i).getRank().getObjectiveValue() + 1 ) {
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
	
	public int flush(Card startCard, ArrayList<Card> hand) {
		int score = 0;
		
		if (hand.get(0).getSuit() == hand.get(1).getSuit() &&
			hand.get(1).getSuit() == hand.get(2).getSuit() &&
			hand.get(2).getSuit() == hand.get(3).getSuit()) {
			score += 4;
			if (startCard.getSuit() == hand.get(0).getSuit()) {
				score += 1;
			}
		}
		
		return score;
	}
	
	public int playstack_sum(ArrayList<Card> playStack) {
		int total = 0;
		
		for (Card c : playStack) {
			total += c.getValue();
		}
		
		return total;
	}
	
	public int playstack_pair(ArrayList<Card> playStack) {
		if (playStack.size() < 2) {
			return 0;
		}
		
		if (playStack.get(playStack.size()-1).getRank() == playStack.get(playStack.size()-2).getRank()) {
			return 2;
		}
		
		return 0;
	}
	
	public int playstack_pairRoyal(ArrayList<Card> playStack) {
		if (playStack.size() < 3) {
			return 0;
		}
		
		if (playStack.get(playStack.size()-1).getRank() == playStack.get(playStack.size()-2).getRank() &&
			playStack.get(playStack.size()-2).getRank() == playStack.get(playStack.size()-3).getRank()) {
			return 6;
		}
		
		return 0;
	}
	
	public int playstack_pairDoubleRoyal(ArrayList<Card> playStack) {
		if (playStack.size() < 4) {
			return 0;
		}
		
		if (playStack.get(playStack.size()-1).getRank() == playStack.get(playStack.size()-2).getRank() &&
			playStack.get(playStack.size()-2).getRank() == playStack.get(playStack.size()-3).getRank() &&
			playStack.get(playStack.size()-3).getRank() == playStack.get(playStack.size()-4).getRank()){
			return 12;
		}
		
		return 0;
	}
	
	public int playstack_Run(ArrayList<Card> playStack) {
		if (playStack.size() < 3) {
			return 0;
		}
		
	    int maxRun = 0;

	    // check all runs starting at 3
	    for (int curRun = 3; curRun <= playStack.size(); curRun++) {
	        List<Card> subList = playStack.subList(playStack.size() - curRun, playStack.size());

	        if (playstack_RunHelper(subList)) {
	            maxRun = curRun;
	        }
	        else {
	        	return maxRun;
	        }
	    }

	    return maxRun;
	}
	
	private boolean playstack_RunHelper(List<Card> cards) {
	    HashMap<Integer, Integer> map = new HashMap<>();
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
