import java.util.ArrayList;
import java.util.List;
import java.util.*;

//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;


public class Hand {
    //private final static Logger logger = LogManager.getLogger(Hand.class);
    private List<Card> cards = new ArrayList<Card>(10); //all cards in the hand
    private Integer total;//total value of all cards in hand
    private int cardsInHand;

    public int getCardsInHand() {
        this.cardsInHand = this.cards.size();
        return this.cardsInHand;
    }
    public Integer getTotal() {
        this.sumOfTheCards();
        return this.total;
    }
    public Integer sumOfTheCards() { //sum of cards in hand
        int sum = 0;
        for (Card card : this.cards) {
            sum += card.getRank();
        }
        this.total = sum;
        return sum;
    }

    // Check whether you have Ace - to add 1 or 11
    public boolean check_ace_hand(ArrayList<Card> h) {
    	/********* Iterator design pattern implemented *********/
    	Iterator itr = h.iterator();
    	int j=0;
    	while(itr.hasNext()) {
    		if(h.get(j).getValue() == 11) {
                return true;
            }
    		j++;
    		itr.next();
    	}
        return false;
    }

    // Count the number of Aces in hand
    public int ace_cnt_hand(ArrayList<Card> h) {
        int count_no_ace = 0;
        /********* Iterator design pattern implemented *********/
        Iterator itr = h.iterator();
    	int j=0;
    	while(itr.hasNext()) {
    		if (h.get(j).getValue() == 11) {
                count_no_ace++;
            }
    		j++;
    		itr.next();
    	}
        return count_no_ace; //we then return this ace count.
    }

    // Get sum of highest ace
    public int high_ace_get_sum(ArrayList<Card> h) {
        int get_sum = 0;
        /********* Iterator design pattern implemented *********/
        Iterator itr = h.iterator();
    	int val=0;
    	while(itr.hasNext()) {
    		get_sum = get_sum + h.get(val).getValue();
    		val++;
    		itr.next();
    	}
        return get_sum;
    }

    // Generate additional Hand
    public int gen_add_hand(ArrayList<Card> h) {
        if (check_ace_hand(h)) {
            // If sum less than 21, return highest sum
            if (high_ace_get_sum(h) <= 21) {
                return high_ace_get_sum(h);
            } else {
                for (int index = 0; index < ace_cnt_hand(h); index++) {
                    int hand_sum = high_ace_get_sum(h) - (index + 1) * 10;
                    if (hand_sum <= 21) {
                        return hand_sum;
                    }
                }
            }
        } else {
            int hand_sum = 0;
            /********* Iterator design pattern implemented *********/
            Iterator itr = h.iterator();
        	int q=0;
        	while(itr.hasNext()) {
        		hand_sum = hand_sum + h.get(q).getValue();
        		q++;
        		itr.next();
        	}
            return hand_sum;
        }
        return 22;
    }
}
