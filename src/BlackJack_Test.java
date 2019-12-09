import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class BlackJack_Test {
		@Test
		void test1() throws Exception{
			  Card card1 = new Card(Suits.Diamonds, 1, 1);
		      Card card2 = new Card(Suits.Hearts, 11, 11);
		      Card card3 = new Card(Suits.Hearts, 13, 13);

		      assertEquals(card1.getRank(), 1); //Ace
		      assertEquals(card2.getRank(), 10); //JAck
		      assertEquals(card3.getRank(), 10); //King

		/*
		 * assertEquals(card1.getValue(), 1); assertEquals(card2.getValue(), 11); //
		 * Jack : card value is set as 10 by default assertEquals(card3.getValue(), 13);
		 * // King: card value is set as 10 by default
		 */		}
		@Test 
		void test2() throws Exception{
			  Deck deck = Deck.getInstance();
		      assertEquals(deck.getCards().size(), 52); //create all 52 cards
		}
		@Test
		void test3() throws Exception{
			  Deck deck = Deck.getInstance();
		      assertEquals(deck.getCards().size(), 52); //create all 52 cards
		      Card card = deck.getCards().get(0);
		      deck.shufflecardDeck();
		      assertNotSame(card, deck.getCards().get(0)); //shuffledDeck
		      assertEquals(deck.getCards().size(), 52); //shuffled all 52 cards
		}
		@Test
		void test4() throws Exception{
			Suits c = Suits.Clubs;
			assertEquals(c.getValue(), 0);

		}
		
}
