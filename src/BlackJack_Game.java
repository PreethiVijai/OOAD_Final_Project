import java.util.ArrayList;
import java.awt.event.*;
import javax.swing.*;
import java.awt.Font;
import java.io.FileInputStream;
import java.util.Collections;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;

public class BlackJack_Game {
	  
	  public boolean cardHidden;
	  public boolean is_win_dealer;
	  public volatile boolean is_refresh;
	  private Hand hand;
	  // In order to change the UI, creating 2 objects of PlayerView to keep track.
	  PlayerView bg_cp;
	  PlayerView crd_cp;
	  
	  // List of cards for Dealer and Player
	  ArrayList<Card> card_Dealer;
	  ArrayList<Card> card_Player;

	  // Create a JF to enable the buttons
	  JFrame JF;
	  Deck dk;
	  
	  public static Connection con;
	  PreparedStatement statement = null;
      // Create the buttons 
	  JButton hit_Button;
	  JButton stand_Button;
	  JButton double_Button;
	  JButton exit_Button;

	  public BlackJack_Game(JFrame f) {
		  //Create a deck and shuffle it
		    try {
		    	/**** Singleton pattern implemented ****/
				dk = Deck.getInstance();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		    dk.shufflecardDeck();
		    hand = new Hand();
		    // Initialize dealer and player cards
		    card_Dealer = new ArrayList<Card>();
		    card_Player = new ArrayList<Card>();
		    
		    bg_cp = new PlayerView(card_Dealer, card_Player);
		    JF = f;
		    
		    //Initialize the card as hidden at first
		    cardHidden = true;
		    
		    is_win_dealer = true;
		    is_refresh = false;
		    
		    try
	        {
		    	/**** Facade pattern implementation - Using jdbc.sql.connection, the implementation is abstracted from the user ******/ 
	            connectionQuery();
	            statement =  con.prepareStatement("Show databases;");
	           ResultSet result = statement.executeQuery();
	            System.out.println("DataBase table accessed");
			
			  while(result.next()) { String r_id= result.getString("Database");
			  //System.out.println(retrievedid); 
			  }
			 
	            //con.close();
	        }

	        catch(Exception e)
	        {
	            e.printStackTrace();
	            System.out.println(e.getMessage().toString());
	        }
	  }

	  public static void connectionQuery()
	  {
	        try
	        {
	            Class.forName("com.mysql.jdbc.Driver");
	            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ooad?useSSL=false","root","Code123#");
	            System.out.println("Database connection established");
	        }
	        catch (ClassNotFoundException e)
	        {
	            e.printStackTrace();
	            System.out.println("Remote server could not be connected");
	        }
	        catch (SQLException e)
	        {
	            e.printStackTrace();
	            System.out.println("Remote db connection establishment error");
	        }
	    }
	  
	  public void create_BJ() {
	
		   // System.out.println("GAME FORMED");
		    JF.setTitle("BLACKJACK!");
		    JF.setSize(1130, 665);
		    JF.setLocationRelativeTo(null);
		    //Dont change this
		    JF.setResizable(false);
		    JF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		    //Define all the buttons
		    hit_Button = new JButton("HIT");
		    double_Button = new JButton("DOUBLE");
		    stand_Button = new JButton("STAND");
		    exit_Button = new JButton("EXIT GAME");
		    
		    // Set the dimensions of buttons
		    hit_Button.setBounds(390, 550, 100, 50);
		    double_Button.setBounds(650, 550, 150, 50);
		    stand_Button.setBounds(520, 550, 100, 50);
		    exit_Button.setBounds(930, 50, 140, 50);
		    
		    //Set font attributes
		    hit_Button.setFont(new Font("Arial", Font.BOLD, 16));
		    double_Button.setFont(new Font("Arial", Font.BOLD, 16));
		    stand_Button.setFont(new Font("Arial", Font.BOLD, 16));
		    exit_Button.setFont(new Font("Arial", Font.BOLD, 16));
		    
		    // Add buttons to the frame
		    JF.add(hit_Button);
		    JF.add(double_Button);
		    JF.add(stand_Button);
		    JF.add(exit_Button);
		
		    /* ..... IF exit button is pressed......... */
		    exit_Button.addActionListener(new ActionListener() { 
		      public void actionPerformed(ActionEvent e) {
		        JOptionPane.showMessageDialog(JF, "Adios! ");
		        
		        System.out.println("You clicked on exit button. Game over! Adios!");
		        /* Print all the values of database */
		        PreparedStatement query = null;
		        try{
		        	 	query =  con.prepareStatement("Select * from blackjack;");
				        ResultSet result = query.executeQuery();
				        System.out.println("Blackjack Table Accessed");
						
						  while(result.next()) { 
							  String id = result.getString("id");
							  String winner = result.getString("Winner");
							  String reason = result.getString("Reason");
							  
							  System.out.println("ID: "+ id + "   WINNER: "+ winner +"   REASON: " + reason ); 
						  }
		    	  }
		    	  catch (Exception ex) {  
		              System.err.println(ex.getMessage()); 
		          }
		        System.exit(0);
		      }
		    });
		
		    /* Else deal the cards to dealer and player */
		    bg_cp = new PlayerView(card_Dealer, card_Player);
		    bg_cp.setBounds(0, 0, 1130, 665);
		    JF.add(bg_cp);
		    JF.setVisible(true);
	  }

	  public void BlackJack_Start() {
		  // Assign cards for dealer
		  for(int j = 0; j<2; j++) {
			  card_Dealer.add(dk.obtaincard(j));
		  }
		  
		  //Assign cards for Player
		  for(int j = 2; j<4; j++) {
			  card_Player.add(dk.obtaincard(j));
		  }
	    
		  for (int j = 0; j < 4; j++) {dk.discardcard(0);}
	
		  //Placing the cards in correct boundary
	      crd_cp = new PlayerView(card_Dealer, card_Player);
	      crd_cp.setBounds(0, 0, 1130, 665);
	      JF.add(crd_cp);
	      JF.setVisible(true);
	      
	      //Check the hands for Blackjack or continue round
	      curr_Hand_check(card_Dealer);
	      curr_Hand_check(card_Player);
	
	      /* Defining functions for Stand button click */
	      stand_Button.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent e) {
		    	  // soft -17 rule checked
		        while ( gen_add_hand(card_Dealer) < 17 ) {                                                  
		        	add_a_Card(card_Dealer);
		        	curr_Hand_check(card_Dealer);
		        }
		        
		        //Deciding the winner factor
		        if (( gen_add_hand(card_Dealer) < 21) && gen_add_hand(card_Player) <21) {
		        	if( gen_add_hand(card_Player) > gen_add_hand(card_Dealer)) {
		        		is_win_dealer = false;
		        		cardHidden = false;
		        		JOptionPane.showMessageDialog(JF, "Better hand for Player. Player WON! ");
		        		try{
		        			System.out.println("Player won because of a better hand.. Inserting it into database");
				    		  statement.executeUpdate("INSERT INTO blackjack(Winner,Reason) " + "VALUES ('Player', 'Better hand for Player')");
				    	  }
				    	  catch (Exception ex) {  
				              System.err.println(ex.getMessage()); 
				          }
		        		rest();
		        		is_refresh = true;
		        	}
		        	else {
		        		cardHidden = false;
		        		JOptionPane.showMessageDialog(JF, "Better hand for Dealer. Dealer WON! ");
		        		try{
		        			  System.out.println("Dealer won because of a better hand.. Inserting it into database");
				    		  statement.executeUpdate("INSERT INTO blackjack(Winner,Reason) " + "VALUES ('Dealer', 'Better hand for dealer')");
				    	  }
				    	  catch (Exception ex) {  
				              System.err.println(ex.getMessage()); 
				          }
		        		rest();
		        		is_refresh = true;
		        	}
		        }
		      }
		    });
	      
	      
	      /* Defining functions for Double button click */
	      double_Button.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent e) {
		        // Double button adds two cards to the player.
		        add_a_Card(card_Player);add_a_Card(card_Player);
		        curr_Hand_check(card_Player);
		        //soft -17 Usecase check
		        if (gen_add_hand(card_Player) < 17 && gen_add_hand(card_Dealer) < 17 ){                                                  
		        	add_a_Card(card_Dealer);
		        	curr_Hand_check(card_Dealer);
		        }
		      }
		    });
	      
	      /* Defining functions for hit button click */
	      hit_Button.addActionListener(new ActionListener() {
	    	  public void actionPerformed(ActionEvent e) {
	    		  // Hit adds one card to the player
	    		  add_a_Card(card_Player);
	    		  curr_Hand_check(card_Player);
	    		  //soft -17 Usecase check
	    		  if (gen_add_hand(card_Player) < 17 && gen_add_hand(card_Dealer) < 17){                                                 
	    			  add_a_Card(card_Dealer);
	    			  curr_Hand_check(card_Dealer); 
	    		  }
	    	  }
	      });    
	  }

	  public void curr_Hand_check (ArrayList<Card> h) {
	    if (h.equals(card_Player)) {
		      if(gen_add_hand(h) == 21){
		    	  is_win_dealer = false;
		    	  cardHidden = false;
		    	  // Display the message
		    	  JOptionPane.showMessageDialog(JF, "BLACKJACK! Player Won !!");
		    	  try{
		    		  System.out.println("Player won because of BlackJack.. Inserting it into database");
		    		  statement.executeUpdate("INSERT INTO blackjack(Winner,Reason) " + "VALUES ('Player', 'Blackjack')");
		    	  }
		    	  catch (Exception e) { 
		              System.err.println("Got an exception! "); 
		          }
		    	  rest();
		    	  is_refresh = true;
		      }
		      else if (gen_add_hand(h) > 21) {
		    	  // Display the message
		    	  cardHidden = false; JOptionPane.showMessageDialog(JF, "BUSTED! Dealer Won !!");
		    	  try{
		    		  System.out.println("Player is busted. Dealer won because of BlackJack.. Inserting it into database");
		    		  statement.executeUpdate("INSERT INTO blackjack(Winner,Reason) " + "VALUES ('Dealer', 'Player Busted')");
		    	  }
		    	  catch (Exception e) {  
		              System.err.println(e.getMessage()); 
		          } 
		    	  rest();
		    	  is_refresh = true;
		      }
	    }
	    else {
		      if(gen_add_hand(h) == 21) {
		        cardHidden = false;
		        // Display the message
		        JOptionPane.showMessageDialog(JF, "BLACKJACK! Dealer Won !!");
		        try{
		        	  System.out.println("Dealer won because of BlackJack.. Inserting it into database");
		    		  statement.executeUpdate("INSERT INTO blackjack(Winner,Reason) " + "VALUES ('Dealer', 'Blackjack')");
		    	  }
		    	  catch (Exception e) {  
		              System.err.println(e.getMessage()); 
		          }
		        rest();
		        is_refresh = true;
		      }
		      else if (gen_add_hand(h) > 21) {
		    	is_win_dealer = false;
		    	cardHidden = false;
		        // Display the message
		        JOptionPane.showMessageDialog(JF, "BUSTED! Player Won !!");
		        try{
		        	  System.out.println("Dealer is busted. Player won because of BlackJack.. Inserting it into database");
		    		  statement.executeUpdate("INSERT INTO blackjack(Winner,Reason) " + "VALUES ('Player', 'Dealer Busted')");
		    	  }
		    	  catch (Exception e) { 
		              System.err.println(e.getMessage()); 
		          }
		        rest();
		        is_refresh = true;
		      }
	    	}
	  	}

	  // Add a card
	  public void add_a_Card(ArrayList<Card> h) {
		    h.add(dk.obtaincard(0));
		    dk.discardcard(0);
		    // Hide the card
		    cardHidden = true;
	  }

	  // Check whether you have Ace - to add 1 or 11
	  public boolean check_ace_hand(ArrayList<Card> h) {
		    		return this.hand.check_ace_hand(h);
	  }

	  // Count the number of Aces in hand
	  public int ace_cnt_hand(ArrayList<Card> h){
		  return this.hand.ace_cnt_hand(h);
		    }

	  // Get sum of highest ace
	  public int high_ace_get_sum(ArrayList<Card> h) {
		  return this.hand.high_ace_get_sum(h);
	  }

	  // Generate additional Hand
	  public int gen_add_hand (ArrayList<Card> h) {
		  return this.hand.gen_add_hand(h);
	  }

	  // rest function
	  public static void rest() {
	    try {
	      Thread.sleep(500);
	    }
	    catch (InterruptedException e) {
	    	//Exception e
	    }
	  }
}
