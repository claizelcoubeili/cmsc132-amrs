import java.io.*;
import java.util.*;
import java.awt.*;
import java.math.*;

public class Instruction {
	int[] registers = new int[2];
	String instruction;
	int immediateValue;
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

	public void loadInstruction(int index) {
		
			/*	if(this.stage == null){
				this.stage =="F";
				return ;
				} */ 
			
			for (int i=0;i<index;i++) {
				if(this.stage == ARMS.table[i][cpuCycle] ){
				
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
					ARMS.otherRegisters[3] = 1;
							
			}else{
					//sets the OF to 0
					ARMS.otherRegisters[3] = 0;
			}
			
		
		
			 if(this.stage == "E"){
			this.registers[0] = this.immediateValue; 
			}
		
	}

	public void addInstruction(int index) {
	
				for (int i=0;i<index;i++) {
					if(this.stage == ARMS.table[i][cpuCycle] ){
						break;
					}
			}
			
			if(this.immediateValue > 99 || this.immediateValue < -99){
					//sets the OF to 1
					ARMS.otherRegisters[3] = 1;
			}else{
			 		//sets the OF to 0
			 		ARMS.otherRegisters[3] = 0;
			}	
			
			
			if(this.stage =="E"){
			this.registers[0] = this.registers[0] + this.registers[1];
			}
		
	}

	public void subInstruction(int index) {
	
				for (int i=0;i<index;i++) {
					if(this.stage == ARMS.table[i][cpuCycle] ){
					break;
				}
			}
		
			if(this.immediateValue > 99 || this.immediateValue < -99){
					//sets the OF to 1
					ARMS.otherRegisters[3] = 1;
			}else{
					//sets the OF to 0
					ARMS.otherRegisters[3] = 0;
			}	
			
			if(this.stage == "E"){
			this.registers[0] = this.registers[0] - this.registers[1];
			}
	}

	public void cmpInstruction(int index) {
	
			int difference;
			
				for (int i=0;i<index;i++) {
					if(this.stage == ARMS.table[i][cpuCycle] ){
						break;
					}
			}
			
			if(this.immediateValue > 99 || this.immediateValue < -99){
					//sets the OF to 1
					ARMS.otherRegisters[3] = 1;
			}else{
						//sets the OF to 0
					ARMS.otherRegisters[3] = 0;
			}
			
				
			if(this.stage == "E"){
			
						difference = this.registers[0] - this.registers[1];
			
						if(difference == 0){
							//sets ZF to 1
								ARMS.otherRegisters[5] = 1;
						}else{
							//sets ZF to 0
							 	ARMS.otherRegisters[5] = 0;
						}
			
						if(difference < 0){
							//sets NF to 1
								ARMS.otherRegisters[4] = 1;
						}else{
							//sets NF to 0
							ARMS.otherRegisters[4] = 0;
						}
			
						if(difference > 0){
								ARMS.otherRegisters[4] = 0;
								ARMS.otherRegisters[5] = 0;
						}
			
				}
				
	}
}