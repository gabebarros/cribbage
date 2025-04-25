/*
 * Functionality:
 * 		definition for Rank enum for card
 */

package main.model;

public enum Rank{

	//set rank values
	ACE(1), TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6),
	SEVEN(7), EIGHT(8), NINE(9), TEN(10),
	JACK(10), QUEEN(10), KING(10);
	
	private int value;
	
	//Constructor to set value
	//@param value: value to set
	Rank(int value) {
	  this.value = value;
	
	}
	
	//Standard getter
	public int getValue() {
	  return value + 0;//no reference
	
	}
	
	//getter for royal cards values
	public int getObjectiveValue() {
		if (this == JACK) {
			return 11;
		}
		else if(this == QUEEN) {
			return 12;
		}
		else if(this == KING) {
			return 13;
		}
		
		return this.getValue(); //no reference
	}

}