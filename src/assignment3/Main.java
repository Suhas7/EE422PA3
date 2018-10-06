/* WORD LADDER Main.java
 * EE422C Project 3 submission by
 * Replace <...> with your actual data.
 * <Student1 Name>
 * <Student1 EID>
 * <Student1 5-digit Unique No.>
 * <Student2 Name>
 * <Student2 EID>
 * <Student2 5-digit Unique No.>
 * Slip days used: <0>
 * Git URL:
 * Fall 2018
 */

package assignment3;
import java.util.*;
import java.io.*;
import java.util.LinkedList;
import java.util.Queue;

public class Main {
	public static ArrayList<String> input; //stores the words input by user
	public static Scanner kb;	// input Scanner for commands

	// Static for DFS
	static ArrayList<String> stack = new ArrayList<String>();
	static ArrayList<String> visited = new ArrayList<String>();
	static Set<String> dict = Main.makeDictionary(); // check it is okay to create this here
	static char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

	
	public static void main(String[] args) throws Exception {
		PrintStream ps;	// output file, for student testing and grading only
		// If arguments are specified, read/write from/to files instead of Std IO.
		if (args.length != 0) {
			kb = new Scanner(new File(args[0]));
			ps = new PrintStream(new File(args[1]));
			System.setOut(ps);			// redirect output to ps
		} else {
			kb = new Scanner(System.in);// default input from Stdin
			ps = System.out;			// default output to Stdout
		}
		initialize();
		
		input = parse(kb);
		if(input.size()==0){
			return;
		}
		
		if(input.size()==0){
			return;
		}
		// BFS
		//printLadder(getWordLadderBFS(input.get(0),input.get(1)));
		// DFS
		// printLadder(getWordLadderDFS(input.get(0), input.get(1)));
	}

	public static void initialize() {
//		input = parse(kb);
//		if(input.size()==0){
//			return;
//		}
	}

	/**
	 * @param keyboard Scanner connected to System.in
	 * @return ArrayList of Strings containing start word and end word.
	 * If command is /quit, return empty ArrayList.
	 */
	public static ArrayList<String> parse(Scanner keyboard) {
		ArrayList<String> output = new ArrayList<String>(2);
		String currString = keyboard.next();
		if(currString.equals("/quit")){return output;}
		output.add(currString);
		output.add(keyboard.next());
		return output;
	}

	public static ArrayList<String> getWordLadderDFS(String start, String end) {
		String startUpperCase = start.toUpperCase(); // convert inputs to upper case
		String endUpperCase = end.toUpperCase();
		ArrayList<String> noSolutionReturn = new ArrayList<String>(); // if no word ladder, returns lists of only start and end
		noSolutionReturn.add(start);
		noSolutionReturn.add(end);
		if(!(dict.contains(endUpperCase)) || !(dict.contains(startUpperCase))) { // check for valid inputs
			return null;
		}
		if(!(dfsHelper(startUpperCase, endUpperCase))) { // Recursive function call
			stack = noSolutionReturn; // return start and end if no word ladder is found
		}
		return stack; // return the stack if a word ladder is found
	}


	public static boolean dfsHelper(String start, String end) {
		// stone and money should return a 3252 word ladder
		// aloof and money should not find a word ladder
		// for testing:
		int startLength = start.length(); // length of start word
		String newWord; // child of start word
		stack.add(start.toLowerCase()); // push start to stack
		visited.add(start); // mark start as visited
		if(stack.get(stack.size()-1).equals(end.toLowerCase())) { // base case: top of stack is the end word
			return true;
		}
		// loop through all children of start
		for(int j=0; j<startLength; j++) {
			for(int i=0; i<26; i++) {
				newWord = start.substring(0, j) + alphabet[i] + start.substring(j+1, startLength);
				// if the child word has not been visited
				if(dict.contains(newWord) && !(visited.contains(newWord))) {
					if(dfsHelper(newWord, end)) {
						return true;
					}
					else {
						stack.remove(stack.size()-1);
					}

				}
			}
		}
		// if you have fallen out of the above loop, then there are no more children, and you need to backtrack
		return false;
	}

	public static ArrayList<String> getWordLadderBFS(String start, String end) {
		start = start.toUpperCase(); //clean input
		end = end.toUpperCase();
		Set<String> dict = makeDictionary(); //generate dict
		Set<String> visited = new HashSet<String>(); //create set for logging visited
		Map<String, String> edgeMap = new HashMap<String, String>(); //map stores child -> parent pairs
		Queue<String> nodeQueue = new LinkedList<>(); //create queue for which nodes to work through, BFS
		nodeQueue.add(start);//seed initial conditions
		String curr=start;
		visited.add(curr);
		boolean ladderExists=false;
		endFound: //artificially stop traversing once end is found, label to jump to
		while(!nodeQueue.isEmpty() && !curr.equals(end)) {
			curr=((LinkedList<String>) nodeQueue).pop();
			for (int i = 0; i < curr.length(); i++) {  //explore all child variants
				for (char j = 'A'; j <= 'Z'; j++) {
					String tmpVariation = curr.substring(0,i)+String.valueOf(j)+curr.substring(i+1);
					if(dict.contains(tmpVariation) && !visited.contains(tmpVariation)){
						nodeQueue.add(tmpVariation); //build queue w next children
						visited.add(tmpVariation);  //log as visited
						edgeMap.put(tmpVariation,curr); //store variation/parent pair in map
						if(tmpVariation.equals(end)){ //if you reached the end, artificially break loop
							curr=end;
							ladderExists=true;
							break endFound;
						}
					}
				}
			}
		}
		if(!ladderExists){
			ArrayList<String> outputLadder =  new ArrayList<String>(2);
			outputLadder.add(start.toLowerCase());
			outputLadder.add(end.toLowerCase());
			return outputLadder;
		}
		Stack<String> finalLadder = new Stack<String>();
		while(!curr.equals(start)){    //traverse edgemap to get to beginning, push to stack
			finalLadder.push(curr);
			curr=edgeMap.get(curr);
		}
		finalLadder.push(start);
		ArrayList<String> outputLadder =  new ArrayList<String>(finalLadder.size());
		int i = 0;
		while(!finalLadder.isEmpty()){  //convert stack to returnable array
			outputLadder.add(finalLadder.pop().toLowerCase());
			//System.out.println(outputLadder.get(i));  //debugging to see final list
			i++;
		}
		if(outputLadder.size()==1){
			outputLadder.add(end); //adds the end word if no ladder was ever found
		}
		return outputLadder;
	}

	private int getDiffLetters(String a, String b){
		if(a.length()!=b.length()){
			return -1;
		}
		int out = 0;
		for(int i = 0;i<a.length();i++){
			if(a.charAt(i)!=b.charAt(i)){
				out++;
			}
		}
		return out;
	}

	public static void printLadder(ArrayList<String> ladder) {
		int n = ladder.size()-2;
		if(n==0) {
			System.out.println("no word ladder can be found between " + ladder.get(0).toLowerCase() + " and " + ladder.get(1).toLowerCase());
			return;
		}
		System.out.println("a " + String.valueOf(n) + "-rung word ladder exists between " + input.get(0).toLowerCase() + " and " + input.get(1).toLowerCase());
		String currentWord = ladder.get(0);
		System.out.println(currentWord); //print initial entry in ladder
		String lastWord=currentWord;     //seed lastword variable
		if(ladder.size()==2){    //check for case where there is no ladder
			System.out.println(ladder.get(1).toLowerCase());
		}else {  //if there is a ladder, print it accordingly
			for (int i = 1; i < ladder.size(); i++) {
				currentWord = ladder.get(i);
				int diff = 0;
				for (int j = 0; j < currentWord.length(); j++) {
					if (currentWord.charAt(j) != lastWord.charAt(j)) {
						diff = j;
						break;
					}
				}
				String out = currentWord.substring(0, diff) + String.valueOf(currentWord.charAt(diff)).toUpperCase() + currentWord.substring(diff + 1);
				System.out.println(out); //print word w letter changed capitalized
				lastWord = currentWord; //store last word for comparison purposes
			}
		}
	}

	/* Do not modify makeDictionary */
	public static Set<String>  makeDictionary () {
		Set<String> words = new HashSet<String>();
		Scanner infile = null;
		try {
			infile = new Scanner (new File("five_letter_words.txt"));
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
