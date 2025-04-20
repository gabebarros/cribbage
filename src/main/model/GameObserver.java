package main.model;

import java.util.ArrayList;

public interface GameObserver {
    void onCribUpdated();
    void onStarterCardDrawn(Card card);
}
