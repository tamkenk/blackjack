package blackjack.domain;

import java.util.Scanner;

import blackjack.Util;

/**
 * This class implements the user as a player.
 * 
 * @author Ken Tam
 *
 */
public class User extends Player {
	private Scanner userInput; 
	
	public User(Deck deck, Scanner userInput) {
		super(deck);
		this.userInput = userInput;
	}

	/**
	 * Method to draw card(s) until stopped by user or bust.
	 * @return	false if limit has been exceeded (bust). Otherwise, return true.
	 */
	@Override
	public boolean draw() {
		boolean done = false;
		while (! done) {
			System.out.printf("Your hand: %s, total value %d\n", this.displayHand(), this.getBestValue());

			if ( this.isBust() ) {
				return false;
			}
			if ( this.isBlackjack() ) {
				return true;
			}
						
			String action = Util.getUserInput("Hit? (y<cr>) or n<cr>) ", userInput);			
			switch(action) {
			case "y":
				this.addToHand();
				break;
			case "n":
				done = true;
				break;
			}		
		}

		return true;
	}
}
