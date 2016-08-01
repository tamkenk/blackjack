package blackjack.domain;

/**
 * This enum defines the Rank of card.
 * 
 * @author Ken Tam
 *
 */
public enum Rank {
	ACE("A", 1),
	TWO("2", 2),
	THREE("3", 3),
	FOUR("4", 4),
	FIVE("5", 5),
	SIX("6", 6),
	SEVEN("7", 7),
	EIGHT("8", 8),
	NINE("9", 9),
	JACK("J", 10),
	QUEEN("Q", 10),
	KING("K", 10);
	
	private String name;
	private int value;
	

	private Rank(String name, int value) {
		this.name = name;
		this.value = value;
	}
	
	public String getName() {
		return name;
	}

	public int getValue() {
		return value;
	}	
}
