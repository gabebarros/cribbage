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
    private final JPanel playCardsPanel = new JPanel();  // holds buttons only
    
    private JLabel playStackTotalLabel = new JLabel("Total Value: 0");

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
        starterCardLabel.setAlignmentX(Component.CENTER_ALIGNMENT);  // Aligns component in BoxLayout
        starterCardLabel.setHorizontalAlignment(SwingConstants.CENTER); // Aligns text inside label
        starterCardLabel.setMaximumSize(new Dimension(Integer.MAX_VALUE, starterCardLabel.getPreferredSize().height)); // Allows stretching
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
        playAreaPanel.setLayout(new BoxLayout(playAreaPanel, BoxLayout.Y_AXIS));
        playAreaPanel.setPreferredSize(new Dimension(600, 150));

        // Set up the label
        playStackTotalLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        playAreaPanel.add(playStackTotalLabel);

        // Set up the card buttons panel
        playCardsPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        playAreaPanel.add(playCardsPanel);

        // Add to cribPanel
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
    	if (card == null) {
    		starterCardLabel.setText(null);
    	}
    	else {
    		starterCardLabel.setText("Starter: " + card);
    	} 
    }

    public void updatePlayerHand(Player player, JPanel panel, ActionListener listener, GameMode gamemode) {
        panel.removeAll();
        int index = 0;
        JButton b;
        for (Card c : player.getHand()) {
        	if ((gamemode == GameMode.CPU_EASY || gamemode == gamemode.CPU_HARD) && game.getPlayer2() == player) {
        		b = new JButton(" ");
        	}
        	else {
        		b = new JButton(c.toString());
        	}
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
    
    @Override
    public void onPlayStackUpdated(ArrayList<Card> playStack) {
        playCardsPanel.removeAll();

        int total = 0;
        for (Card card : playStack) {
            total += card.getValue(); // Make sure this uses correct Cribbage logic
            JButton cardButton = new JButton(card.toString());
            playCardsPanel.add(cardButton);
        }

        playStackTotalLabel.setText("Play Stack Total: " + total);

        playCardsPanel.revalidate();
        playCardsPanel.repaint();
    }
    
    @Override
    public void onScoreUpdated(int player1Score, int player2Score) {
        player1ScoreLabel.setText(game.getPlayer1().getName() + ": " + player1Score);
        player2ScoreLabel.setText(game.getPlayer2().getName() + ": " + player2Score);

        if (game.getPlayer1().getScore() >= 121 || game.getPlayer2().getScore() >= 121) {
            controller.setGameOver(true);

            String winner = game.getPlayer1().getScore() >= 121
                ? game.getPlayer1().getName()
                : game.getPlayer2().getName();

            JOptionPane.showMessageDialog(
                null,
                winner + " wins the game!",
                "Game Over",
                JOptionPane.INFORMATION_MESSAGE
            );

            int choice = JOptionPane.showConfirmDialog(
                null,
                "Would you like to play again?",
                "Play Again?",
                JOptionPane.YES_NO_OPTION
            );

            if (choice == JOptionPane.YES_OPTION) {
                // Delay restart until after current call stack clears
                SwingUtilities.invokeLater(() -> {
                    game.resetForNewGame();
                    onCribUpdated();
                    updateStarterCard(null);
                    controller.setGameOver(false);
                    controller.startGame();
                });
            } else {
                System.exit(0);
            }
        }
    }
    
    public void updateDealerIndicator(Player dealer) {
        String p1Title = controller.getGame().getPlayer1().getName();
        String p2Title = controller.getGame().getPlayer2().getName();

        if (dealer == controller.getGame().getPlayer1()) {
            p1Title += " (Dealer)";
        } else {
            p2Title += " (Dealer)";
        }

        player1Panel.setBorder(BorderFactory.createTitledBorder(p1Title));
        player2Panel.setBorder(BorderFactory.createTitledBorder(p2Title));

        player1Panel.repaint();
        player2Panel.repaint();
    }

    public static void main(String[] args) { 	
        String[] options = {"Human vs Human", "Human vs CPU"};
        int mode = JOptionPane.showOptionDialog(
            null,
            "Choose game mode:",
            "Cribbage Game Mode",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            null
        );

        String player1Name = JOptionPane.showInputDialog(null, "Enter name for Player 1:");
        if (player1Name == null || player1Name.trim().isEmpty()) {
            player1Name = "Player 1";
        }

        String player2Name;
        GameMode gamemode;

        if (mode == 1) { // Human vs CPU
            player2Name = "CPU";
            
            String[] difficultyOptions = {"Easy", "Hard"};
            int difficulty = JOptionPane.showOptionDialog(
                null,
                "Choose CPU difficulty:",
                "CPU Difficulty",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                difficultyOptions,
                null
            );

            if (difficulty == 0) {
                gamemode = GameMode.CPU_EASY;
            } else {
                gamemode = GameMode.CPU_HARD;
            }
        } else { // Human vs Human or default
            player2Name = JOptionPane.showInputDialog(null, "Enter name for Player 2:");
            if (player2Name == null || player2Name.trim().isEmpty()) {
                player2Name = "Player 2";
            }
            gamemode = GameMode.PVP;
        }
        
        game = new Game(player1Name, player2Name);
        View view = new View(player1Name, player2Name);
        controller = new Controller(game, view, gamemode);

        controller.startGame();
    }

}