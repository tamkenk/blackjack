package blackjack;

import java.util.Scanner;

import blackjack.domain.Dealer;
import blackjack.domain.Deck;
import blackjack.domain.Hand;
import blackjack.domain.Player;
import blackjack.domain.User;

/**
 * This class implements a basic Blackjack card game with these rules: <p><ul>
 * <li>Only one player and dealer</li>
 * <li>Single Deck</li>
 * <li>Deck is shuffled every 6 rounds</li>
 * <li>Player is not allowed to split cards</li>
 * <li>Winning percentage will be displayed at end of game</li>
 * </ul>
 * <p>
 * The project organizes data model classes under the domain package. A 
 * model-view-controller pattern can be adopted if more advance UI (the view part) 
 * is needed. 
 * 
 * @author Ken Tam
 *
 */
public class Play {
	enum Result {
		WIN, LOSE, TIE;
	}
	private static final int SHUFFLE_INTERVAL = 6;
	
	private Scanner userInput;
	private int gameCount;
	private int winCount;
	private int tieCount;
	private Deck deck;
	
	/**
	 * Main entry point method.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Scanner userInput = new Scanner(System.in);
		
		try {
			Play play = new Play(userInput);
			
			play.playGame();
			System.exit(0);
		}
		catch (Exception e) {
			e.printStackTrace(System.err);
			System.exit(-1);
		}
		finally {
			userInput.close();
		}
	}
	
	/**
	 * Class to start the game.
	 * 
	 * @param userInput	for collecting input from player
	 */
	public Play(Scanner userInput) {
		this.userInput = userInput;
		gameCount = 0;
		winCount = 0;
		tieCount = 0;
		deck = new Deck();		
	}
	
	/**
	 * Method to play the game.
	 */
	public void playGame() {		
		System.out.println("Welcome to Blackjack game");
		
		boolean done = false;
		while ( !done) {
			String action = Util.getUserInput("Play game? (y<cr> or n<cr>) ", userInput);

			switch (action) {
			case "y":
				gameCount++;
				switch (oneGame()) {
				case WIN:
					System.out.println("You win!");
					winCount++;
					break;
				case LOSE:
					System.out.println("You lose!");
					break;
				case TIE:
					System.out.println("You tie!");
					tieCount++;
					break;
				}
				break;
			case "n":
				System.out.println("Thank you for playing");
				System.out.printf("Number of games played: %d, win: %d, lose: %d, tie: %d, ", 
					gameCount, winCount, (gameCount-winCount-tieCount), tieCount);
				// tie game is considered a half win 
				System.out.printf("with winning percentage: %.2f%%",  
					((gameCount > 0) ? ((winCount+((float)tieCount/2.0))/gameCount) * 100 : 0f));
				done = true;
				break;
			}
		}
	}

	/**
	 * Play one game and return result
	 * @return the result of game played - WIN, LOSE or TIE
	 */
	private Result oneGame() {
		System.out.printf("Game: %d\n", gameCount);
		
		// need to shuffle deck every <n> game
		if ( 0 == (gameCount % SHUFFLE_INTERVAL) ) {
			deck = new Deck();
			deck.shuffle();
		}

		Player user = new User(deck, userInput);
		Player dealer = new Dealer(deck);
		
		// deal 2 cards to each player in 2 rounds
		user.addToHand();
		dealer.addToHand();
		user.addToHand();
		dealer.addToHand();
		
		System.out.printf("Dealer's first card: %s\n", dealer.getCard(0));
		
		// user's play
		boolean ret = user.draw();
		if ( ! ret ) {
			System.out.println("Your hand exceeded 21");
			return Result.LOSE;
		}
		if ( user.isBlackjack() ) {
			System.out.println("You have a blackjack hand!");
		}
		
		// dealer's play
		System.out.printf("Dealer's hand: %s, total value %d\n", dealer.displayHand(), dealer.getBestValue());
		ret = dealer.draw();
		System.out.printf("Dealer's final hand: %s, total value %d\n", dealer.displayHand(), dealer.getBestValue());
		// compare hands
		if ( ! ret ) {
			System.out.println("Dealer's hand exceeded 21");
			return Result.WIN;
		}
		else if ( dealer.getBestValue() > user.getBestValue() ) {
			return Result.LOSE;
		}
		else if ( dealer.getBestValue() < user.getBestValue() ) {
			return Result.WIN;
		}
		else {
			return Result.TIE;
		}
	}
	
	@SuppressWarnings("unused")
	private Result oneGameOLD() {
		System.out.printf("Game: %d\n", gameCount+1);
		
		// need to shuffle deck every <n> game
		if ( 0 == ((gameCount+1) % SHUFFLE_INTERVAL) ) {
			deck.shuffle();
		}
		
		Hand dealerHand = new Hand();
		Hand userHand = new Hand();
		
		// deal 2 cards to each player in 2 rounds
		userHand.addToHand(deck.getFromTop());
		dealerHand.addToHand(deck.getFromTop());
		userHand.addToHand(deck.getFromTop());		
		dealerHand.addToHand(deck.getFromTop());
		
		System.out.printf("Dealer's first card: %s\n", dealerHand.getCard(0));
		
		// check for blackjack
		if ( dealerHand.isBlackjack() ) {
			if ( userHand.isBlackjack() ) {
				System.out.println("Both hands have blackjack");
				return Result.TIE;
			}
			System.out.printf("Dealer's hand: %s\n", dealerHand );
			System.out.println("Dealer has blackjack");
			return Result.LOSE;
		}
		if ( userHand.isBlackjack() ) {
			if ( dealerHand.isBlackjack() ) {
				System.out.println("Both hands have backjack");
				return Result.TIE;
			}						
			System.out.printf("Your hand: %s\n", userHand );
			System.out.println("You have blackjack");
			return Result.WIN;
		}
		
		boolean done = false;
		while (! done) {
			if ( userHand.isBust() ) {
				System.out.printf("Sorry, your hand: %s, total value: %d exceeds 21\n", userHand, userHand.getBestValue());	
				return Result.LOSE;
			}
			
			System.out.printf("Your hand: %s, total value %d\n", userHand, userHand.getBestValue());
			
			String action = Util.getUserInput("Hit? (y<cr>) or n<cr>) ", userInput);			
			switch(action) {
			case "y":
				userHand.addToHand(deck.getFromTop());
				break;
			case "n":
				done = true;
				break;
			}		
		}
		
		System.out.println("You are done");
		System.out.printf("Dealer's hand: %s, total value %d\n", dealerHand, dealerHand.getBestValue());
		// dealer must draw card until total is 17+ or at soft 17
		while ( dealerHand.getBestValue() <= 16 || 
				(dealerHand.getAceCount() > 0 && 17 == dealerHand.getBestValue()) ) {
			dealerHand.addToHand(deck.getFromTop());
		}
		System.out.printf("Dealer's final hand: %s, total value %d\n", dealerHand, dealerHand.getBestValue());
		// compare hands
		if ( dealerHand.isBust() ) {
			System.out.println("Dealer's hand exceeds 21");
			return Result.WIN;
		}
		else if ( dealerHand.getBestValue() > userHand.getBestValue() ) {
			return Result.LOSE;
		}
		else if ( dealerHand.getBestValue() < userHand.getBestValue() ) {
			return Result.WIN;
		}
		else {
			return Result.TIE;
		}
	}
}
