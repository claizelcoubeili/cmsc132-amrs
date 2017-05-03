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
	ArrayList<String> rawInput = new ArrayList<String>();
	ArrayList<Instruction> instructions = new ArrayList<Instruction>();
	HashMap<String,String> patterns = new HashMap<String,String>();
	ArrayList<ArrayList<String>> table = new ArrayList<ArrayList<String>>();

	int[] integerRegisters;
	int[] otherRegisters;
	int rawInputSize;

	public ARMS() {
		this.readFile("input.txt");
		this.rawInputSize = this.rawInput.size();

		this.loadRegex();
		this.parseInput();
	}

	public void readFile(String path) {
		ArrayList<String> tempArray;

		// read file input
		try {
			String line;
			BufferedReader reader = new BufferedReader(new FileReader(path));
			Scanner scan = new Scanner(System.in);

			// read every line
			String tempString;
			while((line=reader.readLine()) != null) {
				this.rawInput.add(line);
			}

		} catch(FileNotFoundException e) {
			System.out.println("File not found");
		} catch(Exception e) {
			System.out.println("Error in reading: "+e.getMessage());
		}
	}

	// regex
	public void loadRegex() {
		this.patterns.put("LOAD", "(LOAD) R(\\d+) (\\d+)");
		this.patterns.put("ADD", "(ADD) R(\\d+) R(\\d+)");
		this.patterns.put("SUB", "(SUB) R(\\d+) R(\\d+)");
		this.patterns.put("CMP", "(CMP) R(\\d+) R(\\d+)");
	}

	public void parseInput() {
		// for each line of instruction from file
		Instruction temp;
		int tempSize;
		Pattern p;
		Matcher m;

		// iterate over all instructions and create an instance
		for(int i=0; i<this.rawInputSize; i++) {

			// check for all 
			for(String pattern : patterns.values()) {
				p = Pattern.compile(pattern);
				m = p.matcher(this.rawInput.get(i));

				// compare pattern to 
				this.instructions.add(new Instruction(m.group(1), m.group(2), m.group(3)));
			}
		}
	}

	public void execute() {
		int i, j;
		int sizeHolder;

		for(i=0; i<rawInputSize; i++) {
			this.table.add(new ArrayList<String>());
			sizeHolder = this.table.get(i).size();
			for(j=0; j<=sizeHolder; j++) {
				if(sizeHolder)
			}
		}
	}
}