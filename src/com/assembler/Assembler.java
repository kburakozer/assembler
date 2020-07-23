package com.assembler;

import java.util.regex.*;
import java.util.List;
public class Assembler {


    //this method take operand and returns its binary value.
    public String opCode(String operand) {
        String opCode = Seperator.opCodeSeperator(operand);
        if(opCode.equals("JE")){
            opCode = "JZ";
        }
        if(opCode.equals("JNE")){
            opCode = "JNZ";
        }

        String[] opCodes = {
                "HALT", "LOAD", "STORE", "ADD", "SUB", "INC", "DEC", "MUL",
                "DIV", "XOR", "AND", "OR", "NOT", "SHL", "SHR", "NOP",
                "PUSH", "POP", "CMP", "JMP", "JZ", "JNZ", "JC", "JNC",
                "JA", "JAE", "JB", "JBE", "READ", "PRINT"
        };
        String binCode = "";
        for(int i=0; i<opCodes.length; i++) {
            if(opCodes[i].equals(opCode)) {
                if(i < 15) {
                    binCode += Conversion.binary(i+1);
                } else {
                    String code = "" + (i-5);
                    binCode += Conversion.hexadecimal(code);
                }

            }
        }
        if(binCode.length() < 6) {
            while(binCode.length() < 6) {
                binCode = 0 + binCode;
            }
        } else if(binCode.length() == 8) {
            String tmp = binCode;
            binCode = "";
            for(int i = 2; i<8; i++) {
                //copy the bincode to delete first 2 binary values

                binCode += tmp.charAt(i);
            }
        }
        return binCode;
    }


    // this method takes the operand and calculates the address mode in binary
    public String addressMode(String operand) {
        String op = Seperator.operandSeperator(operand);
        String opCode = "";
        // tests if operand is a hexadecimal value
        String s1 = "[0-9A-F]{4}";        // 00
        // tests if operand is a non-digit character
        String s2 = "\\'\\D\\'";    // 00
        // tests if operand is a register like A,B,C,D,E or S
        String s3 = "[ABCDES]";     // 01
        // tests if operand is a [register] inside is A,B,C,D,E or S
        String s4 = "\\[[ABCDES]\\]"; // 10
        // tests if operand is a memory adress e.g [1234]
        String s5 = "\\[\\d\\]";    // 11
        // tests if operand is a label referring to a memory address.
        String s6 = "\\w+";         // 11

        int num = 0;
        if(op.equals("NOP") || op.equals("HALT")){
            return "00";
        }
        if(Pattern.matches(s1, op)){
            opCode = "00";
        } else if(Pattern.matches(s2, op)){
            opCode = "00";

        } else if(Pattern.matches(s3, op)){
            opCode = "01";
        } else if(Pattern.matches(s4, op)){
            opCode = "10";
        } else if(Pattern.matches(s5, op)){
            opCode = "11";
        } else if(Pattern.matches(s6, op)) {
            opCode = "11";
        } else{
            opCode = "00";
        }
        return opCode;
    }


    // this method takes the operand and calculates the operand in binary
    public String operandCode(String opp, List<String> array){
        // the variable that will be converted to binart at the end of the method
        int num = 0;

        // separates the codeline and brings the operand
        String operand = Seperator.operandSeperator(opp);
        int lastIndex = operand.length() - 1;
        // no operation if the operand is a label
        if(operand.charAt(lastIndex) == ':'){
            return null;
        }

        // tests the cases for patterns

        // tests if operand is a hexadecimal value
        String s1 = "[0-9A-F]{4}";        // 00
        // tests if operand is a non-digit character
        String s2 = "\\'\\D\\'";    // 00
        // tests if operand is a register like A,B,C,D,E or S
        String s3 = "[ABCDES]";     // 01
        // tests if operand is a [register] inside is A,B,C,D,E or S
        String s4 = "\\[[ABCDES]\\]"; // 10
        // tests if operand is a memory adress e.g [1234]
        String s5 = "\\[\\d\\]";    // 11
        // tests if operand is a label
        String s6 = "\\w+";         // 11

        // returns 16 zeros if operand is NOP or HALT
        if(operand.equals("NOP") || operand.equals("HALT")){
            return "0000000000000000";
        } else{
            if(Pattern.matches(s1, operand)){
//                String hexaList = "0123456789ABCDEF";
//                int size = operand.length() -1;
//                int j = 0;
//                // converts the hexadecimal value to decimal value
//                for(int i = size; i>=0; i--) {
//                    String m = "" + operand.charAt(i);
//                    int s = Integer.parseInt(m);
//                    int result = (int) (s*Math.pow(16,j));
//                    num +=  result;
//                    j++;

                int size = operand.length() - 1;

                int j = 0;
                int tmp = 0;
                int result = 0;
                for(int i=size; i>=0; i--) {
                    String m = "" + operand.charAt(i);
                    if(m.equals("A")) {
                        tmp = 10;
                    } else if(m.equals("B")) {
                        tmp = 11;
                    }else if(m.equals("C")) {
                        tmp = 12;
                    }else if(m.equals("D")) {
                        tmp = 13;
                    }else if(m.equals("E")) {
                        tmp = 14;

                    }else if(m.equals("F")) {
                        tmp = 15;
                    } else {
                        tmp = Integer.parseInt(m);
                    }
                    result += (int) (tmp*Math.pow(16,j));
                    j++;
                }
                num +=  result;
            } else if(Pattern.matches(s2, operand)){
                char c = operand.charAt(1);
                num = (int) c;
            } else if(Pattern.matches(s3, operand)){
                String registers = "ABCDES";
                // checks if operand is within the registers above
                for(int i = 0; i<registers.length(); i++){
                    // finds out the number of the register
                    String op = "" + operand.charAt(i);
                    if(op.equals(operand)){
                        num = registers.indexOf(operand) + 1;
                        break;
                    }
                }

            } else if(Pattern.matches(s4, operand)){
                // as operand is like this [A], it assigns the register inside []
                String regOperand = "" + operand.charAt(1);
                String registers = "ABCDES";
                // checks if operand is within the registers above
                for(int i = 0; i<registers.length(); i++){
                    // finds out the number of the register
                    String op = "" + registers.charAt(i);
                    if(op.equals(regOperand)){
                        num = registers.indexOf(regOperand) + 1;
                        break;
                    }
                }
            } else if(Pattern.matches(s5, operand)){
                // removes [ and ] from the operand to get the String of the integer
                String str = operand.replaceAll("\\[", "").replaceAll("\\]", "");
                // parses the string into integer
                int n = Integer.parseInt(str);
                num = n;
            } else if(Pattern.matches(s6, operand)){
                String label = operand + ":";
                int i = 0;
                for(String c:array){
                    String word = c;
                    int size = word.length() - 1;
                    if(word.equals(label)){
                        break;
                    } else if(word.charAt(size) == ':'){
                        continue;
                    } else{
                        i++;
                    }
                }
                num = i*3;
            } else{
                num = 0;
            }
        }
        if(num > 65535){
            System.out.println("The number is bigger than 16 bit");
            System.exit(0);
        }

        String result = "";
        int binNum = Conversion.binary(num);
        result = result + binNum;
        while(result.length()<16){
            result = 0 + result;
        }
        return result;
    }


    // this method takes a string of instruction and calculates it into binary.
    public String instruction (String s, List<String> array){
        String binInstruction = "";
        String opCode = opCode(s);
        String addressMode = addressMode(s);
        String operand = operandCode(s, array);
        binInstruction = binInstruction + opCode + addressMode + operand;

        return binInstruction;
    }


}
