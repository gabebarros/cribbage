package main.model;

public enum Rank{

	ACE(1), TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6),
	SEVEN(7), EIGHT(8), NINE(9), TEN(10),
	JACK(10), QUEEN(10), KING(10);
	
	private int value;
	
	Rank(int value) {
	  this.value = value;
	
	}
	
	public int getValue() {
	  return value;
	
	}
	
	public int getObjectiveValue() {
		if (this == JACK) {
			return 11;
		}
		if(this == QUEEN) {
			return 12;
		}
		if(this == KING) {
			return 13;
		}
		
		return this.getValue();
	}

}