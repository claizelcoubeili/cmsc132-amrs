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

	int[] integerRegisters = new int[32]; 
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
		this.readFile("../input/TestCase002.txt");
		this.noOfInst = this.rawInput.size();

		this.loadRegex();
		this.parseInput();
		this.initializeTable();
		this.execute();
		this.printTable();
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
				
				// will not continue if not instruction's turn to start the instruction
				if(this.cpuCycle < i){
					continue;
				}

				if(!this.instructions.get(i).done) {
					// executed depending on the instruction
					switch(this.instructions.get(i).instruction) {
						case "LOAD":	this.instructions.get(i).loadInstruction(this, i);
										break;
						case "ADD":		this.instructions.get(i).addInstruction(this, i);
										break;
						case "SUB":		this.instructions.get(i).subInstruction(this, i);
										break;
						case "CMP":		this.instructions.get(i).cmpInstruction(this, i);
										break;
					}
				}
			}

			printRegisters();
			this.cpuCycle++;
			if(checkDone()) break;
			// if(cpuCycle == 20) break;
			// System.out.println("=================");
		}

	}

	public Boolean checkDone() {
		boolean flag = true;

		for(int i=0; i<this.instructions.size(); i++) {
			if(this.instructions.get(i).done == true) continue;
			else {
				flag = false;
				break;
			}
		}

		return flag;

	}

	public void printRegisters() {
		System.out.println("\n================================================================================");
		System.out.println("\n\t\tClock Cycle "+(cpuCycle+1));
		System.out.println("Integer Registers: ");
		for(int i=0; i<this.integerRegisters.length; i++) {
			System.out.print("R"+i+"\t");
		}
		System.out.println();
		for(int i=0; i<this.integerRegisters.length; i++) {
			System.out.print(this.integerRegisters[i]+"\t");
		}

		System.out.println("\n\nOther Registers: ");
		System.out.println("PC\tMAR\tMBR\tOF\tNF\tZF\t");
		for(int i=0; i<this.otherRegisters.length; i++) {
			System.out.print(this.otherRegisters[i]+"\t");
		}
		System.out.println();
		System.out.println("================================================================================\n");

	}

	public void printTable() {
		
		System.out.print("\nInstruction\t");
		for(int i=0; i<this.cpuCycle; i++) {
			System.out.print((i+1)+"\t");
		}
		System.out.println("\n");
		for(int i=0; i<this.table.size(); i++){
			System.out.print(this.rawInput.get(i)+"\t");
			for(int j=0; j<this.table.get(i).size(); j++) {
				System.out.print(this.table.get(i).get(j)+"\t");
			}
			System.out.println();
		}
	}
}