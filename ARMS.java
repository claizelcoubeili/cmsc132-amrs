import java.io.*;
import java.lang.StringBuilder;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.*;
import java.awt.*;
import java.math.*;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ARMS {
	ArrayList<ArrayList<String>> rawInput = new ArrayList<ArrayList<String>>();
	ArrayList<Instruction> instructions = new ArrayList<Instruction>();
	Hashmap<String,String> patterns = new HashMap<String,String>();

	int[] integerRegisters;
	int[] otherRegisters;
	int rawInputSize;

	public ARMS() {
		this.readFile("input.txt");
		this.rawInputSize = this.rawInput.size();
		System.out.println(rawInput);
	}

	public void readFile(String path) {
		ArrayList<String> tempArray;
		int count = 0;

		// read file input
		try {
			String line;
			BufferedReader reader = new BufferedReader(new FileReader(path));
			Scanner scan = new Scanner(System.in);

		// read every line
			String tempString;
			while((line=reader.readLine()) != null) {

				tempString = "";
				tempArray = new ArrayList<String>();
				for(int i = 0; i < line.length(); i++) {
					char c = line.charAt(i);
					if(c == ' '){
						tempArray.add(tempString);
						tempString = "";
					} else if(i == line.length() - 1) {
						tempString = tempString + Character.toString(c);
						tempArray.add(tempString);
						tempString = "";
					}else{
						tempString = tempString + Character.toString(c);
					}
				}		
				this.rawInput.add(tempArray);
				count++;
			}

		} catch(FileNotFoundException e) {
			System.out.println("File not found");
		} catch(Exception e) {
			System.out.println("Error in reading: "+e.getMessage());
		}
	}

	// regex
	public void loadRegex() {
		this.patterns.put("LOAD", "");

	}

	public void parseInput() {
		// for each line of instruction from file
		Instruction temp;
		int tempSize;
		for(int i=0; i<this.rawInputSize; i++) {
			temp = new Instruction();
			tempSize = this.rawInput.get(i).size();

			for(int j=0; j<tempSize; j++) {

				if(this.rawInput.get(i).get(j).indexOf("LOAD") != -1) {			// load instruction

				}

			}
		}
	}
}