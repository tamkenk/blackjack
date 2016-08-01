package blackjack.domain;

/**
 * This class defines a playing card with focus on Blackjack - e.g. inclusion 
 * of isAce() method. A more generic Card class can be defined and subclass 
 * from for the same purpose.
 * 
 * @author Ken Tam
 *
 */
public class Card implements Comparable<Card> {
	private Suit suit;
	private Rank rank;
	
	public Card(Suit suit, Rank rank) {
		this.suit = suit;
		this.rank = rank;
	}

	public Suit getSuit() {
		return suit;
	}

	public Rank getRank() {
		return rank;
	}
	
	public int getValue() {
		return rank.getValue();
	}
	
	public boolean isAce() {
		return (1 == rank.getValue()); 
	}
	
	@Override
	public String toString() {
		return suit.getName() + ":" + rank.getName();
	}

	@Override
	public int compareTo(Card c) {
		if ( c.suit.getOrder() != this.suit.getOrder() ) {
			return this.suit.getOrder() - c.suit.getOrder();
		}

		return this.getValue() - c.getValue();
	}
}
