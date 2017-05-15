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


public class AMRS {
	ArrayList<String> rawInput = new ArrayList<String>();
	ArrayList<Instruction> instructions = new ArrayList<Instruction>();
	HashMap<String,String> patterns = new HashMap<String,String>();
	ArrayList<ArrayList<String>> table = new ArrayList<ArrayList<String>>();

	int[] integerRegisters; 
	int[] otherRegisters = new int[6]; // PC, MAR, MBR, OF, NF, ZF 
	int noOfInst;
	int cpuCycle = 0;
	final int PC = 0;
	final int MAR = 1;
	final int MBR = 2;
	final int OF = 3;
	final int NF = 4;
	final int ZF = 5;

	public AMRS() {
		this.readFile("../input/input.txt");
		this.noOfInst = this.rawInput.size();

		this.loadRegex();
		this.parseInput();
		this.initializeTable();
		this.execute();
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
		for(int i=0; i<this.noOfInst; i++) {

			// check for all 
			for(String pattern : patterns.values()) {
				p = Pattern.compile(pattern);
				m = p.matcher(this.rawInput.get(i));

				// compare pattern to 
				if(m.find()) {
					this.instructions.add(new Instruction(m.group(1), Integer.parseInt(m.group(2)), Integer.parseInt(m.group(3))));
					continue;
				}
			}
		}
		
		if(this.instructions.size() != this.noOfInst) {
			System.out.println("Syntax Error! Check your input then try again.");
			System.exit(0);
		}
	}

	public void initializeTable() {
		for(int i=0; i<noOfInst; i++) {
			this.table.add(new ArrayList<String>());
		}
	}

	public void execute() {
		int i, j;
		int sizeHolder;

		this.otherRegisters[this.PC] = 0; // set PC to 1st instruction
		this.cpuCycle = 0;

		while(true) {
			for(i=0; i<this.noOfInst; i++) {
				this.table.get(i).add("");				// dynamically adds another cell
				this.otherRegisters[this.PC] = i;		// points the pc to appropriate index, to change so that it will point to executing instruction
				
				// will not continue if not instruction's turn to start the instruction
				if(this.cpuCycle < i){
					continue;
				}

				// executed depending on the instruction
				switch(this.instructions.get(i).instruction) {
					case "LOAD":	this.instructions.get(i).loadInstruction(this);
									break;
					case "ADD":		this.instructions.get(i).addInstruction(this);
									break;
					case "SUB":		this.instructions.get(i).subInstruction(this);
									break;
					case "CMP":		this.instructions.get(i).cmpInstruction(this);
									break;
				}
			}

			this.otherRegisters[this.PC] = 0;

			// temporary break, to add checker if all instruction is done
			if(this.cpuCycle == 4) break;
			this.cpuCycle++;

		}

		// temporarily prints the table for checking
		for(i=0;i<this.table.size();i++) {
			System.out.println(this.table.get(i));
		}
	}
}