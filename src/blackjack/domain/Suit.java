package blackjack.domain;

/**
 * This enum defines the Suit of card.
 * 
 * @author Ken Tam
 *
 */
public enum Suit {
	SPADES("S", 3),
	HEARTS("H", 2),
	CLUBS("C", 1),
	DIAMONDS("D", 0);
	
	private final String name;
	private final int order;
	
	private Suit(String name, int order) {
		this.name = name;
		this.order = order;
	}

	public String getName() {
		return name;
	}

	public int getOrder() {
		return order;
	}
}
