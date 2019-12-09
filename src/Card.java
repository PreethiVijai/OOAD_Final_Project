import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;


/*
   class: Card
   attributes:
      1. cardsuit: The suit to which the card belongs (category)
      2. cardrank: The integer value of card. 10s jacks queens kings have integer value 10.
      3. val or face value: The picture value/ category of the card
 */

public class Card {
   // Initialize the parameters required for card
   private int val;
   private int cardrank;
   private Suits cardsuit;
   private int xval; //for UI
   private int yval; //for UI

   private HashMap<String, Integer> _rank = new HashMap<String, Integer>();
   private List<String> _suit = new ArrayList<String>();

   public Card() {
      val = 0;
      cardrank = 0;
      cardsuit = Suits.Clubs; //Default is zero. Which is for Clubs.
   }


   public Card(Suits cardsuit, Integer cardrank, Integer val) throws Exception {
      if (cardrank < 0 || cardrank > 13) {throw new Exception("Card numbers should be between 0 and 13 only.");}
      if (cardrank > 10) { 
    	 //10, jack, queen, king - all have value of 10.
         cardrank = 10;
      }
      this.cardrank = cardrank;
      this.cardsuit = cardsuit;
      this.val = val;
   }

   /**** ------- getters and setters ------------  ****/
   public void setVal(int val) {
      this.val = val;
   }

   public void setCardrank(int cardrank) {
      this.cardrank = cardrank;
   }

   public int getValue() {
      return val;
   }

   public int getRank() {
      return cardrank;
   }

   public int getSuit() {
      return cardsuit.getValue();
   }

   /**** -------- UI related co-ordinates --------- *****/
   public void setXval(int xval) {
      this.xval = xval;
   }

   public void setYval(int yval) {
      this.yval = yval;
   }


   public void displayCard(Graphics2D g2, boolean d_move, boolean hidden_card, int numcard) throws IOException {
      BufferedImage Picturedeck = ImageIO.read(new File("images/card_images.png"));
      //Fixed height and width of cards
      int h = 392;

      //Set the value to number of cards available
      BufferedImage[][] Picturecard = new BufferedImage[4][13];
      BufferedImage cardupsidedown = ImageIO.read(new File("images/card_upside_down.jpg"));
      int w = 950;
      for (int cardset = 0; cardset < 4; cardset++) {
         for (int cardrange = 0; cardrange < 13; cardrange++) {
            Picturecard[cardset][cardrange] = Picturedeck
                  .getSubimage(cardrange * w / 13, cardset * h / 4, w / 13, h / 4);
         }
      }

      if (!d_move) {
         yval = 400;
      } else {
         yval = 75;
      }

      xval = 500 + (75 * numcard);
      if (hidden_card) {
         g2.drawImage(cardupsidedown, xval, yval, null);
      } else {
         g2.drawImage(Picturecard[cardsuit.getValue()][cardrank], xval, yval, null);
      }
   }

   public void init_cards(Suits suit) {
      //init suite
      _suit.add(suit.toString());
      //init all ranks of all cards from ace to king
      _rank.put("Ace", 1);
      _rank.put("Two", 2);
      _rank.put("Three", 3);
      _rank.put("Four", 4);
      _rank.put("Five", 5);
      _rank.put("Six", 6);
      _rank.put("Seven", 7);
      _rank.put("Eight", 8);
      _rank.put("Nine", 9);
      _rank.put("Ten", 10);
      _rank.put("Jack", 10);
      _rank.put("Queen", 10);
      _rank.put("King", 10);

   }
}
