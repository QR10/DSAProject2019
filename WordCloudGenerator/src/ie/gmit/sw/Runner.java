package ie.gmit.sw;

import java.io.*;
import java.util.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import static java.util.stream.Collectors.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Runner {

	// object console to get input
	static Scanner console = new Scanner(System.in);
	static private Map<String, Integer> frequency = new HashMap<>();
	private static Scanner user;

	public static void main(String[] args) throws IOException {

		user = new Scanner( System.in );

		String fileName;
		String searchKey = "";

		int numOfWords = 10;
		int option = 0;
		
		// User Menu
		do {
			System.out.println("\n============== Welcome =============");
			System.out.println("  Please enter: ");
			System.out.println("  1 - To enter a file name");
			System.out.println("  2 - To specify max number of words(default = 10, max = 10, min = 1)");

			System.out.println("\n  9 - To exit and output words to PNG");
			System.out.println("=============================================");

			System.out.print("\nEnter option number: ");
			option = console.nextInt();
			
			switch (option) {
			case 1:
				// prepare the input file
		        System.out.print("Enter file name: ");
		        fileName = user.nextLine().trim();
		        fileName += ".txt"; // Adding .txt file extension
				readBook(fileName, searchKey);
				break;
			case 2:
				do {
					System.out.println("Enter the num of words to output to the file (min 0, max 10): ");
					numOfWords = console.nextInt();
				} while (!(numOfWords > 0 && numOfWords < 11));
				break;
			case 9:
				System.out.println("\nOutputing PNG...");
				outputPNG(numOfWords);
				
				System.out.println("\nFinished...");
				System.exit(0);
				break;

			default: // default - Invalid option
				System.out.printf("Invalid entry - only 1, 2 or 9 allowed - please try again%n");
			}

		} while (!(option == 9));
	}// main

	
	// Counts number of lines and returns total number as an int
	// Progress bar stuff
	public static int countLines(String fileName) throws IOException {
		LineNumberReader reader = null;
		try {
			reader = new LineNumberReader(new FileReader(fileName));
			while ((reader.readLine()) != null)
				;
			return reader.getLineNumber();
		} catch (Exception ex) {
			return -1;
		} finally {
			if (reader != null)
				reader.close();
		}
	}
	
	// Reads a file line by line and stores it in a hashmap 
	static void readBook(String fileName, String searchKey) throws IOException {

		// Instantiate class FileParser
		FileParser parser = new FileParser();

		// Reference name for the file ignore words
		BufferedReader readerIgnoreWords = new BufferedReader(new FileReader(new File("./ignorewords.txt")));

		// Reference name for the file select in menu
		BufferedReader readerBook = new BufferedReader(new FileReader(new File(fileName)));

		String inputLineIgnoreWords = null;
		String inputLineBook = null;
		int lineCounter = 0;
		
		// load the file ignorewords
		while ((inputLineIgnoreWords = readerIgnoreWords.readLine()) != null) {
			parser.addIgnoreWord(inputLineIgnoreWords);
		}

		int lines = countLines(fileName); // Count how many lines are in the file
		String progessBar = "";

		// print Header
		System.out.println("\nLoading the book " + fileName);
		System.out.println("|-----------------------------------------------------|");

		// load the file book into a HashMap
		while ((inputLineBook = readerBook.readLine()) != null) {

			lineCounter++; // Count the line

			// Count page for each 40 lines
			if (lineCounter % 40 == 0) {
			}

			// print quotes for progress bar each 10 lines
			if (lineCounter % (lines / 10) == 0) { // the total lines was take
													// countLines(fileName)
				progessBar += "*"; // and divide by 10
				System.out.print(progessBar); // print screen the progress bar
			}
			
			// takes each word separate by space
			String[] words = inputLineBook.split(" "); 										

			// Ignore empty lines.
			if (inputLineBook.equals("")) {
				continue;
			}

			// read into string array each word
			for (int i = 0; i < words.length; i++) {

				String word = new String();
				word = words[i];

				// Remove any commas and dots.
				word = word.replace(".", "");
				word = word.replace(",", "");
				word = word.replace("\"", "");
				word = word.replace(";", "");
				word = word.replace(" ", "");
				word = word.replace("-", "");
				word = word.replace("--", "");
				word = word.replace("?", "");
				word = word.replace("!", "");
				word = word.replace("[", "");
				word = word.replace("]", "");
				word = word.replace("(", "");
				word = word.replace(")", "");
				word = word.replace("\"", "");
				word = word.replace("{", "");
				word = word.replace("}", "");
				
				// verify each word and if word exist into the list ignoreword,
				// if yes, this word will be skipped
				if (!(parser.getIgnoreWord().contains(word))) {
					if(frequency.containsKey(word)) {	// if found increment frequency only (integer)
						frequency.put(word.toLowerCase(),frequency.get(word) + 1);
					}
					else {							// if new process the word
						frequency.put(word.toLowerCase(), 1);
					}
				}
				else {
				}
			}

		}// while

		readerBook.close();
		readerIgnoreWords.close();
	}// ReadBook method
	
	// Sorts words in descending order and outputs the a number of the more used words to a PNG file
	public static void outputPNG(int maxWords) throws IOException {	
		 
		// Image declarations
		Font font = new Font(Font.SANS_SERIF, Font.BOLD, 62);
	    BufferedImage image = new BufferedImage(1000, 720, BufferedImage.TYPE_4BYTE_ABGR);
	    Graphics graphics = image.getGraphics();
	    graphics.setColor(Color.black);
	    graphics.setFont(font);
		
		// variables
	    int i = 0;
	    String[] printWords = new String[maxWords];
	    int[] teste = new int[maxWords];
	    int posX = 10, posY = 150;
	    int newSize = 0;
	    
	    
	    // Sorting values in descending order 
	    Map<String, Integer> sorted = frequency
	        .entrySet()
	        .stream()
	        .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
	        .collect(
	            toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
	                LinkedHashMap::new));
	    
	    // Stores the n most used keys (words) in a string array and outputs them to a png file
	    for ( String key : sorted.keySet() ) {   	
	    	if (i < maxWords)
	    	{
	    		// Moves words to a second column
	    		if(i==5)
	    		{
	    			posX =600;
	    			posY=150;
	    		}
	    		
	    		// Storing keys to strings and incrementing positions
	    		printWords[i] = key;
	    		posX = posX + 10;
	    		posY = posY + 100;
	    		teste[i] = sorted.get(key);
	    		newSize = (int) (0.10*teste[i]);	    		
	    		
	    		// Drawing strings on png image
	    		font = new Font(Font.SANS_SERIF, Font.ITALIC, newSize);
	    		graphics.setFont(font);
	    		graphics.drawString(printWords[i], posX, posY);		
	       	}//if
	    	// Breaks the loop after the n words were stored an outputed
	    	else if (i == maxWords)
	    		break;
	    	//incrementing i
	    	i++;
	    }// for
	       
	    graphics.dispose();
	    ImageIO.write(image, "png", new File("image.png"));
	}// OutputPng method
}



