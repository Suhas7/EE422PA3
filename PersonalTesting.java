package assignment3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class PersonalTesting {
	public static void main(String[] args) {

//		String word = "Hello";
//		word = word.toUpperCase();
//	
//		int wordLength = word.length();
//		String new_word;
//		
//		ArrayList<Character> alphabet = new ArrayList<Character>();
//		alphabet.add('A');
//		alphabet.add('B');
//		alphabet.add('C');
//
//		Set<String> dict = makeDictionary();
//		
//		for (int j = 0; j < wordLength; j++) {
//			for (int i = 0; i < 3; i++) {
//				new_word = word.substring(0, j) + alphabet.get(i) + word.substring(j+1, wordLength);
//				System.out.println(new_word);
//				
//				if(dict.contains(new_word)) {
//					System.out.println("^ is a real word");
//				}
//				
//			}
//		}
//		
//		if(word.contains("H")) {
//			System.out.println("Yes there is an H");
//		}
		
//		for(int i=0; i<2; i++) {
//			for(int j =0; j<5; j++) {
//				if(j==3) {
//					continue;
//				}
//				System.out.println(j);
//			}
//		}


		getWordLadderDFS("Stone", "MONEY");
	}


	public static ArrayList<String> getWordLadderDFS(String start, String end) {

//		char[] alphabet = global.alphabet;
//		List<String> visited = Global.visitedstatic List<String> visited;
//		static List<String> path;
		
		start = start.toUpperCase();
		int wordLength = start.length();
		String newWord;
		Set<String> dict = makeDictionary();
		
		for (int j = 0; j < wordLength; j++) {
			for (int i = 0; i < 26; i++) {
				
				newWord = start.substring(0, j) + Global.alphabet[i] + start.substring(j+1, wordLength);
				
				// check if newWord is not a real word
				if(!(dict.contains(newWord))) {
					continue;
				}
				
				// check if answer
				if(newWord.equals(end)) {
					// add newWord to path
					// return path
					System.out.println("You found the answer!");
				}
				
				// check if not visited
				if(!(Global.visited.contains(newWord))) {
					Global.visited.add(newWord); // mark the new word as visited
					getWordLadderDFS(newWord, end);
				}
				

			}
		}
		// if you are here then all combinations of newWord are not real words, not the answers, and have been visited
		String lastWord = Global.visited.get(Global.visited.size()-1); 
		getWordLadderDFS(lastWord, end);
		
		
		
		
		// Returned list should be ordered start to end. Include start and end.
		// If ladder is empty, return list with just start and end.
		// TODO some code
		
		// TODO more code

		return null; // replace this line later with real return
	}

	/* Do not modify makeDictionary */
	public static Set<String> makeDictionary() {
		Set<String> words = new HashSet<String>();
		Scanner infile = null;
		try {
			infile = new Scanner(new File("five_letter_words.txt"));
		} catch (FileNotFoundException e) {
			System.out.println("Dictionary File not Found!");
			e.printStackTrace();
			System.exit(1);
		}
		while (infile.hasNext()) {
			words.add(infile.next().toUpperCase());
		}
		return words;
	}
	
	
}
