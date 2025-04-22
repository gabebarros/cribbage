package main.model;

import java.util.ArrayList;

public interface CpuStrategy {
	Card makeRandomMove();
    Card makeSmartMove(ArrayList<Card> playStack);
}
