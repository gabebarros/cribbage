/*
 * Functionality: 
 * 		Serves as an interface for move cpu move strategy, containing 2 abstract methods.
 */

package main.model;

import java.util.ArrayList;

public interface CpuStrategy { //strategy design pattern
	
	//abstract methods
	Card makeRandomMove();
    Card makeSmartMove(ArrayList<Card> playStack);
}
