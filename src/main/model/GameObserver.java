package main.model;

public interface GameObserver {
    void onCribUpdated();
    void onStarterCardDrawn(Card card);
}
