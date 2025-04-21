package main.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import main.controller.Controller;
import main.model.*;

public class View extends JFrame implements GameObserver {
	private static Controller controller;
	private static Game game;
	
    private JPanel player1Panel = new JPanel();
    private JPanel player2Panel = new JPanel();
    private JPanel cribPanel = new JPanel();
    private JPanel cribCardsPanel = new JPanel();
    private JLabel starterCardLabel = new JLabel();
    private JPanel scorePanel = new JPanel();
    private JLabel player1ScoreLabel;
    private JLabel player2ScoreLabel;
    
    private JPanel playAreaPanel = new JPanel();

    public View(String p1, String p2) {
        setTitle("Cribbage");
        setSize(1300, 900);
        setLayout(new BorderLayout());

        // Player hands
        player1Panel.setBorder(BorderFactory.createTitledBorder(p1));
        add(player1Panel, BorderLayout.SOUTH);

        player2Panel.setBorder(BorderFactory.createTitledBorder(p2));
        add(player2Panel, BorderLayout.NORTH);

        // Crib panel in center (will now hold starter, crib, play area, and scores)
        cribPanel.setBorder(BorderFactory.createTitledBorder("Play Area"));
        cribPanel.setLayout(new BoxLayout(cribPanel, BoxLayout.Y_AXIS));  // Stack vertically
        add(cribPanel, BorderLayout.CENTER);

        // Starter card label
        starterCardLabel.setAlignmentX(Component.CENTER_ALIGNMENT);  // Center horizontally
        cribPanel.add(starterCardLabel);

        // Crib cards section
        JPanel cribContainer = new JPanel();
        cribContainer.setLayout(new BorderLayout());

        JLabel cribLabel = new JLabel("Crib:", SwingConstants.CENTER);
        cribContainer.add(cribLabel, BorderLayout.NORTH);

        cribCardsPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        cribContainer.add(cribCardsPanel, BorderLayout.CENTER);

        cribPanel.add(cribContainer);

        // Play area section
        playAreaPanel.setBorder(BorderFactory.createTitledBorder("Play Stack"));
        playAreaPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        playAreaPanel.setPreferredSize(new Dimension(600, 150));
        cribPanel.add(playAreaPanel);

        // Score panel
        player1ScoreLabel = new JLabel(p1 + " : 0");
        player2ScoreLabel = new JLabel(p2 + " : 0");

        scorePanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        scorePanel.add(player1ScoreLabel, gbc);

        gbc.gridy = 1;
        scorePanel.add(player2ScoreLabel, gbc);

        cribPanel.add(scorePanel);  // Now at the bottom of the stacked crib panel

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
    
    @Override
    public void onPlayStackUpdated(ArrayList<Card> playStack) {
        // Clear the play area and redraw all cards in the play stack
        playAreaPanel.removeAll();
        for (Card card : playStack) {
            JButton cardButton = new JButton(card.toString()); // or use an image
            playAreaPanel.add(cardButton);
        }
        playAreaPanel.revalidate();
        playAreaPanel.repaint();
    }
    
    @Override
    public void onScoreUpdated(int player1Score, int player2Score) {
        player1ScoreLabel.setText(game.getPlayer1().getName() + ": " + player1Score);
        player2ScoreLabel.setText(game.getPlayer2().getName() + ": " + player2Score);
    }

    public static void main(String[] args) {
        game = new Game("Bob", "CPU");
        View view = new View("Bob", "CPU");
        controller = new Controller(game, view);

        game.dealHands();
        controller.playCrib();

        // Display the crib card prompt
        view.promptChooseCribCards();
        
    }
}