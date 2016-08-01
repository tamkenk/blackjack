package blackjack.domain;

/**
 * This class implements the dealer.
 * 
 * @author Ken Tam
 *
 */
public class Dealer extends Player {
	private static final int DEALER_LIMIT = 17;
	
	public Dealer(Deck deck) {
		super(deck);
	}

	/**
	 * Method to draw card(s) until total is 17 or greater but continues to draw at soft 17.
	 * @return	false if limit has been exceeded (bust). Otherwise, return true.
	 */
	@Override
	public boolean draw() {
		// dealer must draw card until total is 17+ or at soft 17
		while ( hand.getBestValue() < DEALER_LIMIT || 
				(hand.getAceCount() > 0 && DEALER_LIMIT == hand.getBestValue()) ) {
			this.addToHand();
		}

		return !this.isBust();
	}
}
