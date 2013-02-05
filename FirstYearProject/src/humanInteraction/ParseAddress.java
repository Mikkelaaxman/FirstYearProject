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
public class ParseAddress {

	/**
	 * Constructor. Initializes input.
	 */
	public ParseAddress() {
	}

	/**
	 * The second and working try, where it's assumed that the information is input in the right order.
	 */
	public String parseAddress(String s) throws BadUserException {
		if(s.length() < 2) {
			throw new BadUserException("Invalid input");
		}
		System.out.println("Input: " + s);
		String[] output = new String[6];

		// Finds address and city
		Pattern p = Pattern.compile("[0-9,]|\\si\\s");
		String[] inputArray = p.split(s);
		output[0] = inputArray[0].trim();
		String numbers = "";
		if(s.length() != output[0].length()) {
			numbers = s.substring(output[0].length() + 1);
		}
		if(inputArray.length > 1) {
			output[5] = inputArray[inputArray.length - 1].trim();
			if(s.length() > output[0].length() + output[5].length() + 1) {
				numbers = s.substring(output[0].length() + 1, s.length() - (output[5].length() + 1));
			} else {
				numbers = "";
			}
		}

		// Finds all the stuff inbetween
		if(numbers.length() > 0) {
			//System.out.println(numbers);
			String[] numbersArray = numbers.split("[\\s,]");
			if(numbersArray[0].length() == 4) {
				output[4] = numbersArray[0];
			} else {
				Boolean end = false;
				int i = 0;
				for(i = 0; i < numbersArray.length && !end; i ++) {
					if(!numbersArray[i].equals("") && !numbersArray[i].equals(" ")) {
						output[1] = numbersArray[i];
						output[2] = "";
						end = true;
					}
				}
				output[4] = numbersArray[numbersArray.length - 1];
				while(i < numbersArray.length - 1) {
					if(output[3] == null) {
						output[3] = numbersArray[i];
					} else {
						output[3] += " " + numbersArray[i];
					}
					i++;
				}
				output[3] = output[3].trim();
			}
		}

		//Prints!
		String returnString = "";
		for(String str : output) {
			returnString += str + "#";
		}
		
		return returnString.substring(0, returnString.length() - 1);
	}

	/**
	 * The first try, where I tried to split the string up in
	 * all it's different parts, and match them against RegEx.
	 */
	/*public void test2() {
		System.out.println("Input: " + s);
		s = s.replaceAll(",", " ").toLowerCase();
		String[] inputArray = s.split("\\s");
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
	}*/

	/**
	 * Main method!
	 * @param args Arguments!
	 */
	public static void main(String[] args) throws BadUserException {
		ParseAddress a = new ParseAddress();
		System.out.println(a.parseAddress("H. C. Lundbyesvej 4A, 2300 Amager"));
	}
}
