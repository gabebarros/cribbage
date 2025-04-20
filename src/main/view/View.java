package main.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import main.controller.Controller;
import main.model.*;

public class View extends JFrame implements GameObserver {
	private Controller controller;
	
    private JPanel player1Panel = new JPanel();
    private JPanel player2Panel = new JPanel();
    private JPanel cribPanel = new JPanel();
    private JPanel cribCardsPanel = new JPanel();
    private JLabel starterCardLabel = new JLabel();
    private JPanel scorePanel = new JPanel();
    private JLabel player1ScoreLabel;
    private JLabel player2ScoreLabel;

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
        JPanel cribContainer = new JPanel();
        cribContainer.setLayout(new BorderLayout());

        JLabel cribLabel = new JLabel("Crib Cards:", SwingConstants.CENTER);
        cribContainer.add(cribLabel, BorderLayout.NORTH);

        cribCardsPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); // Ensure crib cards are also centered
        cribContainer.add(cribCardsPanel, BorderLayout.CENTER);

        cribPanel.add(cribContainer, BorderLayout.CENTER);
        
        player1ScoreLabel = new JLabel(p1 + " : 0");
        player2ScoreLabel = new JLabel(p2 + " : 0");
        
        // Create a score panel for the bottom right of the cribPanel
        scorePanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 1;  // Position to the right (GridBagLayout index starts at 0)
        gbc.gridy = 1;  // Position to the bottom (GridBagLayout index starts at 0)
        scorePanel.add(player1ScoreLabel, gbc);

        gbc.gridy = 2;  // Move to the next row
        scorePanel.add(player2ScoreLabel, gbc);

        // Place the score panel in the bottom-right of the cribPanel
        cribPanel.add(scorePanel, BorderLayout.SOUTH);

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

    // New method to display the crib card prompt
    public void promptChooseCribCards() {
        String message = "Please choose 2 cards for your crib.";
        String title = "Choose Crib Cards";

        // Show a modal dialog asking the user to choose the crib cards
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.INFORMATION_MESSAGE);
    }
    
    public void updateScores(int player1Score, int player2Score) {
        player1ScoreLabel.setText("Player 1 Score: " + player1Score);
        player2ScoreLabel.setText("Player 2 Score: " + player2Score);
    }

    public static void main(String[] args) {
        Game game = new Game("Bob", "CPU");
        View view = new View("Bob", "CPU");
        Controller controller = new Controller(game, view);

        game.dealHands();
        controller.showHands();

        // Display the crib card prompt
        view.promptChooseCribCards();
        
    }
}