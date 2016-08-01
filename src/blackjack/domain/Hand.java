package blackjack.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * This class implements a hand of cards.
 * 
 * @author Ken Tam
 *
 */
public class Hand {
	private final static String EMPTY = "empty";
	private final static int ACE_CUTOFF = 11;
	private final static int ACE_UPLIFT = 10;
	private static final int TWENTY_ONE = 21;	
	private List<Card> hand;
	private int aceCount;
	
	public Hand() {
		hand = new ArrayList<Card>();
		aceCount = 0;
	}
	
	public void addToHand(Card card) {
		hand.add(card);
		if ( card.isAce() ) {
			aceCount++;
		}
	}
	
	public void clearHand() {
		hand.clear();
		aceCount = 0;
	}
	
	public int getValue() {
		int value = 0;
		
		for (Card c: hand) {
			value += c.getValue();
		}
		return value;
	}
	
	public int getBestValue() {
		int value = getValue();
		
		if ( value <= ACE_CUTOFF && getAceCount() > 0 ) {
			value += ACE_UPLIFT;
		}
		
		return value;
	}
	
	public int getAceCount() {
		return aceCount;
	}

	public boolean isBlackjack() {
		return ( TWENTY_ONE == getBestValue());
	}
	
	public boolean isBust() {
		return ( TWENTY_ONE < getBestValue());
	}
	
	public Card getCard(int index) {
		return (index < hand.size()) ? hand.get(index) : null;
	}
	
	@Override
	public String toString() {
		if ( hand.isEmpty() ) {
			return EMPTY;
		}
		StringBuilder sb = new StringBuilder();
		sb.append(hand.get(0));
		for (int i=1; i < hand.size(); i++) {
			sb.append(", ");
			sb.append(hand.get(i).toString());
		}
		
		return sb.toString();
	}
}
