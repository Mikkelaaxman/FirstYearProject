package humanInteraction;

import java.util.regex.*;

/**
 * A class that tries to understand an address string that a user writes, 
 * and parses it into an array, to be used by the map.
 * The implementation assumes that the user inputs things 
 * in the normal order (road, number, floor, zip, city).
 * The implementation needs a number, a "," or an " i " to 
 * function properly. Otherwise it cannot tell what is
 * road and what is city.
 * @author jakobvase
 * @version 1.0, 30/1-2013
 */
public class AddressFilter {
	private String input;

	/**
	 * Constructor. Initializes input.
	 */
	public AddressFilter() {
		input = "H. C. Lundbyes vej 23, 4. tv, 2500 Valby";
	}

	/**
	 * The second and working try, where it's assumed that the information is input in the right order.
	 */
	public void test() throws BadUserException {
		if(input.length() < 2) {
			throw new BadUserException("Invalid input");
		}
		System.out.println("Input: " + input);
		String[] output = new String[5];

		// Finds address and city
		Pattern p = Pattern.compile("[0-9,]|\\si\\s");
		String[] inputArray = p.split(input);
		output[0] = inputArray[0].trim();
		String numbers = "";
		if(input.length() != output[0].length()) {
			numbers = input.substring(output[0].length() + 1);
		}
		if(inputArray.length > 1) {
			output[4] = inputArray[inputArray.length - 1].trim();
			if(input.length() > output[0].length() + output[4].length() + 1) {
				numbers = input.substring(output[0].length() + 1, input.length() - (output[4].length() + 1));
			} else {
				numbers = "";
			}
		}

		// Finds all the stuff inbetween
		if(numbers.length() > 0) {
			//System.out.println(numbers);
			String[] numbersArray = numbers.split("[\\s,]");
			if(numbersArray[0].length() == 4) {
				output[3] = numbersArray[0];
			} else {
				Boolean end = false;
				int i = 0;
				for(i = 0; i < numbersArray.length && !end; i ++) {
					if(!numbersArray[i].equals("") && !numbersArray[i].equals(" ")) {
						output[1] = numbersArray[i];
						end = true;
					}
				}
				output[3] = numbersArray[numbersArray.length - 1];
				while(i < numbersArray.length - 1) {
					if(output[2] == null) {
						output[2] = numbersArray[i];
					} else {
						output[2] += " " + numbersArray[i];
					}
					i++;
				}
				output[2] = output[2].trim();
			}
		}

		//Prints!
		for(String s : output) {
			System.out.println(s);
		}
		System.out.println("Dansesmølf");
	}

	/**
	 * The first try, where I tried to split the string up in
	 * all it's different parts, and match them against RegEx.
	 */
	public void test2() {
		System.out.println("Input: " + input);
		input = input.replaceAll(",", " ").toLowerCase();
		String[] inputArray = input.split("\\s");
		for(String s : inputArray) {
			System.out.println(s);
		}
		String[] output = new String[5];
		int postcodeIndex = -1, roadnameIndex = -1, houseNumberIndex = -1, cityIndex = -1, floorIndex = -1;
		for(int i = 0; i < inputArray.length; i++) {
			String s = inputArray[i];
			if(s.matches("\\d{4}")) {
				postcodeIndex = i;
				output[3] = s;
			} else if(s.matches("vej|gade|boulevard")) {
				roadnameIndex = i;
				if(true) {
					output[0] = s;
				}
			} else if(s.matches("[.]")) {
				floorIndex = i;
				output[2] = s;
			} else if(s.matches("\\d")) {
				houseNumberIndex = i;
				output[1] = s;
			} else if(s.matches("by|strup|r��d|kbh|havn|k��bing|borg|nedre|��vre|sm��rum|paris|rom|bjerget|b��k|mark|felt|feldt|feld|hus|vig|minde|odense|r��nne|lem") && cityIndex < 0) {
				cityIndex = i;
				output[4] = s;
			}
		}
		for(String s : output) {
			System.out.println(s);
		}
	}

	/**
	 * Main method!
	 * @param args Arguments!
	 */
	public static void main(String[] args) throws BadUserException {
		AddressFilter a = new AddressFilter();
		a.test();
	}
}
