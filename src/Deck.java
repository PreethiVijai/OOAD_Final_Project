import java.util.Collections;
import java.util.ArrayList;
import java.util.List;

public class Deck {
  private static Deck deck_instance;
   private ArrayList<Card> allCards;
   
   /**  Make constructor private to implement Singleton pattern **/
   private Deck()  throws Exception {
	   allCards = new ArrayList<Card>(); 
	   for (Suits suit: Suits.values()) {
	         for (int value = 0; value < 13; value++) {
	            if (value == 0) {
	               Card cardval = new Card(suit, value, 11);
	               allCards.add(cardval);
	            } else {
	               if (value >= 10) {
	                  // IF the card is either A, K , Q or J, set it to 10
	                  Card cardval = new Card(suit, value, 10);
	                  allCards.add(cardval);
	               } else {
	                  Card cardval = new Card(suit, value, value + 1);
	                  allCards.add(cardval);
	               }
	            }
	         }
	   
   }
	   
   }
  
   /**** Singleton pattern implementation ****/
  synchronized public static Deck getInstance(){ // represents the entire deck of cards
	  if(deck_instance==null) {
		  try {
			deck_instance= new Deck();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
      }
      return deck_instance;
   }

   //get all cards or the entire deck
   public List<Card> getCards() {
      return this.allCards;
   }


   //Shuffle the cards of deck
   public void shufflecardDeck() {
      Collections.shuffle(allCards);
   }

   //this is just the same above method, but returns the deck
   public List<Card> shuffleDeck() {
       Collections.shuffle(allCards);
       return  allCards;
   }

   //get a single card to play / make the player move with
   public Card obtaincard(int i) {
      return allCards.get(i);
   }

   //make a move with the top card. Remove the card from deck
   public Card dealWithCurrentDeck() {
      Card returnThisCard = this.allCards.get(0);
      this.allCards.remove(0);
      return returnThisCard;
   }

   public Card discardcard(int i) {
      return allCards.remove(i);
   }
}
