import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.swing.JComponent;
import javax.imageio.ImageIO;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import java.io.*;

public class PlayerView extends JComponent implements MouseListener {
		// Declare 2 variables to maintain the scores of the dealer and player
		private int sc_Dealer;
		private int sc_Player;
	
		// Declare 2 lists to maintain the cards of the dealer and player
		private ArrayList<Card> Card_Player;
		private ArrayList<Card> Card_Dealer;
		
		//Define 3 images - bg, buzzer and logo
		public BufferedImage buzzer;
		public BufferedImage BJLogo;
		public BufferedImage BJbkground;
		
		// Close the Card Face of the Dealer
		public boolean cardUpside = true; 
		
		public static boolean is_Bet_Placed = false;
		private int curr_Amount;
		public static int placed_Bet;

		//Defining the constructor
		public PlayerView(ArrayList<Card> Card_D, ArrayList<Card> Card_P) {
			//Initialize the scores of Player and dealer
		    sc_Player = 0;
		    sc_Dealer = 0; 
		    
		    //Initialize the Cards of the dealer and the Player
			Card_Player = Card_P;
		    Card_Dealer = Card_D;		    
		    
		    //Starting the game with 5000$
		    curr_Amount = 5000;
		    addMouseListener(this);
		}

		// Paint the Images and Text
		public void paintComponent(Graphics g) {
			Graphics2D graphics2 = (Graphics2D) g;

		    try {
		    	buzzer = ImageIO.read(new File("images/bet_chip.png")); 
		    	BJLogo = ImageIO.read(new File("images/gamedesign.png"));
		    	BJbkground = ImageIO.read(new File("images/layout.png"));
		    }
		    catch(IOException e) {
		    	//Exception e
		    }
		
		    // Define the position of images - Don't change the order
		    graphics2.drawImage(BJbkground, 0, 0, null);
		    graphics2.drawImage(buzzer, 850, 250, null);
		    graphics2.drawImage(BJLogo, 470, 180, null);

		    
		    // Set all Text Attributes
		    graphics2.setColor(Color.YELLOW);
		    graphics2.setFont(new Font("Arial", Font.BOLD, 30));
		    
		    graphics2.drawString("DEALER", 515, 50);
		    graphics2.drawString("PLAYER", 515, 425);
		    
		    // Strings to display the Scores of dealer and player
		    graphics2.drawString("DEALER WON: ", 50, 245);
		    graphics2.drawString(Integer.toString(sc_Dealer), 300, 245);
		    graphics2.drawString("PLAYER WON: ", 50, 295);
		    graphics2.drawString(Integer.toString(sc_Player), 300, 295);
		    
		    
		    graphics2.setFont(new Font("Arial", Font.BOLD, 20));
		    graphics2.drawString("Start the game by betting the money.", 750, 200);
		    graphics2.drawString("Click Here.", 850, 220);
		    graphics2.setFont(new Font("Arial", Font.BOLD, 30));
		    graphics2.drawString("PRESENT BALANCE: " + curr_Amount, 50, 125);
		
		    try {
		    	for (int k = 0; k < Card_Dealer.size(); k++) {
		    		if (k != 0)
		    			Card_Dealer.get(k).displayCard(graphics2, true, false, k);
		    		else
		    			if(cardUpside)
		    				Card_Dealer.get(k).displayCard(graphics2, true, true, k);
		    			else
		    				Card_Dealer.get(k).displayCard(graphics2, true, false, k);
		    	}
		    	
		    	for (int k = 0; k < Card_Player.size(); k++) { 
			        Card_Player.get(k).displayCard(graphics2, false, false, k);
			    }
		    }
		    catch (IOException e) {
		    	// Exception E
		    }
				  
		}

		// Refresh the values
		public void refresh(int curr_money, int Score_player, int Score_Dealer, boolean card_Hidden) {
		    sc_Player = Score_player;
		    curr_Amount = curr_money;
		    cardUpside = card_Hidden;
		    sc_Dealer = Score_Dealer;
		    this.repaint();
		}

		// Defining the onclick mouse actions
		public void mousePressed(MouseEvent e) {
			int mouseX = e.getX();
			int mouseY = e.getY();

			// Don't change these values - Buzzer icon co ordinates
		    if(mouseX >= 850 && mouseX <= 1000 && mouseY >= 250 && mouseY <= 400) {
		      is_Bet_Placed = true; 
		      String[] options = new String[] {"25", "50", "75", "100"};
		      
		      //DIALOG BOX TO DISPLAY BET
		      int bet_Resp = JOptionPane.showOptionDialog(null, "PLACE YOUR BET!", "BET AMOUNT",JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
		      
		      // Set the value of bets
		      switch(bet_Resp) {
		      	case 0:
		    	  	placed_Bet = 25; 
			        curr_Amount -= 25;
			        break;
		      	case 1:
		    	  	placed_Bet = 50; 
			        curr_Amount -= 50;
			        break;
		      	case 2:
		      		placed_Bet = 75;
			        curr_Amount -= 75;
			        break;
		      	case 3:
		      		placed_Bet = 100;
			        curr_Amount -= 100;
			        break;
			    default:
			    	placed_Bet = 25;
			        curr_Amount -= 25;
			        System.out.println("The bet is taken as 25");
			        break;
		      }
		
	          System.out.println("*****************************************");
		      System.out.println("Bet Amount: " + placed_Bet + ".");
		      System.out.println("Increase your current balance by  " + placed_Bet +
		              " by winning. Your balance will reduce by " + placed_Bet + " if you lose.");
		      System.out.println("*****************************************");
		      MainClass.startGame.BlackJack_Start();
		    }
		
		  }
  
	  // Leave it Empty . Declaration needed.
	  public void mouseExited(MouseEvent e) {
	
	  }
	  public void mouseEntered(MouseEvent e) {}
	  public void mouseClicked(MouseEvent e) {
			
	  }
	  public void mouseReleased(MouseEvent e) {}
	  
  
}