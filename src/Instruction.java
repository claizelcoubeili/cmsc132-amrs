import java.io.*;
import java.util.*;
import java.awt.*;
import java.math.*;

public class Instruction {
	int[] registers = new int[2];
	String instruction = "";
	int immediateValue = 0;
	String stage = "F";
	boolean raw = false;
	boolean war = false;
	boolean waw = false;
	boolean done = false;
	boolean stall = false;
	final int DESTINATION = 0;
	final int SOURCE = 0;

	public Instruction(String inst, int valA, int valB) { //constructor
		this.instruction = inst;
		this.registers[0] = valA;

		switch(inst) {
			case "LOAD":	this.immediateValue = valB;	
							break;
			case "ADD":		this.registers[1] = valB;	
							break;
			case "SUB":		this.registers[1] = valB;	
							break;
			case "CMP":		this.registers[1] = valB;	
							break;
		}
	}

	// function to look for hazards
    public void checkForHazards(AMRS amrs, int index) {
    	// check for stalls
    	// System.out.println("Start: ");
		for(int i=0;i<index;i++) {

			// write after write
			if(amrs.instructions.get(i).registers[DESTINATION] == this.registers[DESTINATION] && amrs.instructions.get(i).done == false) {
				System.out.println("Hazard Detected! Hazard: Write after Write");
				this.waw = true;
			}

			// write after read
			if(amrs.instructions.get(i).registers[SOURCE] == this.registers[DESTINATION] && amrs.instructions.get(i).done == false) {
				this.war = true;
				System.out.println("Hazard Detected! Hazard: Write after Read");
			}

			// read after write
			if(amrs.instructions.get(i).registers[DESTINATION] == this.registers[SOURCE] && amrs.instructions.get(i).done == false) {
				this.raw = true;
				System.out.println("Hazard Detected! Hazard: Read after Write");
			}

			// System.out.println("Index: "+i+" WAW: "+this.waw+" WAR: "+this.war+" RAW: "+this.raw);
		}

		if(waw || war || raw) this.stall = true;
		else this.stall = false;

		this.waw = false;
		this.war = false;
		this.raw = false;

		// System.out.println("STALL: "+stall);
    }

    // function to update table
    public void updateTable(AMRS amrs, int index) {
    	if(this.stage == "F") {
			amrs.table.get(index).set(amrs.cpuCycle, "F");
			this.stage = "D";
			// what happens in fetch
		} else if (this.stage == "D") {
			amrs.table.get(index).set(amrs.cpuCycle, "D");
			this.stage = "E";
			// what happens in decode
		} else if (this.stage == "E") {
			amrs.table.get(index).set(amrs.cpuCycle, "E");
			this.stage = "M";
			// what happens in execute
		} else if (this.stage == "M") {
			amrs.table.get(index).set(amrs.cpuCycle, "M");
			this.stage = "W";
			// what happends in whrite back
		} else if (this.stage == "W") {
			amrs.table.get(index).set(amrs.cpuCycle, "W");
			this.stage = " ";
		} else if (this.stage == " ") {
			this.done = true;
		}
    }

	public void check(AMRS amrs, int index) {
    	for (int i=0;i<index;i++) {
			if(this.stage == amrs.table.get(i).get(amrs.cpuCycle) ){
				break;
			}
		}
		
		// check overflow flag
		if(this.immediateValue > 99 || this.immediateValue < -99){
			//sets the OF to 1
			amrs.otherRegisters[amrs.OF] = 1;
		}

		checkForHazards(amrs, index);
		// System.out.println("STALL in check: "+stall);

		// if stall, write S
		if(this.stall) {
			amrs.table.get(index).set(amrs.cpuCycle, "S");
			// System.out.println("pumasok: "+amrs.table.get(index).get(amrs.cpuCycle));
		} else {
			updateTable(amrs, index);	
    	}
    }

    
	public void loadInstruction(AMRS amrs, int index) {
		check(amrs, index);

		if(this.stall) return;

		switch (amrs.table.get(index).get(amrs.cpuCycle)) {
			case "F":	amrs.otherRegisters[amrs.PC] = index + 1;
						break;
			case "M":	amrs.integerRegisters[this.registers[this.DESTINATION]] = this.immediateValue;
						break;
		}
	}
  
	public void addInstruction(AMRS amrs, int index) {
		check(amrs, index);

		if(this.stall) return;

		switch (amrs.table.get(index).get(amrs.cpuCycle)) {
			case "F":	amrs.otherRegisters[amrs.PC] = index + 1;
						break;
			case "E":	amrs.integerRegisters[this.registers[this.DESTINATION]] = this.registers[this.DESTINATION] + this.registers[SOURCE];
						
						// check for overflow
						if(amrs.integerRegisters[this.registers[this.DESTINATION]] > 99 || amrs.integerRegisters[this.registers[this.DESTINATION]] < -99) {
							amrs.otherRegisters[amrs.OF] = 1;
						}

						break;
		}
	}

	public void subInstruction(AMRS amrs, int index) {
		check(amrs, index);

		if(this.stall) return;

		switch (amrs.table.get(index).get(amrs.cpuCycle)) {
			case "F":	amrs.otherRegisters[amrs.PC] = index + 1;
						break;
			case "E":	amrs.integerRegisters[this.registers[this.DESTINATION]] = this.registers[this.DESTINATION] - this.registers[SOURCE];
						
						// check for overflow
						if(amrs.integerRegisters[this.registers[this.DESTINATION]] > 99 || amrs.integerRegisters[this.registers[this.DESTINATION]] < -99) {
							amrs.otherRegisters[amrs.OF] = 1;
						}

						break;
		}
	}

	public void cmpInstruction(AMRS amrs, int index) {
		int difference;
		check(amrs, index);

		if(this.stall) return;

		switch (amrs.table.get(index).get(amrs.cpuCycle)) {
			case "F":	amrs.otherRegisters[amrs.PC] = index + 1;
						break;
			case "E":	difference = amrs.integerRegisters[this.registers[this.DESTINATION]] - amrs.integerRegisters[this.registers[this.SOURCE]];

						if(difference == 0){
							//sets ZF to 1
								amrs.otherRegisters[amrs.ZF] = 1;
						}else{
							//sets ZF to 0
							 	amrs.otherRegisters[amrs.ZF] = 0;
						}

						if(difference < 0){
							//sets NF to 1
								amrs.otherRegisters[amrs.NF] = 1;
						}else{
							//sets NF to 0
							amrs.otherRegisters[amrs.NF] = 0;
						}

						if(difference > 0){
							amrs.otherRegisters[amrs.NF] = 0;
							amrs.otherRegisters[amrs.ZF] = 0;
						}
						break;
		}
    }

}
