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

		if(inst == "LOAD") {
			this.immediateValue = valB;	
		} else if(inst == "ADD") {
			this.registers[1] = valB;
		} else if(inst == "SUB") {
			this.registers[1] = valB;
		} else if(inst == "CMP") {
			this.registers[1] = valB;
		}
	}

	public void loadInstruction(AMRS amrs) {
		
		// set flags
		if(this.immediateValue > 99 || this.immediateValue  < -99){
			//sets the OF to 1
			amrs.otherRegisters[amrs.OF] = 1;
		}else{
			//sets the OF to 0
			amrs.otherRegisters[amrs.OF] = 0;
		}

		checkForHazards(amrs);

		// if stall, write S
		if(this.stall) {
			amrs.table.get(amrs.otherRegisters[amrs.PC]).set(amrs.cpuCycle, "S");
			return;
		}

		updateTable(amrs);
	}
  
	public void addInstruction(AMRS amrs) {
	
			for (int i=0;i<amrs.otherRegisters[0];i++) {
				if(this.stage == amrs.table.get(i).get(amrs.cpuCycle) ){
					break;
				}
			}
			
			if(this.immediateValue > 99 || this.immediateValue < -99){
					//sets the OF to 1
					amrs.otherRegisters[3] = 1;
			}else{
			 		//sets the OF to 0
			 		amrs.otherRegisters[3] = 0;
			}	

			
			if(this.stage =="E"){
				this.registers[0] = this.registers[0] + this.registers[1];
			}	
	}

	public void subInstruction(AMRS amrs) {
	
		for (int i=0;i<amrs.otherRegisters[0];i++) {
			if(this.stage == amrs.table.get(i).get(amrs.cpuCycle) ){
				break;
			}
		}
	
		if(this.immediateValue > 99 || this.immediateValue < -99){
				//sets the OF to 1
				amrs.otherRegisters[3] = 1;
		}else{
				//sets the OF to 0
				amrs.otherRegisters[3] = 0;
		}	
		
		if(this.stage == "E"){
			this.registers[0] = this.registers[0] - this.registers[1];
		}
	}

	public void cmpInstruction(AMRS amrs) {
	
		int difference;
		
		for (int i=0;i<amrs.otherRegisters[0];i++) {
			if(this.stage == amrs.table.get(i).get(amrs.cpuCycle) ){
				break;
			}
		}
		
		if(this.immediateValue > 99 || this.immediateValue < -99){
				//sets the OF to 1
				amrs.otherRegisters[3] = 1;
		}else{
					//sets the OF to 0
				amrs.otherRegisters[3] = 0;
		}
		
			
		if(this.stage == "E"){
		
			difference = this.registers[0] - this.registers[1];

			if(difference == 0){
				//sets ZF to 1
					amrs.otherRegisters[5] = 1;
			}else{
				//sets ZF to 0
				 	amrs.otherRegisters[5] = 0;
			}

			if(difference < 0){
				//sets NF to 1
					amrs.otherRegisters[4] = 1;
			}else{
				//sets NF to 0
				amrs.otherRegisters[4] = 0;
			}

			if(difference > 0){
				amrs.otherRegisters[4] = 0;
				amrs.otherRegisters[5] = 0;
			}

		}
    }

    // function to look for hazards
    public void checkForHazards(AMRS amrs) {
    	// check for stalls
		for(int i=0;i<amrs.otherRegisters[amrs.PC];i++) {

			// write after write
			if(amrs.instructions.get(i).registers[DESTINATION] == this.registers[DESTINATION] && amrs.instructions.get(i).done == false) {
				this.stall = true;
				this.waw = true;
			}

			// write after write
			else if(amrs.instructions.get(i).registers[SOURCE] == this.registers[DESTINATION] && amrs.instructions.get(i).done == false) {
				this.stall = true;
				this.waw = true;
			}

			// read after write
			else if(amrs.instructions.get(i).registers[DESTINATION] == this.registers[SOURCE] && amrs.instructions.get(i).done == false) {
				this.stall = true;
				this.raw = true;
			}

			// stall if same stage
			else if(amrs.instructions.get(i).stage == this.stage) {
				this.stall = true;
			}
		}
    }

    // function to update table
    public void updateTable(AMRS amrs) {
    	if(this.stage == "F") {
			amrs.table.get(amrs.otherRegisters[amrs.PC]).set(amrs.cpuCycle, "F");
			this.stage = "D";
			// what happens in fetch
		} else if (this.stage == "D") {
			amrs.table.get(amrs.otherRegisters[amrs.PC]).set(amrs.cpuCycle, "D");
			this.stage = "E";
			// what happens in decode
		} else if (this.stage == "E") {
			amrs.table.get(amrs.otherRegisters[amrs.PC]).set(amrs.cpuCycle, "E");
			this.stage = "M";
			// what happens in execute
		} else if (this.stage == "M") {
			amrs.table.get(amrs.otherRegisters[amrs.PC]).set(amrs.cpuCycle, "M");
			this.stage = "W";
			// what happends in whrite back
		} else if (this.stage == "W") {
			amrs.table.get(amrs.otherRegisters[amrs.PC]).set(amrs.cpuCycle, "W");
			this.done = true;
		} 
    }
}