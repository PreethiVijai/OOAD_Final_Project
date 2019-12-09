import java.io.*;
import javax.swing.*;
import java.awt.event.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.*;


public class Game_UI extends JComponent implements ActionListener{
	// Starting the UI with background image 
	private static BufferedImage bgimg;
	
	// Initialize the buttons
	private JButton play_button = new JButton("PLAY!");
	private JButton exit_button = new JButton("EXIT!");
	private JButton help_button = new JButton("HELP!");
  

	// Needed to enable actions for buttons
	public Game_UI() {
		play_button.addActionListener(this);
		exit_button.addActionListener(this);
		help_button.addActionListener(this);
	}

	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		try {
			bgimg = ImageIO.read(new File("images/layout.png"));
		}
		catch(IOException e) {
			// Exception Caught
		}

		//Set all the strings required
	    g2.drawImage(bgimg,0,0,null);
	    g2.setFont(new Font("Arial", Font.BOLD, 70));
	    g2.setColor(Color.YELLOW);
	    g2.drawString("Welcome", 420, 100);
	    g2.drawString("to the game", 380, 180);
	    g2.drawString("BLACKJACK!", 350, 260);
	
	    // Design all the buttons - Set dimensions and font
	    exit_button.setBounds(300, 400, 150, 80);
	    exit_button.setFont(new Font("Arial", Font.BOLD, 40));
	    super.add(exit_button);
	    
	    play_button.setBounds(500, 400, 150, 80);
	    play_button.setFont(new Font("Arial", Font.BOLD, 40));
	    super.add(play_button);
	    
	    help_button.setBounds(700, 400, 160, 80);
	    help_button.setFont(new Font("Arial", Font.BOLD, 40));
	    super.add(help_button);
  }

  public void actionPerformed(ActionEvent e) {
	    // Define roles for each function
	  	JButton selectedButton = (JButton)e.getSource();

	  	if(selectedButton == play_button) {
	  		MainClass.now_state = MainClass.PAGE.BLACKJACK;
	    	MainClass.startupF.dispose();
	    	MainClass.BlackJackRefresh.start();
	    	MainClass.BlackJackCheck.start();
	  	}
	  	else if(selectedButton == help_button) {
	  		JOptionPane.showMessageDialog(this, "The goal of the player in the blackjack game is to get a sum of card values as close to 21, without going over." +
                      	"\n The player and the dealer receive two cards from a shuffled deck." +
	  					"\n If the sum is 21, its a blackjack, you won and the Game is over. If the sum is over 21, the player is busted and the opponent won." +
	  					"\n The number cards (2 through 10) are worth the number displayed, face cards are worth 10, and an Ace can be worth either 1 or 11."+ 
	  					"\n Getting another card is called 'Hitting'. Holding the total and ending your turn is 'Standing'. The player can stop hitting at any point." +
	  					"\n The dealer must keep hitting until they get to 17. If they get above 17 without busting, they can stay.", "BLACKJACK GAME RULES",
            JOptionPane.INFORMATION_MESSAGE);
	  	}
	  	else if(selectedButton == exit_button) {
    		System.exit(0);
	  	}
  }
}