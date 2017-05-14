import java.io.*;
import java.util.*;
import java.awt.*;
import java.math.*;

public class Instruction {
	int[] registers = new int[2];
	String instruction = "";
	int immediateValue = 0;
	String stage = null;
	boolean raw=false;
	boolean war=false;
	boolean waw=false;
	

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
		
		/*	if(this.stage == null){
			this.stage =="F";
			return ;
		} */ 
		
		for (int i=0;i<amrs.otherRegisters[0];i++) {
			if(this.stage == amrs.table.get(i).get(amrs.cpuCycle)) {
			
			/*	//raw
				if (this.registers[2] == ) {
					this.stage = "S";
				//war
				} else if (this.registers[2] == ) {
				//waw
				} else if (this.registers[1] ==) 
			*/
				break;
			}
		}
	
		if(this.immediateValue > 99 || this.immediateValue  < -99){
				//sets the OF to 1
				amrs.otherRegisters[3] = 1;
						
		}else{
				//sets the OF to 0
				amrs.otherRegisters[3] = 0;
		}
		
	
	
		if(this.stage == "E"){
			this.registers[0] = this.immediateValue; 
		}
		
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
}