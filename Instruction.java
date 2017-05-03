import java.io.*;
import java.util.*;
import java.awt.*;
import java.math.*;

public class Instruction {
	int[] registers = new int[2];
	String instruction;
	int immediateValue;
	boolean raw;
	boolean war;
	boolean waw;

	public Instruction(String inst, int valA, int valB) {
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

	public void loadInstruction() {
			
	}

	public void addInstruction() {
	}

	public void subInstruction() {
	}

	public void cmpInstruction() {
	}
}