package blackjack.domain;

/**
 * Abstract class which provides common functions for player.
 * 
 * @author Ken Tam
 *
 */
public abstract class Player {
	protected Deck deck;
	protected Hand hand;
	
	public Player(Deck deck) {
		this.deck = deck;
		this.hand = new Hand();
	}
	
	public void addToHand() {
		hand.addToHand(deck.getFromTop());
	}
	
	public String displayHand() {
		return hand.toString();
	}
	
	public int getBestValue() {
		return hand.getBestValue();
	}
	
	public boolean isBust() {
		return hand.isBust();
	}
	
	public boolean isBlackjack() {
		return hand.isBlackjack();
	}
	
	public Card getCard(int index) {
		return hand.getCard(index);
	}
	
	/**
	 * Abstract method to draw card(s) until stopped.
	 * @return	false if limit has been exceeded (bust). Otherwise, return true.
	 */
	public abstract boolean draw();
}
