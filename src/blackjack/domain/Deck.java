package blackjack.domain;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * This class implements a Deck of cards.
 * 
 * @author Ken Tam
 *
 */
public class Deck {
	// LinkedList is not as fast as ArrayDeque but provides shuffle which is
	//   more important, plus the total # of cards is small at 52
	List<Card> cards = new LinkedList<Card>();

	// Note: An Interface can be created for Deck to facilitate testing by
	//			creating a MockDeck class to simulate hands instead of
	//			the random deck in real game. Another option is to subclass
	//			from this Deck class to override methods to simulate hands.
	public Deck() {
		// initialize cards 
		for (Suit s: Suit.values()) {
			for (Rank r: Rank.values()) {
				cards.add(new Card(s,r));
			}
		}
		// shuffle cards after init
		this.shuffle();
	}
	
	public Card getFromTop() {
		if ( cards.isEmpty() ) {
			return null;
		}
		
		return cards.remove(0);
	}
	
	public void addToBottom(Card card) {
		cards.add(card);
	}
	
	public void shuffle() {
		Collections.shuffle(cards);
	}
}
