package main.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import main.controller.Controller;
import main.model.*;

public class View extends JFrame implements GameObserver {
    private JPanel player1Panel = new JPanel();
    private JPanel player2Panel = new JPanel();
    private JPanel cribPanel = new JPanel();
    private JPanel cribCardsPanel = new JPanel();
    private JLabel starterCardLabel = new JLabel();
    private Controller controller;

    public View(String p1, String p2) {
        setTitle("Cribbage");
        setSize(1000, 900);
        setLayout(new BorderLayout());

        player1Panel.setBorder(BorderFactory.createTitledBorder(p1));
        add(player1Panel, BorderLayout.SOUTH);

        player2Panel.setBorder(BorderFactory.createTitledBorder(p2));
        add(player2Panel, BorderLayout.NORTH);

        cribPanel.setBorder(BorderFactory.createTitledBorder("Crib + Starter"));
        cribPanel.setLayout(new BorderLayout());
        cribPanel.add(starterCardLabel, BorderLayout.NORTH);
        cribPanel.add(cribCardsPanel, BorderLayout.CENTER);
        add(cribPanel, BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void updateStarterCard(Card card) {
        starterCardLabel.setText("Starter: " + card);
    }

    public void updatePlayerHand(Player player, JPanel panel, ActionListener listener) {
        panel.removeAll();
        int index = 0;
        for (Card c : player.getHand()) {
            JButton b = new JButton(c.toString());
            b.setActionCommand(String.valueOf(index));
            b.addActionListener(listener);
            panel.add(b);
            index++;
        }
        panel.revalidate();
        panel.repaint();
    }

    public JPanel getPlayer1Panel() { return player1Panel; }
    public JPanel getPlayer2Panel() { return player2Panel; }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void onCribUpdated() {
        cribCardsPanel.removeAll();
        for (Card c : controller.getGame().getCrib()) {
            cribCardsPanel.add(new JLabel(c.toString()));
        }
        cribCardsPanel.revalidate();
        cribCardsPanel.repaint();
    }

    @Override
    public void onStarterCardDrawn(Card card) {
        updateStarterCard(card);
    }

    public static void main(String[] args) {
        Game game = new Game("Bob", "CPU");
        View view = new View("Bob", "CPU");
        Controller controller = new Controller(game, view);

        game.dealHands();
        controller.showHands();
    }
}
