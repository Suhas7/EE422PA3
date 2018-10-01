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
		printLadder(getWordLadderBFS(input.get(0),input.get(1)));
		// TODO methods to read in words, output ladder
	}

	public static void initialize() {
		input = parse(kb);
	}
	
	/**
	 * @param keyboard Scanner connected to System.in
	 * @return ArrayList of Strings containing start word and end word. 
	 * If command is /quit, return empty ArrayList. 
	 */
	public static ArrayList<String> parse(Scanner keyboard) {
		ArrayList<String> output = new ArrayList<String>(2);
		output.add(keyboard.next());
		output.add(keyboard.next());
		return output;
	}
	
	public static ArrayList<String> getWordLadderDFS(String start, String end) {
		
		// Returned list should be ordered start to end.  Include start and end.
		// If ladder is empty, return list with just start and end.
		Set<String> dict = makeDictionary();


		return null; // replace this line later with real return
	}
	
    public static ArrayList<String> getWordLadderBFS(String start, String end) {
		start = start.toUpperCase(); //clean input
		end = end.toUpperCase();
		Set<String> dict = makeDictionary(); //geeerate dict
		Set<String> visited = new HashSet<String>(); //create set for logging visited
		Map<String, String> edgeMap = new HashMap<String, String>(); //map stores child -> parent pairs
		Queue<String> nodeQueue = new LinkedList<>(); //create queue for which nodes to work through, BFS
		nodeQueue.add(start);//seed initial conditions
		String curr=start;
		visited.add(curr);
		endFound: //stop traversing once end is found, label to jump to
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
							break endFound;
						}
					}
				}
			}
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
		return outputLadder; // replace this line later with real return
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
