PROJECT AMRS
(An MSC Regi Suggested) MSC = Mysterious Simple Computer

The simple computer supports a 5-stage pipeline consisting of a Fetch, Decode, Execute, Memory Access and Write back stage. There are 32 integer (R1-R32) registers. Other registers include the Program Counter (PC),Memory Address Register (MAR), MBR (Memory Buffer Register), Overflow Flag (OF), Negative Flag (NF) and Zero Flag (ZF).
  
 Intructions that can be read are: LOAD, ADD, SUB, and CMP.


How to compile and run: (make sure you are in /cmsc132-amrs)
javac -d ../bin *.java
java -cp ../bin Main
