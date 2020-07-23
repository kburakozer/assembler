package com.assembler;

public class Seperator {

    // takes the instruction as argument and return operation code such as LOAD
    public static String opCodeSeperator(String s){
        String word;
        // splits the string via blank space
        String [] array = s.split(" ");
        // first element holds the operation code
        word = array[0];
        return word;
    }

    public static String operandSeperator(String s){
        String word;
        // splits the string via blank space
        String [] array = s.split(" ");
        // checks if the array size is more than 1
        if(array.length > 1)
            // if size is more than one operand is at index 1
            word = array[1];
        else
            // else operand is at index 0 as the size of array is 1
            word = array[0];

            return word;
    }

}
