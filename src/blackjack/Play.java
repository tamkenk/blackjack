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
				// tie game is not counted as game played
				System.out.printf("with winning percentage: %.2f%%\n",  
					(((gameCount-tieCount) > 0) ? ((float)winCount/(gameCount-tieCount)) * 100f : 0f));
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
			System.out.println("shuffling cards");
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
		boolean result = user.draw();
		Result returnVal = Result.LOSE;
		
		if ( ! result ) {
			System.out.println("Your hand exceeded 21");
			returnVal = Result.LOSE;
		}
		else {
			// dealer's play
			System.out.printf("Dealer's hand: %s, total value %d\n", dealer.displayHand(), dealer.getBestValue());
			result = dealer.draw();
			System.out.printf("Dealer's final hand: %s, total value %d\n", dealer.displayHand(), dealer.getBestValue());
			// compare hands
			if ( ! result ) {
				System.out.println("Dealer's hand exceeded 21");
				returnVal = Result.WIN;
			}
			else if ( dealer.getBestValue() > user.getBestValue() ) {
				returnVal = Result.LOSE;
			}
			else if ( dealer.getBestValue() < user.getBestValue() ) {
				returnVal = Result.WIN;
			}
			else {
				returnVal = Result.TIE;
			}
		}		
		// return cards to deck
		user.returnCardsToDeck();
		dealer.returnCardsToDeck();
		
		return returnVal;
	}
}
