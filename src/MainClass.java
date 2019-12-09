import javax.swing.JFrame;
import java.io.*;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.PreparedStatement;

public class MainClass {
	  //Initialize the frames for User Interface. 
	  public static JFrame startupF = new JFrame();
	  public static JFrame blackJackF = new JFrame();
	
	  // Initialize the scores for dealer and player.
	  public static int availBal = 5000;
	  private static int score_d = 0;
	  private static int score_p = 0;
	  
	  
	  public static BlackJack_Game startGame = new BlackJack_Game(blackJackF);
	  
	  // To keep a track to reset the values 
	  private static boolean PlayFirst = true;
	  
	  //To display frames based on state
	  public static enum PAGE{
		  STARTUP,
		  BLACKJACK
	  };
	  
	  //INITIALIZE THE FIRST PAGE
	  public static PAGE now_state = PAGE.STARTUP;

	  
	  //Main function to initialize the game. LETS START THE GAME!!!
	  public static void main(String[] args) throws InterruptedException, FileNotFoundException  {
		    /* store the output in current working directory */
		  String curr_dir = System.getProperty("user.dir");
		  PrintStream logger = new PrintStream(new File(curr_dir+"\\BlackJack_Output.txt")); 
		  System.setOut(logger); 
		  if(now_state == PAGE.STARTUP) {
			  //Set all default parameters for the GUI screen.
			  startupF.setTitle("BLACKJACK GAME! READY TO PLAY? ");
			  startupF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			  startupF.setSize(1130, 665);
			  startupF.setLocationRelativeTo(null);
			  
			  //DONT CHANGE THIS - Make frame not resizeable
			  startupF.setResizable(false);
			  
			  // Object for buttons - Add them to startup frame
			  Game_UI gUI = new Game_UI();
			  startupF.add(gUI);
			  startupF.setVisible(true);
		  }
		  BlackJack_Test t = new BlackJack_Test();
		  try {
			 // System.out.println("Hoooooooo");
			t.test1();
			t.test2();
			t.test3();
			t.test4();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  
	  }
	
	  public static Thread BlackJackRefresh = new Thread () {
		  public void run () {
			  while(true){
				  startGame.bg_cp.refresh(availBal, score_p, score_d-1, startGame.cardHidden);
			  }
		  }
	  };
	
	  public static Thread BlackJackCheck = new Thread () {
	    public void run () {
	      while(true) {
	        if (PlayFirst||startGame.is_refresh) {
	        	System.out.println("*****************************************");
	        	System.out.println("Play a new game. Place your bet now!");
	        	System.out.println("*****************************************");
	        	if (startGame.is_win_dealer){
	        		//We will increase the scores of dealer
	        		score_d++;
	        		// Subtract the balances from money earned or lost.
	        		availBal-= PlayerView.placed_Bet;
	        	}
	        	else { 
	        		score_p++;
	        		availBal+= PlayerView.placed_Bet*2;
	        	}
	        	//Round is over. SO remove everything and reset a new game.
	        	blackJackF.getContentPane().removeAll();
	        	startGame = new BlackJack_Game(blackJackF);
	        	startGame.create_BJ();
	
	        	PlayFirst = false; }}
	    }
	  };
}
