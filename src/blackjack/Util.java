package blackjack;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Scanner;

/**
 * Utility class for miscellaneous functions
 * 
 * @author Ken Tam
 *
 */
public class Util {
	public static String getUserInput(String prompt, Scanner scanner) {
		System.out.print(prompt);

		String action = scanner.nextLine();
		
		if ( null == action ) {
			return "";
		}
		
		return action.trim().toLowerCase();
	}
	
	public static String getUserInput(String prompt, BufferedReader userInput) {
		System.out.println(prompt);
		String line;
		try {
			line = userInput.readLine();
			if ( null != line ) {
				return line.trim().toLowerCase();
			}
			return "";
		} catch (IOException e) {
			return "";
		}
	}
}
