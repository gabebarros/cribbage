/*
 * Functionality: the visual interface for the game, including only visual logic for the most part
 * *AI USED*
 */

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
	
	// Panels
    private JPanel player1Panel = new JPanel();
    private JPanel player2Panel = new JPanel();
    private JPanel cribPanel = new JPanel();
    private JPanel cribCardsPanel = new JPanel();
    private JPanel scorePanel = new JPanel();
    private JPanel playAreaPanel = new JPanel();
    private final JPanel playCardsPanel = new JPanel();  // holds buttons only
    private JPanel winsCornerPanel;
    
    // score labels
    private JLabel player1ScoreLabel;
    private JLabel player2ScoreLabel;
    private JLabel scoreMessageLabel = new JLabel(" "); // shows last scoring message
    
    //turn indicator atts
    private JLabel turnLabel = new JLabel();
    private String p1Name;
    private String p2Name;
    
    //other labels
    private JLabel playStackTotalLabel = new JLabel("Total Value: 0");
    private JLabel starterCardLabel = new JLabel();    
    private JLabel winsDisplayLabel;

    /*
     * The constructor, taking the names of both players for display
     * @Param p1:
     * 		name of player 1
     * @Param p2:
     * 		name of player 2
     */
    public View(String p1, String p2) {
    	p1Name = p1;
    	p2Name = p2;
    	
        setTitle("Cribbage");
        setSize(1280, 720); //basic dimensions for video resolution, fits screen without maximization
        setLayout(new BorderLayout());

        // Player hands
        player1Panel.setBorder(BorderFactory.createTitledBorder(p1));
        add(player1Panel, BorderLayout.SOUTH); //plant at bottom

        player2Panel.setBorder(BorderFactory.createTitledBorder(p2));
        add(player2Panel, BorderLayout.NORTH); //plant at top

        // Crib panel in center (will now hold starter, crib, play area, and scores)
        cribPanel.setBorder(BorderFactory.createTitledBorder("Play Area"));
        cribPanel.setLayout(new BoxLayout(cribPanel, BoxLayout.Y_AXIS));  // Stack vertically
        add(cribPanel, BorderLayout.CENTER); //plant in middle

        // Starter card label
        starterCardLabel.setAlignmentX(Component.CENTER_ALIGNMENT);  // Aligns component in BoxLayout
        starterCardLabel.setHorizontalAlignment(SwingConstants.CENTER); // Aligns text inside label
        starterCardLabel.setMaximumSize(new Dimension(Integer.MAX_VALUE, starterCardLabel.getPreferredSize().height)); // Allows stretching
        
     // Top container for wins corner + starter label
        JPanel topCribHeader = new JPanel(new BorderLayout());
        topCribHeader.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));  // Limits height

        // Wins corner panel (right side of play area)
        winsCornerPanel = new JPanel();
        winsDisplayLabel = new JLabel("Wins - " + game.getPlayer1().getName() + ": 0, " + game.getPlayer2().getName() + ": 0");
        winsCornerPanel.add(winsDisplayLabel);
        topCribHeader.add(winsCornerPanel, BorderLayout.EAST); //plant on right

        // Optional: spacer to the left
        topCribHeader.add(Box.createHorizontalStrut(10), BorderLayout.WEST);

        // Starter label centered
        topCribHeader.add(starterCardLabel, BorderLayout.CENTER);

        // Add header panel to cribPanel
        cribPanel.add(topCribHeader);
        cribPanel.add(starterCardLabel);

        // Crib cards section
        JPanel cribContainer = new JPanel();
        cribContainer.setLayout(new BorderLayout());

        //add crib label
        JLabel cribLabel = new JLabel("Crib:", SwingConstants.CENTER);
        cribContainer.add(cribLabel, BorderLayout.NORTH); //plant in center of cribContainer

        //crib card display
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
        
        // Score message display
        scoreMessageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        cribPanel.add(scoreMessageLabel); // Add it to the bottom of the main panel

        scorePanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        scorePanel.add(player1ScoreLabel, gbc);

        gbc.gridy = 1;
        scorePanel.add(player2ScoreLabel, gbc);

        cribPanel.add(scorePanel);  // Now at the bottom of the stacked crib panel
        
        //add turn indicator
        playAreaPanel.add(turnLabel);
        turnLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        //set to terminate on close and make visible once all has been done
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    /* method to update starter card
     * @param: card
     * 		a card, the starter card to be specific
     */
    public void updateStarterCard(Card card) {
    	if (card == null) { //null check
    		starterCardLabel.setText(null);
    	}
    	else {
    		starterCardLabel.setText("Starter: " + card);
    	} 
    }

    /*
     * method for updating the visual player hand
     * @param player:
     * 		which player needs updating
     * @param panel: 
     * 		the player's panel
     * @listener:
     * 		the listner
     * @gamemode:
     * 		gamemode, self-explanatory.
     */
    public void updatePlayerHand(Player player, JPanel panel, ActionListener listener, GameMode gamemode) {
        panel.removeAll(); //removes old cards in panel
        int index = 0;
        JButton b;
        //loop for adding current hand
        for (Card c : player.getHand()) {
        	if ((gamemode == GameMode.CPU_EASY || gamemode == gamemode.CPU_HARD) && game.getPlayer2() == player) {
        		b = new JButton(" "); //hide cpu cards
        	}
        	else {
        		b = new JButton(c.toString()); //show if plyer
        	}
        	//set listener for card value
            b.setActionCommand(String.valueOf(index));
            b.addActionListener(listener);
            panel.add(b);
            index++;
        }
        //update
        panel.revalidate();
        panel.repaint();
    }

    //basic getters for player panels
    public JPanel getPlayer1Panel() { return player1Panel; }
    public JPanel getPlayer2Panel() { return player2Panel; }

    //basic setter for controller
    // @param controller: controller instance
    public void setController(Controller controller) {this.controller = controller;}

    @Override
    /*
     * update event for when the crib is updated
     */
    public void onCribUpdated() {
        cribCardsPanel.removeAll(); //remove prev cards
        for (Card c : controller.getGame().getCrib()) { //loop for adding current cards
            cribCardsPanel.add(new JLabel(c.toString())); //label those cards!
        }
        //update
        cribCardsPanel.revalidate();
        cribCardsPanel.repaint();
    }

    @Override
    /*
     * event for starter card draw
     * @param card:
     * 		the starter card drawn.
     */
    public void onStarterCardDrawn(Card card) {
        updateStarterCard(card); //update visual for starter card
    }
    
    @Override
    /*
     * event for play stack being updated
     * @param playStack:
     * 		current cards in the playstack
     */
    public void onPlayStackUpdated(ArrayList<Card> playStack) {
        playCardsPanel.removeAll(); //remove prev card visuals

        int total = 0; //counter
        for (Card card : playStack) {
            total += card.getValue();
            JButton cardButton = new JButton(card.toString()); 
            playCardsPanel.add(cardButton); //add new card
        }

        playStackTotalLabel.setText("Play Stack Total: " + total); //label text update

        //update panel to screen
        playCardsPanel.revalidate();
        playCardsPanel.repaint();
    }
    
    @Override
    /*
     * event for the score being updated.
     * @param player1Score:
     * 		score of player 1
     * @param player2Score:
     * 		score of player 2
     */
    public void onScoreUpdated(int player1Score, int player2Score) {
    	//update score labels
        player1ScoreLabel.setText(game.getPlayer1().getName() + ": " + player1Score);
        player2ScoreLabel.setText(game.getPlayer2().getName() + ": " + player2Score);

        //check if someone has won yet.
        if (game.getPlayer1().getScore() >= 121 || game.getPlayer2().getScore() >= 121) {
            controller.setGameOver(true); //ends game absolutely

            //check who won
            String winner = game.getPlayer1().getScore() >= 121
                ? game.getPlayer1().getName()
                : game.getPlayer2().getName();
            
            //second check in case both players crossed 121, winner should be higher score
            winner = game.getPlayer1().getScore() > game.getPlayer2().getScore()
            		? game.getPlayer1().getName()
            		: game.getPlayer2().getName();

            // Increment the winner's win count
            if (winner.equals(game.getPlayer1().getName())) {
                game.incrementPlayer1Wins();
            } else {
                game.incrementPlayer2Wins();
            }
            
            //update wins display
            winsDisplayLabel.setText("Wins - " + game.getPlayer1().getName() + ": " + game.getPlayer1Wins() + ", "
            + game.getPlayer2().getName() + ": " + game.getPlayer2Wins());
            
            //game over msg
            JOptionPane.showMessageDialog(
                null,
                winner + " wins the game!",
                "Game Over",
                JOptionPane.INFORMATION_MESSAGE
            );

            //replay option
            int choice = JOptionPane.showConfirmDialog(
                null,
                "Would you like to play again?",
                "Play Again?",
                JOptionPane.YES_NO_OPTION
            );

            //if replay
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

    /*
     * event for dealer update
     * @param dealer:
     * 		whichever player is the new dealer
     */
    public void updateDealerIndicator(Player dealer) {
    	//check for dealer
        if (dealer == controller.getGame().getPlayer1()) {
        	if (!p1Name.equals(dealer.getName() + " (Dealer)")) {
        		p1Name += " (Dealer)";
        	}
        	p2Name = game.getPlayer2().getName();
        } else {
        	if (!p2Name.equals(dealer.getName() + " (Dealer)")) {
        		p2Name += " (Dealer)";
        	}
        	p1Name = game.getPlayer1().getName();
        }
        
        //set panels according to names
        player1Panel.setBorder(BorderFactory.createTitledBorder(p1Name));
        player2Panel.setBorder(BorderFactory.createTitledBorder(p2Name));

        //update screen
        player1Panel.repaint();
        player2Panel.repaint();
    }

    /*
     * method for game initialization on view run.
     * @param args[]:
     * 		main args, unused here.
     */
    public static void main(String[] args) { 	
        String[] options = {"Human vs Human", "Human vs CPU"};
        // difficulty selection
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

        //set player1name
        String player1Name = JOptionPane.showInputDialog(null, "Enter name for Player 1:");
        if (player1Name == null || player1Name.trim().isEmpty()) {
            player1Name = "Player 1";
        }

        String player2Name;
        GameMode gamemode;

        if (mode == 1) { // Human vs CPU
            player2Name = "CPU"; //default name
            
            //cpu difficulty selection
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

            //sets le gamemode depending on choice
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
        
        //create game 
        game = new Game(player1Name, player2Name); //init game
        View view = new View(player1Name, player2Name); //intialize view
        controller = new Controller(game, view, gamemode); //init controller

        //set the game to begin
        controller.startGame();
    }

    //method for updating to player1 turn
	public void player1Turn() {
		turnLabel.setText(p1Name + "'s turn!");
	}

	//method for updating to player2's turn
	public void player2Turn() {
		turnLabel.setText(p2Name + "'s turn!");
	}

	/*
	 * event for end of round summary display.
	 * @param startCard:
	 * 		starting card
	 * @param p1Points:
	 * 		player 1's point total
	 * @param p2Points:
	 * 		player 2's point total
	 * @param cribPoints:
	 * 		cribPoint total
	 */
	public void showSummary(Card startCard, int p1Points, int p2Points, int cribPoints) {
		//show stats
		JOptionPane.showMessageDialog(null,
	            "Show Phase:\n\n" +
	            p1Name + "'s hand: " + game.getPlayer1OriginalHand() + "\n" +
	            "Starter: " + startCard + "\n" +
	            "Points: " + p1Points + "\n\n" +

	            p2Name + "'s hand: " + game.getPlayer2OriginalHand() + "\n" +
	            "Starter: " + startCard + "\n" +
	            "Points: " + p2Points + "\n\n" +

	            "Crib: " + game.getCrib() + "\n" +
	            "Starter: " + startCard + "\n" +
	            "Crib Points: " + cribPoints,
	            "Show Phase",
	            JOptionPane.INFORMATION_MESSAGE
	        );
	}
	
	public void showScoreMessage(String message) {
		scoreMessageLabel.setText(message);
		
		// Clear message after a few seconds:
		new javax.swing.Timer(3000, e -> scoreMessageLabel.setText(" ")).start();
	}

}